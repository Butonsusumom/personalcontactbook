package dao;

import entity.CustomUser;

import java.util.List;

public interface UserDAO {

    void setPathToFileDB(String pathToFileDB);

    void create(CustomUser user);

    List<CustomUser> getAllUsers();

    CustomUser getUserById(String id);

    CustomUser getByLogin(String login);

}
