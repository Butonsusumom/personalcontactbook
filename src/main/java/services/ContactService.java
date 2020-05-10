package services;

import entity.Contact;
import dao.ContactDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ContactService {
    private final static Logger logger = getLogger(ContactService.class);

    private ContactDAO dao;

    @Resource
    private Map<String, ContactDAO> daoServices;

    public void setDataSource(Properties properties) {
        if (!properties.get("possibleTypesDB").toString().contains(properties.get("typeDB").toString())) {
            logger.error("Type data base is not known: '" + properties.get("typeDB") + "'. Refer parameter 'typeDB' in config file: config.properties");
            System.exit(1);
        }
        this.dao = daoServices.get("contact" + properties.get("typeDB"));
        this.dao.setTypeDB(properties.get("pathToDBFiles").toString());
    }

    public void create(Contact contact) {
        dao.create(contact);
    }

    public Contact getById(String idContact) {
        return dao.getContactById(idContact);
    }

    public List<Contact> getByIdUser(String idUser) {
        return dao.getByIdUser(idUser);
    }

    public List<Contact> getByIdUserAndName(String idUser, String lastName, String name, String mobilePhone) {
        return dao.getByIdUserAndName(idUser, lastName, name, mobilePhone);
    }

    public void update(Contact contact) {
        dao.update(contact);
    }

    public void delete(String idContact) {
        dao.delete(idContact);
    }
}
