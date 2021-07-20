package moviemad.controller;

import static org.mockito.Mockito.*;

import moviemad.dao.interfaces.UserDao;
import moviemad.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserController_authenticate {

	private static UserDao userDao;
	private static User sampleUser;
	
	@BeforeAll
	public static void initialise() {
		sampleUser = new User("user", "pass", null, null, null, null, null, null, null);
		userDao = mock(UserDao.class);
		when(userDao.getUserByUsername("user")).thenReturn(sampleUser);
		UserController.userDao = userDao;	
	}
	
	@Test
	public void shouldReturnTrueWhenMatching() {
        assertTrue(UserController.authenticate("user", "pass"));
    }

	@Test
	public void shouldReturnFalseWhenUserNotMatching() {
        assertFalse(UserController.authenticate("wrongUser", "pass"));
    }

	@Test
	public void shouldReturnFalseWhenPasswordNotMatching() {		
        assertFalse(UserController.authenticate("user", "wrongPass"));
    }
    
	@Test
	public void shouldReturnFalseWhenBothNotMatching() {
        assertFalse(UserController.authenticate("wrongUser", "wrongPass"));
    }
	
	@Test
	public void shouldReturnFalseWhenUserIsEmpty() {
        assertFalse(UserController.authenticate("", "pass"));
    }
	
	@Test
	public void shouldReturnFalseWhenPasswordIsEmpty() {	
        assertFalse(UserController.authenticate("user", ""));
    }
	
	@Test
	public void shouldReturnFalseWhenBothEmpty() {
        assertFalse(UserController.authenticate("", ""));
    }
	
	@Test
	public void shouldReturnFalseWhenUserIsNull() {
        assertFalse(UserController.authenticate(null, "pass"));
    }
	
	@Test
	public void shouldReturnFalseWhenPasswordIsNulll() {
        assertFalse(UserController.authenticate("user", null));
    }
	
	@Test
	public void shouldReturnFalseWhenBothNull() {
		assertFalse(UserController.authenticate(null, null));
	}
}
