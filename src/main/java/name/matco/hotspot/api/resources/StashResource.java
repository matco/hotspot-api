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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier;

import name.matco.hotspot.api.App;
import name.matco.hotspot.model.Spot;
import name.matco.hotspot.model.Stash;
import name.matco.hotspot.model.User;
import name.matco.hotspot.repositories.SpotRepository;
import name.matco.hotspot.repositories.StashRepository;

@Path("stashs")
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
	public List<Stash> allStashs(@QueryParam("search") final String search) {
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
		var stash = stashRepository.getByUuid(uuid).orElseThrow(() -> new NotFoundException());
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
