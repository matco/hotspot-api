package name.matco.hotspot.repositories;

public class EmailAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1130181786555298555L;

	public EmailAlreadyExistsException(final String email) {
		super(String.format("There is already an account associated to the e-mail %s", email));
	}
}
