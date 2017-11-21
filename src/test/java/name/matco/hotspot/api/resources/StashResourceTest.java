package name.matco.hotspot.api.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import name.matco.hotspot.api.AuthenticatedTest;
import name.matco.hotspot.model.Stash;

public class StashResourceTest extends AuthenticatedTest {

	public static String ENDPOINT = "stashs";
	public static GenericType<List<Stash>> STASHS_TYPE = new GenericType<List<Stash>>() {
		//don't care
	};

	@Test
	public void test_stash_crud() {
		final Stash newStash = new Stash();
		newStash.setName("City halls");
		newStash.setDescription("Some beautiful city halls");

		//try to retrieve stashs and create stash before login
		Response response = target(ENDPOINT).request().get();
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

		response = target(ENDPOINT).request().post(Entity.entity(newStash, MediaType.APPLICATION_JSON));
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

		create_user();
		get_token();

		//retrieve stashs
		response = target(ENDPOINT).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		final List<Stash> stashs = response.readEntity(STASHS_TYPE);
		assertTrue(stashs.isEmpty());

		//create stash
		response = target(ENDPOINT).request().post(Entity.entity(newStash, MediaType.APPLICATION_JSON));
		assertEquals(response.getStatus(), Status.OK.getStatusCode());
		Stash responseStash = response.readEntity(Stash.class);
		assertNotNull(responseStash.getPk());
		assertTrue(responseStash.getUuid().matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}"));
		assertEquals("City halls", responseStash.getName());

		//retrieve stashs again, there is one more stash
		assertEquals(stashs.size() + 1, target(ENDPOINT).request().get(STASHS_TYPE).size());

		//get unexisting stash
		response = target(ENDPOINT + "/" + UUID.randomUUID().toString()).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

		//update stash
		responseStash.setName("Beautiful city halls");
		response = target(ENDPOINT + "/" + responseStash.getUuid()).request().put(Entity.entity(responseStash, MediaType.APPLICATION_JSON));
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

		response = target(ENDPOINT + "/" + responseStash.getUuid()).request().get();
		assertEquals(response.getStatus(), Status.OK.getStatusCode());
		responseStash = response.readEntity(Stash.class);
		assertTrue(responseStash.getUuid().matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}"));
		assertEquals("Beautiful city halls", responseStash.getName());

		//delete stash
		response = target(ENDPOINT + "/" + responseStash.getUuid()).request().delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

		//retrieve stashs again, there is one more stash
		assertEquals(stashs.size(), target(ENDPOINT).request().get(STASHS_TYPE).size());
	}

}
