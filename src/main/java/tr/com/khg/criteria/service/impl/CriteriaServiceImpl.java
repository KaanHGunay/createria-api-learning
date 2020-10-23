package tr.com.khg.criteria.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.khg.criteria.domain.Car;
import tr.com.khg.criteria.domain.CarStatistic;
import tr.com.khg.criteria.domain.Person;
import tr.com.khg.criteria.domain.maps.PersonMultiAttributes;
import tr.com.khg.criteria.service.CriteriaService;
import tr.com.khg.criteria.service.dto.CarDTO;
import tr.com.khg.criteria.service.dto.PersonDTO;
import tr.com.khg.criteria.service.mapper.CarMapper;
import tr.com.khg.criteria.service.mapper.PersonMapper;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CriteriaServiceImpl implements CriteriaService {

    private final Logger log = LoggerFactory.getLogger(CriteriaServiceImpl.class);

    private final EntityManager entityManager;

    private final PersonMapper personMapper;

    private final CarMapper carMapper;

    public CriteriaServiceImpl(EntityManager entityManager, PersonMapper personMapper, CarMapper carMapper) {
        this.entityManager = entityManager;
        this.personMapper = personMapper;
        this.carMapper = carMapper;
    }

    @Override
    public List<PersonDTO> selectAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "Alaska"));

        TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultStream().map(personMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<String> selectOneAttribute() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root.get("name"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "Alaska"));

        TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultList();
    }

    @Override
    public List<Object[]> selectMultipleAttributes() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Person> root = criteriaQuery.from(Person.class);

        Path<Object> pathName = root.get("name");
        Path<Object> pathSurname = root.get("surname");

        criteriaQuery.select(criteriaBuilder.array(pathName, pathSurname));
        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultList();
    }

    @Override
    public List<Object[]> selectMultipleAttributesOtherWay() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Person> root = criteriaQuery.from(Person.class);

        Path<Object> pathName = root.get("name");
        Path<Object> pathSurname = root.get("surname");

        criteriaQuery.multiselect(pathName, pathSurname);
        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultList();
    }

    @Override
    public List<PersonMultiAttributes> selectMultipleAttributesWithMapper() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonMultiAttributes> criteriaQuery = criteriaBuilder.createQuery(PersonMultiAttributes.class);
        Root<Person> root = criteriaQuery.from(Person.class);

        Path<Object> pathName = root.get("name");
        Path<Object> pathSurname = root.get("surname");

        criteriaQuery.multiselect(pathName, pathSurname);
        TypedQuery<PersonMultiAttributes> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultList();
    }

    @Override
    public String selectTupleCriteriaQueries() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Person> root = criteriaQuery.from(Person.class);

        Path<Object> pathName = root.get("name");
        Path<Object> pathSurname = root.get("surname");
        criteriaQuery.multiselect(pathName, pathSurname);

        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        // Tuple Serializable olmadığından rest dönüşü olmamakta
        List<Tuple> tuples = query.getResultList();

        return (String) tuples.get(0).get(pathName);
    }

    @Override
    public String selectMultipleRoots() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteria = builder.createTupleQuery();

        Root<Person> personRoot = criteria.from(Person.class);
        Root<Car> carRoot = criteria.from(Car.class);

        criteria.multiselect(personRoot, carRoot);

        Predicate personPredicate = builder.and(
            builder.equal(personRoot.get("name"), "Kaan"),
            builder.equal(personRoot.get("surname"), "Günay")
        );

        Predicate carPredicate = builder.equal(carRoot.get("brand"), "Ford");

        criteria.where(builder.and(personPredicate, carPredicate));

        List<Tuple> tuples = entityManager.createQuery(criteria).getResultList();

        return tuples.get(0).get(1).toString();
    }

    @Override
    public List<CarDTO> selectWithJoinQueries() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = builder.createQuery(Car.class);

        Root<Car> root = criteria.from(Car.class);
        // root.join("person");
        root.fetch("person"); // Lazy fetch olan attributes lerin override ederek eager yapma

        criteria.where(builder.isNotNull(root.get("brand")));

        TypedQuery<Car> query = entityManager.createQuery(criteria);
        List<Car> result = query.getResultList();

        for (Car car: result) {
            log.info(car.getPerson().getName());
        }

        return result.stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PersonDTO> selectWithParameters(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);

        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), parameter));

        TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, name);
        log.info(query.toString());

        return query.getResultStream().map(personMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Long selectUsingAggregateFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Person> root = criteriaQuery.from(Person.class);

        criteriaQuery.select(criteriaBuilder.count(root));
            // count
            // max
        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        log.info(typedQuery.toString());
        return typedQuery.getSingleResult();
    }

    @Override
    public CarStatistic selectUsingAggregateFunctionsWithMapping() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarStatistic> carStatisticCriteriaQuery = criteriaBuilder.createQuery(CarStatistic.class);
        Root<Car> root = carStatisticCriteriaQuery.from(Car.class);

        Expression<Long> totalCar = criteriaBuilder.count(root);
        Expression<Long> totalDistinctCar = criteriaBuilder.countDistinct(root);
        Expression<Number> maxYear = criteriaBuilder.max(root.get("year"));
        Expression<Double> avgYear = criteriaBuilder.avg(root.get("year"));
        Expression<Number> sumOfYears = criteriaBuilder.sum(root.get("year"));

        carStatisticCriteriaQuery.select(
            criteriaBuilder.construct(CarStatistic.class, totalCar, totalDistinctCar, maxYear, avgYear, sumOfYears));

        TypedQuery<CarStatistic> query = entityManager.createQuery(carStatisticCriteriaQuery);
        log.info(query.toString());

        return query.getSingleResult();
    }

    @Override
    public List<Object[]> selectFromAndJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        Root<Person> personRoot = criteriaQuery.from(Person.class);

        criteriaQuery.multiselect(carRoot, personRoot);

        criteriaQuery.where(criteriaBuilder.equal(carRoot.get("person"), personRoot.get("id")));

        Path<Object> brand = carRoot.get("brand");
        Path<Object> name = personRoot.get("name");

        criteriaQuery.select(criteriaBuilder.array(brand, name));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultList();
    }
}
