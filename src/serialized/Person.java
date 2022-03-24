package serialized;

import generator.IdGenerator;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    public  String id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String city;

    public Person(ArrayList<String> personData){
        this.id = IdGenerator.generateId(10);
        this.name = personData.get(0);
        this.surname = personData.get(1);
        this.email = personData.get(2);
        this.phone = personData.get(3);
        this.city = personData.get(4);
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

    public String getEmail() { return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() { return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
