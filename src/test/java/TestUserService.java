import dao.UserDAO;
import entity.CustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import services.UserService;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Vadim Sharomov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserService {

    @Autowired
    private Map<String, UserDAO> daoServices;

    @Autowired
    private UserService userService;

    @Test
    public void testUserServiceGetByLogin() {
        UserService mock = mock(UserService.class);
        CustomUser customUser = new CustomUser();
        when(mock.getUserByLogin("1")).thenReturn(customUser);
        when(mock.getUserByLogin("aaa")).thenReturn(null);
        assertEquals(customUser, mock.getUserByLogin("1"));
        assertEquals(null, mock.getUserByLogin("aaa"));
    }

    @Test
    public void testUserServiceGetById() {
        UserService mock = mock(UserService.class);
        CustomUser customUser = new CustomUser();
        when(mock.getById("1")).thenReturn(customUser);
        when(mock.getById("aaa")).thenReturn(null);
        assertEquals(customUser, mock.getById("1"));
        assertEquals(null, mock.getById("aaa"));
    }


    @Test
    public void testUserServiceGetAllUsers() {
        UserService mock = mock(UserService.class);
        ArrayList<CustomUser> customUsers = new ArrayList<>();
        when(mock.getAllUsers()).thenReturn(customUsers);
        assertEquals(customUsers, mock.getAllUsers());
    }
}
