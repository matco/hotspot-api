package name.matco.hotspot.api.dto;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import name.matco.hotspot.model.User;

@JsonInclude(value = Include.NON_NULL)
public record UserDto(
	@NotNull String handle,
	@NotNull String email,
	@NotNull String password, //password field is used only during user registration
	@NotNull String name
) {

	public UserDto(final String email, final String password, final String name) {
		this(null, email, password, name);
	}

	public UserDto(final User user) {
		//do not expose password
		this(user.getHandle(), user.getEmail(), null, user.getName());
	}

}
