package name.matco.hotspot.model;

import java.security.Principal;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import at.favre.lib.crypto.bcrypt.BCrypt;

@JsonInclude(value = Include.NON_NULL)
public class User implements Principal {

	@NotNull
	private long pk;

	@NotNull
	private String handle;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	private Set<Stash> stashs;

	private Set<Spot> spots;

	public final long getPk() {
		return pk;
	}

	public final void setPk(final long pk) {
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

	public final String getFirstname() {
		return firstname;
	}

	public final void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public final String getLastname() {
		return lastname;
	}

	public final void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public final Set<Stash> getStashs() {
		return stashs;
	}

	public final void setStashs(final Set<Stash> stashs) {
		this.stashs = stashs;
	}

	public final Set<Spot> getSpots() {
		return spots;
	}

	public final void setSpots(final Set<Spot> spots) {
		this.spots = spots;
	}

	@JsonIgnore
	public void setPlainTextPassword(final String password) {
		this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
	}

	public boolean checkPassword(final String challengePassword) {
		return BCrypt.verifyer().verify(challengePassword.toCharArray(), password).verified;
	}

	@Override
	@JsonIgnore
	public String getName() {
		return String.format("%s %s", firstname, lastname);
	}
}
