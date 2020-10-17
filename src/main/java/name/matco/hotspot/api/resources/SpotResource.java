package name.matco.hotspot.api.resources;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier;

import name.matco.hotspot.api.App;
import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.SpotRepository;

@Path("spots")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpotResource {

	@Inject
	private SpotRepository spotRepository;

	@Context
	private SecurityContext sc;

	@GET
	public List<Spot> allSpots(@QueryParam("search") final String search) {
		final User user = (User) sc.getUserPrincipal();
		if(StringUtils.isNotBlank(search)) {
			return spotRepository.search(user, search);
		}
		return spotRepository.getByUser(user);
	}

	@POST
	public Spot createSpot(final Spot spot) {
		final User user = (User) sc.getUserPrincipal();
		spot.setUuid(UUID.randomUUID().toString());
		spot.setUserFk(user.getPk());
		spotRepository.save(spot);
		return spot;
	}

	@GET
	@Path(App.RESOURCE_UUID_PATH)
	public Spot getSpot(@PathParam("uuid") final String uuid) {
		final User user = (User) sc.getUserPrincipal();
		final Spot spot = spotRepository.getByUuid(uuid).get();
		if(spot.getUserFk() == user.getPk()) {
			return spot;
		}
		throw new NotAuthorizedException("Bearer");
	}

	@PUT
	@Path(App.RESOURCE_UUID_PATH)
	public Response updateSpot(@PathParam("uuid") final String uuid, final Spot spotDto) {
		final User user = (User) sc.getUserPrincipal();
		var spot = spotRepository.getByUuid(uuid).orElseThrow(() -> new NotFoundException());
		if(spot.getUserFk() == user.getPk()) {
			spot.setName(spotDto.getName());
			spot.setDescription(spotDto.getDescription());
			spot.setLatitude(spotDto.getLatitude());
			spot.setLongitude(spotDto.getLongitude());
			spot.setLabels(spotDto.getLabels());
			spotRepository.update(spot);
			return Response.noContent().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Path(App.RESOURCE_UUID_PATH)
	public Response deleteSpot(@PathParam("uuid") final String uuid) {
		final User user = (User) sc.getUserPrincipal();
		final Spot spot = spotRepository.getByUuid(uuid).get();
		if(spot.getUserFk() == user.getPk()) {
			spotRepository.delete(spot);
			return Response.noContent().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

}
