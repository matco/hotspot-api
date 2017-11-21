package name.matco.hotspot.api.security.tokens;

public class InvalidToken extends Exception {

	private static final long serialVersionUID = 4186551121636713422L;

	public InvalidToken() {
		super("Invalid token");
	}
}