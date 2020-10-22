package tr.com.khg.criteria.service.dto;

import java.io.Serializable;
import tr.com.khg.criteria.domain.enumeration.CarTypes;

/**
 * A DTO for the {@link tr.com.khg.criteria.domain.Car} entity.
 */
public class CarDTO implements Serializable {
    
    private Long id;

    private String brand;

    private Integer year;

    private CarTypes carType;


    private Long personId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public void setCarType(CarTypes carType) {
        this.carType = carType;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarDTO)) {
            return false;
        }

        return id != null && id.equals(((CarDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarDTO{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", year=" + getYear() +
            ", carType='" + getCarType() + "'" +
            ", personId=" + getPersonId() +
            "}";
    }
}
