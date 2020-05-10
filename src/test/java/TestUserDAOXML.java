import dao.UserDAO;
import dao.UserDAOXML;
import entity.CustomUser;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Vadim
 */
public class TestUserDAOXML {
    @SuppressWarnings("Duplicates")
    @Test
    public void testUserDAOXMLgetById() {
        UserDAO userDAOXML = mock(UserDAOXML.class);
        CustomUser customUser = new CustomUser();
        when(userDAOXML.getUserById("1")).thenReturn(customUser);
        when(userDAOXML.getUserById("a")).thenReturn(null);
        assertEquals(customUser, userDAOXML.getUserById("1"));
        assertEquals(null, userDAOXML.getUserById("a"));
    }


    @SuppressWarnings("Duplicates")
    @Test
    public void testUserDAOXMLgetByLogin() {
        UserDAOXML mock = mock(UserDAOXML.class);
        CustomUser customUser = new CustomUser();
        when(mock.getByLogin("login")).thenReturn(customUser);
        when(mock.getByLogin("111")).thenReturn(null);
        assertEquals(customUser, mock.getByLogin("login"));
        assertEquals(null, mock.getByLogin("111"));
    }

    @Test
    public void testUserDAOXMLGetAllUsers() {
        UserDAOXML mock = mock(UserDAOXML.class);
        ArrayList<CustomUser> customUsers = new ArrayList<>();
        when(mock.getAllUsers()).thenReturn(customUsers);
        assertEquals(customUsers, mock.getAllUsers());
    }
}
