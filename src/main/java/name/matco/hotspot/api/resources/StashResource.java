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

import name.matco.hotspot.api.App;
import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.SpotRepository;
import name.matco.hotspot.repositories.StashRepository;

@Path("stashes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StashResource {

	@Context
	private SecurityContext sc;

	@Inject
	private StashRepository stashRepository;

	@Inject
	private SpotRepository spotRepository;

	@GET
	public List<Stash> allStashes(@QueryParam("search") final String search) {
		final User user = (User) sc.getUserPrincipal();
		return stashRepository.search(user, search);
	}

	@POST
	public Stash createStash(final Stash stash) {
		final User user = (User) sc.getUserPrincipal();
		stash.setUuid(UUID.randomUUID().toString());
		stash.setUserFk(user.getPk());
		stashRepository.save(stash);
		return stash;
	}

	@GET
	@Path(App.RESOURCE_UUID_PATH)
	public Stash getStash(@PathParam("uuid") final String uuid) {
		final User user = (User) sc.getUserPrincipal();
		final Stash stash = stashRepository.getByUuid(uuid).get();
		if(stash.getUserFk() == user.getPk()) {
			return stash;
		}
		throw new NotAuthorizedException("Bearer");
	}

	@PUT
	@Path(App.RESOURCE_UUID_PATH)
	public Response updateStash(@PathParam("uuid") final String uuid, final Stash stashDto) {
		final User user = (User) sc.getUserPrincipal();
		final var stash = stashRepository.getByUuid(uuid).orElseThrow(NotFoundException::new);
		if(stash.getUserFk() == user.getPk()) {
			stash.setName(stashDto.getName());
			stash.setDescription(stashDto.getDescription());
			stashRepository.update(stash);
			return Response.noContent().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Path(App.RESOURCE_UUID_PATH)
	public Response deleteStash(@PathParam("uuid") final String uuid) {
		final User user = (User) sc.getUserPrincipal();
		final Stash stash = stashRepository.getByUuid(uuid).get();
		if(stash.getUserFk() == user.getPk()) {
			stashRepository.delete(stash);
			return Response.noContent().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@GET
	@Path(App.RESOURCE_UUID_PATH + "/spots")
	public List<Spot> allSpots(@PathParam("uuid") final String uuid) {
		final User user = (User) sc.getUserPrincipal();
		final Stash stash = stashRepository.getByUuid(uuid).get();
		if(stash.getUserFk() == user.getPk()) {
			return spotRepository.getByStash(stash);
		}
		throw new NotAuthorizedException("Bearer");
	}

	@POST
	@Path(App.RESOURCE_UUID_PATH + "/spots" + App.RESOURCE_SPOT_UUID_PATH)
	public void addSpot(@PathParam("uuid") final String uuid, @PathParam("spotUuid") final String spotUuid) {
		final User user = (User) sc.getUserPrincipal();
		final Stash stash = stashRepository.getByUuid(uuid).get();
		if(stash.getUserFk() == user.getPk()) {
			final Spot spot = spotRepository.getByUuid(spotUuid).get();
			if(spot.getUserFk() == user.getPk()) {
				spotRepository.addToStash(stash, spot);
				return;
			}
		}
		throw new NotAuthorizedException("Bearer");
	}

	@DELETE
	@Path(App.RESOURCE_UUID_PATH + "/spots" + App.RESOURCE_SPOT_UUID_PATH)
	public void removeSpot(@PathParam("uuid") final String uuid, @PathParam("spotUuid") final String spotUuid) {
		final User user = (User) sc.getUserPrincipal();
		final Stash stash = stashRepository.getByUuid(uuid).get();
		if(stash.getUserFk() == user.getPk()) {
			final Spot spot = spotRepository.getByUuid(spotUuid).get();
			if(spot.getUserFk() == user.getPk()) {
				spotRepository.removeFromStash(stash, spot);
				return;
			}
		}
		throw new NotAuthorizedException("Bearer");
	}

}
