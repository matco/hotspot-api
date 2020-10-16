package name.matco.hotspot.api.mappers;

import java.util.NoSuchElementException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {

	@Override
	public Response toResponse(final NoSuchElementException exception) {
		return Response
				.status(Status.NOT_FOUND)
				.build();
	}

}