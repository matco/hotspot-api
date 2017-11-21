package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import name.matco.hotspot.api.AuthenticatedTest;
import name.matco.hotspot.model.Spot;

public class SpotResourceTest extends AuthenticatedTest {

	public static String ENDPOINT = "spots";
	public static GenericType<List<Spot>> SPOTS_TYPE = new GenericType<List<Spot>>() {
		//don't care
	};

	@Test
	public void test_spot_crud() {
		final Spot newSpot = new Spot();
		newSpot.setName("Annecy city hall");
		newSpot.setLatitude(45.8990465d);
		newSpot.setLongitude(6.1286562d);
		newSpot.setDescription("The city hall of Annecy");
		newSpot.setLabels(new HashSet<String>(Arrays.asList("by-the-lake", "big-house")));

		//try to retrieve spots and create spot before login
		Response response = target(ENDPOINT).request().get();
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

		response = target(ENDPOINT).request().post(Entity.entity(newSpot, MediaType.APPLICATION_JSON));
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

		create_user();
		get_token();

		//retrieve spots
		response = target(ENDPOINT).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		final List<Spot> spots = response.readEntity(SPOTS_TYPE);
		assertTrue(spots.isEmpty());

		//create spot
		response = target(ENDPOINT).request().post(Entity.entity(newSpot, MediaType.APPLICATION_JSON));
		assertEquals(response.getStatus(), Status.OK.getStatusCode());
		Spot responseSpot = response.readEntity(Spot.class);
		assertNotNull(responseSpot.getPk());
		assertTrue(responseSpot.getUuid().matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}"));
		assertEquals("Annecy city hall", responseSpot.getName());

		//retrieve spots again, there is one more spot
		assertEquals(spots.size() + 1, target(ENDPOINT).request().get(SPOTS_TYPE).size());

		//retrieve spots again, using a search
		assertEquals(spots.size() + 1, target(ENDPOINT).queryParam("search", "annecy").request().get(SPOTS_TYPE).size());

		//get unexisting spot
		response = target(ENDPOINT + "/" + UUID.randomUUID().toString()).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

		//update spot
		responseSpot.setName("Mairie d'Annecy");
		response = target(ENDPOINT + "/" + responseSpot.getUuid()).request().put(Entity.entity(responseSpot, MediaType.APPLICATION_JSON));
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

		response = target(ENDPOINT + "/" + responseSpot.getUuid()).request().get();
		assertEquals(response.getStatus(), Status.OK.getStatusCode());
		responseSpot = response.readEntity(Spot.class);
		assertTrue(responseSpot.getUuid().matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}"));
		assertEquals("Mairie d'Annecy", responseSpot.getName());

		//delete spot
		response = target(ENDPOINT + "/" + responseSpot.getUuid()).request().delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

		//retrieve spots again, there is one more spot
		assertEquals(spots.size(), target(ENDPOINT).request().get(SPOTS_TYPE).size());
	}

}
