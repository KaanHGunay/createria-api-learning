package tr.com.khg.criteria.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import tr.com.khg.criteria.domain.enumeration.CarTypes;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_type")
    private CarTypes carType;

    @ManyToOne
    @JsonIgnoreProperties(value = "cars", allowSetters = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public Car brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getYear() {
        return year;
    }

    public Car year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public Car carType(CarTypes carType) {
        this.carType = carType;
        return this;
    }

    public void setCarType(CarTypes carType) {
        this.carType = carType;
    }

    public Person getPerson() {
        return person;
    }

    public Car person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return id != null && id.equals(((Car) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", year=" + getYear() +
            ", carType='" + getCarType() + "'" +
            "}";
    }
}
