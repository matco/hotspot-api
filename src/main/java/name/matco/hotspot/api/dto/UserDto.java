package name.matco.hotspot.api.dto;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import name.matco.hotspot.model.User;

@JsonInclude(value = Include.NON_NULL)
public class UserDto {

	@NotNull
	private String handle;

	@NotNull
	private String email;

	@NotNull
	private String password; //password field will be used during user registration

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	public UserDto() {
		//required by serializer
		//when coming from an HTTP client
	}

	public UserDto(User user) {
		handle = user.getHandle();
		email = user.getEmail();
		//do not expose password
		firstname = user.getFirstname();
		lastname = user.getLastname();
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
