package name.matco.hotspot.api.json;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public ObjectMapper getContext(final Class<?> type) {
		return MAPPER;
	}
}
