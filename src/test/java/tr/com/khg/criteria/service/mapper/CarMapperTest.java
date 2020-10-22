package tr.com.khg.criteria.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarMapperTest {

    private CarMapper carMapper;

    @BeforeEach
    public void setUp() {
        carMapper = new CarMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(carMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(carMapper.fromId(null)).isNull();
    }
}
