package name.matco.hotspot.api.resources;

import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;

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
		final var spot = spotRepository.getByUuid(uuid).orElseThrow(NotFoundException::new);
		if(spot.getUserFk() == user.getPk()) {
			spot.setName(spotDto.getName());
			spot.setDescription(spotDto.getDescription());
			spot.setLatitude(spotDto.getLatitude());
			spot.setLongitude(spotDto.getLongitude());
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
