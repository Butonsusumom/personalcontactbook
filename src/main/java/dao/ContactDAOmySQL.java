package dao;

import entity.Contact;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import repository.ContactRepository;

import javax.annotation.Resource;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service("contactmysql")
public class ContactDAOmySQL extends AbstractDAO implements ContactDAO {
    private final static Logger logger = getLogger(ContactDAOmySQL.class);

    @Resource
    private ContactRepository contactRepository;

    @Override
    public void setTypeDB(String pathToFileDB) {

    }

    @Override
    public Contact getContactById(String id) {
        return contactRepository.getContactById(Long.valueOf(id));
    }

    @Override
    public List<Contact> getByIdUser(String idUser) {
        return contactRepository.findContactByIdUser(Long.valueOf(idUser));
    }

    @Override
    public List<Contact> getByIdUserAndName(String idUser, String lastName, String name, String mobilePhone) {
        return contactRepository.getByIdUserAndName(Long.valueOf(idUser), lastName, name, mobilePhone);
    }

    @Override
    public void create(Contact contact) {
        contactRepository.save(contact);
    }

    @Override
    public void update(Contact contact) {
        contactRepository.updateContact(contact.getLastName(), contact.getName(), contact.getMiddleName(), contact.getMobilePhone(), contact.getHomePhone(), contact.getAddress(), contact.getEmail(), contact.getId());
    }

    @Override
    public void delete(String id) {
        contactRepository.deleteById(Long.valueOf(id));
    }
}
