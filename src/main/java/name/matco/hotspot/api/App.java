package name.matco.hotspot.api;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/")
public class App extends ResourceConfig {

	public final static String RESOURCE_UUID_PATH = "/{uuid:[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}}";
	public final static String RESOURCE_SPOT_UUID_PATH = "/{spotUuid:[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}}";

	private static Properties PROPERTIES;

	public App() {
		packages(getClass().getPackage().getName());
		//dependencies injection
		register(new AppBinder());
		//jersey advanced features
		register(JacksonFeature.class);
		register(MultiPartFeature.class);
		//authentication
		register(RolesAllowedDynamicFeature.class);
		//register(AuthenticationRequestFilter.class);
	}

	public static Properties getAppProperties() {
		if(PROPERTIES == null) {
			PROPERTIES = new Properties();
			try {
				PROPERTIES.load(App.class.getResourceAsStream("/config.properties"));
			}
			catch(final IOException e) {
				e.printStackTrace();
			}
			PROPERTIES.putAll(System.getProperties());
		}
		return PROPERTIES;
	}
}
