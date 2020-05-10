import dao.ContactDAO;
import entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import services.ContactService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


/**
 * @author Vadim Sharomov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestContactService {

    @Autowired
    private Map<String, ContactDAO> daoServices;

    @Autowired
    private ContactService userService;

    @Test
    public void testContactServiceDelete() {
        ContactService mock = mock(ContactService.class);
        doThrow(new RuntimeException()).when(mock).delete("idContact");
    }

    @Test
    public void testContactServiceGetById() {
        ContactService mock = mock(ContactService.class);
        Contact contact = new Contact();
        when(mock.getById("1")).thenReturn(contact);
        when(mock.getById("a")).thenReturn(null);
        assertEquals(contact, mock.getById("1"));
        assertEquals(null, mock.getById("a"));
    }

    @Test
    public void testContactServiceGetByIdUser() {
        ContactService mock = mock(ContactService.class);
        List<Contact> contacts = new ArrayList<>();
        when(mock.getByIdUser("1")).thenReturn(contacts);
        when(mock.getByIdUser("a")).thenReturn(contacts);
        assertEquals(contacts, mock.getByIdUser("1"));
        assertEquals(contacts, mock.getByIdUser("a"));
    }

    @Test
    public void testContactServiceGetByIdUserAndName() {
        ContactService mock = mock(ContactService.class);
        List<Contact> contacts = new ArrayList<>();
        when(mock.getByIdUserAndName("1", "lastName", "name", "567")).thenReturn(contacts);
        when(mock.getByIdUserAndName("a", "lastName", "name", "567")).thenReturn(contacts);
        assertEquals(contacts, mock.getByIdUserAndName("1", "lastName", "name", "567"));
        assertEquals(contacts, mock.getByIdUserAndName("a", "lastName", "name", "567"));
    }
}
