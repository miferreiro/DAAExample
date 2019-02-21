package es.uvigo.esei.daa.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class UserUnitTest {
	@Test
	public void testUserStringStringString() {

		final String login = "login";
		final String password = "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1";
		final String role = "admin";
		
		final User user = new User(login, password, role);
		
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo(role)));
	}

	@Test(expected = NullPointerException.class)
	public void testUserStringStringStringNullLogin() {
		new User(null, "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1", "admin");
	}
	
	@Test(expected = NullPointerException.class)
	public void testUserStringStringStringNullPassword() {
		new User("login", null, "admin");
	}

	@Test(expected = NullPointerException.class)
	public void testUserStringStringStringNullRole() {
		new User("login", "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1", null);
	}
	
	@Test
	public void testSetLogin() {
		
		final String password = "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1";
		final String role = "admin";
		
		final User user = new User("aux", password, role);
		user.setLogin("login");
		
		assertThat(user.getLogin(), is(equalTo("login")));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo(role)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullLogin() {
		final User user = new User("login", "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1", "user");

		user.setLogin(null);
	}

	@Test
	public void testSetPassword() {
		
		final String login = "login";
		final String role = "admin";
		
		final User user = new User(login, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", role);
		user.setPassword("d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1");
		
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo("d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1")));
		assertThat(user.getRole(), is(equalTo(role)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullPassword() {
		final User user = new User("login", "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1", "user");

		user.setPassword(null);
	}
	
	@Test
	public void testSetRole() {
		
		final String login = "login";
		final String password = "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1";
		
		
		final User user = new User(login, password, "user");
		user.setRole("admin");
		
		assertThat(user.getLogin(), is(equalTo(login)));
		assertThat(user.getPassword(), is(equalTo(password)));
		assertThat(user.getRole(), is(equalTo("admin")));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullRole() {
		final User user = new User("login", "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1", "user");

		user.setRole(null);
	}
	
}
