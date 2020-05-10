package dao;

import entity.CustomUser;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@Service("usermysql")
public class UserDAOmySQL extends AbstractDAO implements UserDAO {
    private final static Logger logger = getLogger(UserDAOmySQL.class);

    @Resource
    private UserRepository userRepository;

    @Override
    public void setPathToFileDB(String pathToFileDB) {

    }

    @Override
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public CustomUser getByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    @Override
    public CustomUser getUserById(String id) {
        return userRepository.getUserById(Long.valueOf(id));
    }

    @Override
    public void create(CustomUser user) {
        userRepository.save(user);
    }

}
