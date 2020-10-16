package name.matco.hotspot.api;

import jakarta.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import name.matco.hotspot.repositories.SpotRepository;
import name.matco.hotspot.repositories.StashRepository;
import name.matco.hotspot.repositories.UserRepository;
import name.matco.hotspot.repositories.mocks.SpotRepositoryMock;
import name.matco.hotspot.repositories.mocks.StashRepositoryMock;
import name.matco.hotspot.repositories.mocks.UserRepositoryMock;

public class APITest extends JerseyTest {

	@Override
	protected ResourceConfig configure() {
		final ResourceConfig config = new App();
		config.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(UserRepositoryMock.class).to(UserRepository.class).in(Singleton.class).ranked(100);
				bind(StashRepositoryMock.class).to(StashRepository.class).in(Singleton.class).ranked(100);
				bind(SpotRepositoryMock.class).to(SpotRepository.class).in(Singleton.class).ranked(100);
			}
		});
		return config;
	}

	@Override
	public void configureClient(final ClientConfig config) {
		config.register(MultiPartFeature.class);
		config.register(JacksonFeature.class);
	}

}
