package tr.com.khg.criteria.domain.maps;

public class PersonMultiAttributes {

    private String name;

    private String surname;

    PersonMultiAttributes() {}

    public PersonMultiAttributes(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
