package repository;

import entity.CustomUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<CustomUser, Long> {

    List<CustomUser> findAll();

    @Query("select u from CustomUser u where u.id = ?1")
    CustomUser getUserById(Long id);

    @Query("select u from CustomUser u where u.login = ?1")
    CustomUser getByLogin(String login);



}
