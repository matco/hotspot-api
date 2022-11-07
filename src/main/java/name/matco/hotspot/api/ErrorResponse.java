package name.matco.hotspot.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ErrorResponse {

	public final String error;

	//do not delete this
	//this is required by WADL and Jersey tests
	public ErrorResponse() {
		this("Unknown error");
	}

	public ErrorResponse(final String error) {
		this.error = error;
	}
}
