package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "iduser")
    private long idUser;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "mobilephone")
    private String mobilePhone;

    @Column(name = "homephone")
    private String homePhone;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    public Contact() {

    }

    public Contact(long idUser, String lastName, String name, String middleName, String mobilePhone, String homePhone, String address, String email) {
        this.idUser = idUser;
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.email = email;
    }

    public Contact(long idUser, long id, String lastName, String name, String middleName, String mobilePhone, String homePhone, String address, String email) {
        this.idUser = idUser;
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.email = email;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone == null ? "" : homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "Contact: " + idUser + " " + lastName + " " + name + " " + middleName + " " + mobilePhone + " " + address + " " + email;
    }
}
