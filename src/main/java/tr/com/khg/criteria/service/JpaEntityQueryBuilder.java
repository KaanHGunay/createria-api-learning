package tr.com.khg.criteria.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

public class JpaEntityQueryBuilder<E> {

    private final EntityManager entityManager;

    private final Class<E> entityClass;

    private final CriteriaBuilder criteriaBuilder;

    private final CriteriaQuery<E> criteriaQuery;

    private final Root<E> root;

    private final List<Predicate> predicates = new ArrayList<>();

    private final List<Order> orders = new ArrayList<>();

    private final Map<String, Join<?, ?>> joins = new HashMap<>();

    private Integer firstResult;

    private Integer maxResults;

    private JpaEntityQueryBuilder(EntityManager entityManager, Class<E> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(criteriaQuery.getResultType());
    }

    public static <T> JpaEntityQueryBuilder<T> initialize(EntityManager entityManager, Class<T> entityClass) {
        return new JpaEntityQueryBuilder<>(entityManager, entityClass);
    }

    public List<E> list() {
        TypedQuery<E> typedQuery = prepareSelectTypedQuery();

        if (firstResult != null) {
            typedQuery.setFirstResult(firstResult);
        }

        if (maxResults != null) {
            typedQuery.setMaxResults(maxResults);
        }

        return typedQuery.getResultList();
    }

    public E uniqueResult() {
        TypedQuery<E> typedQuery = prepareSelectTypedQuery();
        return typedQuery.getSingleResult();
    }

    public Page<E> page(PageRequest pageRequest) {
        this.firstResult = Long.valueOf(pageRequest.getOffset()).intValue();
        this.maxResults = pageRequest.getPageSize();

        IteratorUtils.toList(pageRequest.getSort().iterator())
            .forEach(sort -> addOrderBy(sort.getProperty(), sort.isAscending()));

        return PageableExecutionUtils.getPage(list(), pageRequest, this::count);
    }

    public long count() {
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<E> root = countCriteriaQuery.from(entityClass);
        joins.forEach((key, value) -> root.join(key));
        countCriteriaQuery.select(criteriaBuilder.count(root)).distinct(true);
        countCriteriaQuery.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Long> typedQuery = entityManager.createQuery(countCriteriaQuery);
        return typedQuery.getSingleResult();
    }

    private TypedQuery<E> prepareSelectTypedQuery() {
        criteriaQuery.select(root).distinct(true);
        criteriaQuery.where(predicates.toArray(new Predicate[]{})).orderBy(orders);
        return entityManager.createQuery(criteriaQuery);
    }

    public <J> JpaEntityQueryBuilder<E> innerJoin(String attribute) {
        joins.put(attribute, root.join(attribute, JoinType.INNER));
        return this;
    }

    public JpaEntityQueryBuilder<E> innerFetch(String attribute) {
        root.fetch(attribute, JoinType.INNER);
        return this;
    }

    public JpaEntityQueryBuilder<E> addOrderBy(String path, boolean ascending) {
        if (ascending) {
            addAscendingOrderBy(path);
        } else {
            addDescendingOrderBy(path);
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> addAscendingOrderBy(String path) {
        orders.add(criteriaBuilder.asc(toJpaPath(path)));
        return this;
    }

    public JpaEntityQueryBuilder<E> addDescendingOrderBy(String path) {
        orders.add(criteriaBuilder.desc(toJpaPath(path)));
        return this;
    }

    public JpaEntityQueryBuilder<E> setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public JpaEntityQueryBuilder<E> setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public JpaEntityQueryBuilder<E> objectEqualsTo(String path, Object value) {
        if (value != null) {
            addEqualPredicate(path, value);
        }
        return this;
    }

    public Optional<Predicate> objectEqualsToPredicate(String path, Object value) {
        if (value != null) {
            return Optional.of(equalPredicate(path, value));
        }
        return Optional.empty();
    }

    public JpaEntityQueryBuilder<E> like(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            predicates.add(criteriaBuilder.like(toJpaPath(path), '%' + value + '%'));
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> likeCaseInsensitive(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(toJpaPath(path)), '%' + value.toLowerCase() + '%'));
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> addInDisjunction(Optional<Predicate>... optionalPredicates) {
        List<Predicate> predicateList = Arrays.stream(optionalPredicates).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        if (predicateList.size() > 1) {
            predicates.add(criteriaBuilder.or(predicateList.toArray(new Predicate[]{})));
        } else if (predicateList.size() == 1) {
            predicates.add(predicateList.get(0));
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> stringEqualsTo(String path, String value) {
        if (StringUtils.isNotBlank(value)) {
            addEqualPredicate(path, value);
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> greaterThanOrEqualsTo(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(toJpaPath(path), comparable));
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> select(List<String> paths) {

        List<Selection<?>> selections = new ArrayList<>();

        for (String path: paths) {
            selections.add(toJpaPath(path));
        }

        criteriaQuery.multiselect(selections);
        return this;
    }

    public JpaEntityQueryBuilder<E> lessThanOrEqualsTo(String path, Comparable comparable) {
        if (Objects.nonNull(comparable)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(toJpaPath(path), comparable));
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> between(String path, Date firstDate, Date secondDate) {
        if (Objects.nonNull(firstDate) && Objects.nonNull(secondDate)) {
            predicates.add(criteriaBuilder.between(toJpaPath(path), firstDate, secondDate));
        }
        return this;
    }

    public JpaEntityQueryBuilder<E> in(String path, Collection<?> collection) {
        if (CollectionUtils.isNotEmpty(collection)) {
            predicates.add(criteriaBuilder.in(toJpaPath(path)).value(collection));
        }
        return this;
    }

    private void addEqualPredicate(String path, Object value) {
        predicates.add(equalPredicate(path, value));
    }

    private Predicate equalPredicate(String path, Object value) {
        return criteriaBuilder.equal(toJpaPath(path), value);
    }

    private <T> Path<T> toJpaPath(String stringPath) {
        String[] pathParts = StringUtils.split(stringPath, '.');

        assert pathParts != null && pathParts.length > 0 : "Path cannot be empty";

        Path<T> jpaPath = null;
        for (String eachPathPart : pathParts) {
            if (jpaPath == null) {
                jpaPath = root.get(eachPathPart);
            } else if (jpaPath instanceof PluralAttributePath && joins.containsKey(((PluralAttributePath<?>) jpaPath).getAttribute().getName())) {
                // TODO: test for nested join
                jpaPath = joins.get(((PluralAttributePath<?>) jpaPath).getAttribute().getName()).get(eachPathPart);
            } else {
                jpaPath = jpaPath.get(eachPathPart);
            }
        }

        return jpaPath;
    }

}
