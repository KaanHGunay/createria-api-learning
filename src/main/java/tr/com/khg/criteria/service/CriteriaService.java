package tr.com.khg.criteria.service;

import tr.com.khg.criteria.service.dto.PersonDTO;

import java.util.List;

public interface CriteriaService {

    List<PersonDTO> selectAll();

    PersonDTO selectOne();

}
