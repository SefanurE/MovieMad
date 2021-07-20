package moviemad.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import moviemad.dao.interfaces.UserDao;
import moviemad.model.User;

public class TestUserController_checkEmailUnique {
	private static UserDao userDao;
	private static User sampleUser;
	
	@BeforeAll
	public static void initialise() {
		userDao = mock(UserDao.class);
		sampleUser = new User("user", "password", "user@email.com", null, null, null, null, null, null);
		UserController.userDao = userDao;	
		when(userDao.emailInDB(anyString())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if (((String)args[0]).equalsIgnoreCase(sampleUser.getEmail())) {
					return true;
				} 
				return false;
			}
		});
	}
	
	@Test
	public void shouldReturnTrueWhenUnique() {
        assertTrue(UserController.checkEmailUnique("userUnique@email.com"));
    }

	@Test
	public void shouldReturnFalseWhenNotUnique() {
        assertFalse(UserController.checkEmailUnique("user@email.com"));
    }

	@Test
	public void shouldReturnFalseWhenEmpty() {
        assertFalse(UserController.checkEmailUnique(""));
    }
	
	@Test
	public void shouldReturnFalseWhenNull() {
        assertFalse(UserController.checkEmailUnique(null));
    }
	
	@Test
	public void shouldReturnFalseWhenCapitalisedNotUnique() {
        assertFalse(UserController.checkEmailUnique("USER@email.com"));
    }
}
