import dao.ContactDAOJSON;
import entity.Contact;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Vadim
 */
public class TestContactDAOJSON {

    @Test
    public void testContactDAOJSONGetById() {
        ContactDAOJSON mock = mock(ContactDAOJSON.class);
        Contact contact = new Contact();
        when(mock.getContactById("1")).thenReturn(contact);
        when(mock.getContactById("a")).thenReturn(null);
        assertEquals(contact, mock.getContactById("1"));
        assertEquals(null, mock.getContactById("a"));
    }

    @Test
    public void testContactDAOJSONGetByIdUser() {
        ContactDAOJSON mock = mock(ContactDAOJSON.class);
        List<Contact> contacts = new ArrayList<>();
        when(mock.getByIdUser("1")).thenReturn(contacts);
        when(mock.getByIdUser("a")).thenReturn(contacts);
        assertEquals(contacts, mock.getByIdUser("1"));
        assertEquals(contacts, mock.getByIdUser("a"));
    }

    @Test
    public void testContactDAOJSONGetByIdUserAndName() {
        ContactDAOJSON mock = mock(ContactDAOJSON.class);
        List<Contact> contacts = new ArrayList<>();
        when(mock.getByIdUserAndName("1", "lastName", "name", "567")).thenReturn(contacts);
        when(mock.getByIdUserAndName("a", "lastName", "name", "567")).thenReturn(contacts);
        assertEquals(contacts, mock.getByIdUserAndName("1", "lastName", "name", "567"));
        assertEquals(contacts, mock.getByIdUserAndName("a", "lastName", "name", "567"));
    }
}
