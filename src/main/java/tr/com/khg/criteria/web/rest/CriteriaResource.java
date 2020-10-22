package tr.com.khg.criteria.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.khg.criteria.domain.maps.PersonMultiAttributes;
import tr.com.khg.criteria.service.CriteriaService;
import tr.com.khg.criteria.service.dto.PersonDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CriteriaResource {

    private final CriteriaService criteriaService;

    public CriteriaResource(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }

    @GetMapping("/criteria/all")
    public List<PersonDTO> getAll() {
        return criteriaService.selectAll();
    }

    @GetMapping("/criteria/one-attribute")
    public List<String> getAttribute() {
        return criteriaService.selectOneAttribute();
    }

    @GetMapping("/criteria/multiple-attributes")
    public List<Object[]> getMultipleAttributes() {
        return criteriaService.selectMultipleAttributes();
    }

    @GetMapping("/criteria/multiple-attributes-other-way")
    public List<Object[]> getMultipleAttributesOtherWay() {
        return criteriaService.selectMultipleAttributesOtherWay();
    }

    @GetMapping("/criteria/multiple-attributes-with-mapper")
    public List<PersonMultiAttributes> selectMultipleAttributesWithMapper() {
        return criteriaService.selectMultipleAttributesWithMapper();
    }

    @GetMapping("/criteria/tuple")
    public String selectTupleCriteriaQueries() {
        return criteriaService.selectTupleCriteriaQueries();
    }
}
