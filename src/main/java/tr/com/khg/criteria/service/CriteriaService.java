package tr.com.khg.criteria.service;

import org.springframework.data.domain.Page;
import tr.com.khg.criteria.domain.Car;
import tr.com.khg.criteria.domain.CarStatistic;
import tr.com.khg.criteria.domain.Person;
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

    List<Object[]> selectGroupByAndHaving();

    List<Object[]> selectOderBy();

    List<Object[]> getRowNumAndData();

    Long get();

    List<?> getA();

    Page<Car> getPage();
}
