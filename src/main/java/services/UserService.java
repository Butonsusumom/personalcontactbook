package services;

import dao.UserDAO;
import entity.CustomUser;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;


@Service
public class UserService {
    private final static Logger logger = getLogger(UserService.class);

    private UserDAO dao;

    @Resource
    private Map<String, UserDAO> daoServices;

    public void setDataSource(Properties properties) {
        if (!properties.get("possibleTypesDB").toString().contains(properties.get("typeDB").toString())) {
            logger.error("Type data base is not known: '" + properties.get("typeDB") + "'. Refer parameter 'typeDB' in config file: config.properties");
            System.exit(1);
        }
        this.dao = daoServices.get("user" + properties.get("typeDB"));
        this.dao.setPathToFileDB(properties.get("pathToDBFiles").toString());
    }

    public void create(CustomUser user) {
        dao.create(user);
    }

    public List<CustomUser> getAllUsers() {
        return dao.getAllUsers();
    }

    public CustomUser getUserByLogin(String login) {
        return dao.getByLogin(login);
    }

    public CustomUser getById(String idUser) {
        return dao.getUserById(idUser);
    }

}


