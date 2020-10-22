package tr.com.khg.criteria.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/criteria/one")
    public PersonDTO getOne() {
        return criteriaService.selectOne();
    }
}
