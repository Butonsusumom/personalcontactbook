package repository;

import entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where c.id = ?1")
    Contact getContactById(Long id);

    @Query("select c from Contact c where c.idUser = ?1")
    List<Contact> findContactByIdUser(Long idUser);

    @Query("select c from Contact c where c.idUser = ?1 and c.lastName like %?2% and c.name like %?3% and c.mobilePhone like %?4%")
    List<Contact> getByIdUserAndName(Long idUser, String lastName, String name, String mobilePhone);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Contact c set c.lastName = ?1, c.name = ?2, c.middleName = ?3, c.mobilePhone = ?4, c.homePhone = ?5, c.address = ?6, c.email = ?7 where c.id = ?8")
    int updateContact(String lastName, String name, String middleName, String mobilePhone, String homePhone, String address, String email, Long id);

    @Transactional
    @Query("delete from Contact c where c.id = ?1")
    @Modifying(clearAutomatically = true)
    void deleteById(Long id);
}
