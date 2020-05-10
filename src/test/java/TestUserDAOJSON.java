import dao.UserDAOJSON;
import entity.CustomUser;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Vadim
 */
public class TestUserDAOJSON {
    @SuppressWarnings("Duplicates")
    @Test
    public void testUserDAOJSONGetById() {
        UserDAOJSON mock = mock(UserDAOJSON.class);
        CustomUser customUser = new CustomUser();
        when(mock.getUserById("1")).thenReturn(customUser);
        when(mock.getUserById("a")).thenReturn(null);
        assertEquals(customUser, mock.getUserById("1"));
        assertEquals(null, mock.getUserById("a"));
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testUserDAOJSONGetByLogin() {
        UserDAOJSON mock = mock(UserDAOJSON.class);
        CustomUser customUser = new CustomUser();
        when(mock.getByLogin("login")).thenReturn(customUser);
        when(mock.getByLogin("111")).thenReturn(null);
        assertEquals(customUser, mock.getByLogin("login"));
        assertEquals(null, mock.getByLogin("111"));
    }

    @Test
    public void testUserDAOJSONGetAllUsers() {
        UserDAOJSON mock = mock(UserDAOJSON.class);
        ArrayList<CustomUser> customUsers = new ArrayList<>();
        when(mock.getAllUsers()).thenReturn(customUsers);
        assertEquals(customUsers, mock.getAllUsers());
    }
}
