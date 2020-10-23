package tr.com.khg.criteria.service;

import tr.com.khg.criteria.domain.CarStatistic;
import tr.com.khg.criteria.domain.maps.PersonMultiAttributes;
import tr.com.khg.criteria.service.dto.CarDTO;
import tr.com.khg.criteria.service.dto.PersonDTO;

import java.util.List;

public interface CriteriaService {

    List<PersonDTO> selectAll();

    List<String> selectOneAttribute();

    List<Object[]> selectMultipleAttributes();

    List<Object[]> selectMultipleAttributesOtherWay();

    List<PersonMultiAttributes> selectMultipleAttributesWithMapper();

    String selectTupleCriteriaQueries();

    String selectMultipleRoots();

    List<CarDTO> selectWithJoinQueries();

    List<PersonDTO> selectWithParameters(String name);

    Long selectUsingAggregateFunctions();

    CarStatistic selectUsingAggregateFunctionsWithMapping();

    List<Object[]> selectFromAndJoin();
}
