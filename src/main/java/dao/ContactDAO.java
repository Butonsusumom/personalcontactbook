package dao;

import entity.Contact;

import java.util.List;


public interface ContactDAO {

    void setTypeDB(String pathToFileDB);

    void create(Contact contact);

    Contact getContactById(String id);

    List<Contact> getByIdUser(String idUser);

    List<Contact> getByIdUserAndName(String idUser, String lastName, String name, String mobilePhone);

    void update(Contact contact);

    void delete(String id);

}
