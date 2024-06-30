package name.matco.hotspot.api.mappers;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import name.matco.hotspot.repositories.EmailAlreadyExistsException;

@Provider
public class EmailAlreadyExistsExceptionMapper implements ExceptionMapper<EmailAlreadyExistsException> {

	@Override
	public Response toResponse(final EmailAlreadyExistsException exception) {
		return Response
			.status(Status.BAD_REQUEST)
			.entity(new ErrorMessage(exception.getMessage()))
			.type(MediaType.APPLICATION_JSON)
			.build();
	}

}
