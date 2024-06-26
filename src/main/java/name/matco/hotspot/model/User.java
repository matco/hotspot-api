package name.matco.hotspot.model;

import java.security.Principal;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import at.favre.lib.crypto.bcrypt.BCrypt;

@JsonInclude(value = Include.NON_NULL)
public class User implements Principal {

	@NotNull
	private int pk;

	@NotNull
	private String handle;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	public final int getPk() {
		return pk;
	}

	public final void setPk(final int pk) {
		this.pk = pk;
	}

	public final String getHandle() {
		return handle;
	}

	public final void setHandle(final String handle) {
		this.handle = handle;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(final String email) {
		this.email = email;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setPlainTextPassword(final String password) {
		this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
	}

	public boolean checkPassword(final String challengePassword) {
		return BCrypt.verifyer().verify(challengePassword.toCharArray(), password).verified;
	}

}
