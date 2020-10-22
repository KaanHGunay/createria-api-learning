package tr.com.khg.criteria.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link tr.com.khg.criteria.domain.Car} entity.
 */
public class CarDTO implements Serializable {
    
    private Long id;

    private String brand;

    private Integer year;

    
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
            "}";
    }
}
