package tr.com.khg.criteria.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.khg.criteria.domain.Person;
import tr.com.khg.criteria.service.CriteriaService;
import tr.com.khg.criteria.service.dto.PersonDTO;
import tr.com.khg.criteria.service.mapper.PersonMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CriteriaServiceImpl implements CriteriaService {

    private final Logger log = LoggerFactory.getLogger(CriteriaServiceImpl.class);

    private final EntityManager entityManager;

    private final PersonMapper personMapper;

    public CriteriaServiceImpl(EntityManager entityManager, PersonMapper personMapper) {
        this.entityManager = entityManager;
        this.personMapper = personMapper;
    }


    @Override
    public List<PersonDTO> a() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);

        TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);
        log.info(query.toString());

        return query.getResultStream().map(personMapper::toDto).collect(Collectors.toList());
    }
}
