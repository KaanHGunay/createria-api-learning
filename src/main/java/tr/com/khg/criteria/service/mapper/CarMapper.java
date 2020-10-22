package tr.com.khg.criteria.service.mapper;


import tr.com.khg.criteria.domain.*;
import tr.com.khg.criteria.service.dto.CarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Car} and its DTO {@link CarDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface CarMapper extends EntityMapper<CarDTO, Car> {

    @Mapping(source = "person.id", target = "personId")
    CarDTO toDto(Car car);

    @Mapping(source = "personId", target = "person")
    Car toEntity(CarDTO carDTO);

    default Car fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }
}
