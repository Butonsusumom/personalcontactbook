import dao.UserDAOmySQL;
import entity.CustomUser;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Created by Vadim
 */
public class TestUserDAOmySQL {

    @Test
    public void testUserDAOmySQLGetById() {
        UserDAOmySQL mock = mock(UserDAOmySQL.class);
        CustomUser customUser = new CustomUser();
        when(mock.getUserById("1")).thenReturn(customUser);
        when(mock.getUserById("a")).thenReturn(null);
        assertEquals(customUser, mock.getUserById("1"));
        assertEquals(null, mock.getUserById("a"));
    }

    @Test
    public void testUserDAOmySQLGetByLogin() {
        UserDAOmySQL mock = mock(UserDAOmySQL.class);
        CustomUser customUser = new CustomUser();
        when(mock.getByLogin("login")).thenReturn(customUser);
        when(mock.getByLogin("111")).thenReturn(null);
        assertEquals(customUser, mock.getByLogin("login"));
        assertEquals(null, mock.getByLogin("111"));
    }

    @Test
    public void testUserDAOmySQLGetAllUsers() {
        UserDAOmySQL mock = mock(UserDAOmySQL.class);
        ArrayList<CustomUser> customUsers = new ArrayList<>();
        when(mock.getAllUsers()).thenReturn(customUsers);
        assertEquals(customUsers, mock.getAllUsers());
    }
}
