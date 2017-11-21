package name.matco.hotspot.repositories;

import java.sql.SQLException;
import java.util.UUID;

import javax.inject.Singleton;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.junit.Before;

import name.matco.hotspot.helpers.InitDatabase;
import name.matco.hotspot.repositories.db.SpotRepositoryDb;
import name.matco.hotspot.repositories.db.StashRepositoryDb;
import name.matco.hotspot.repositories.db.UserRepositoryDb;
import name.matco.hotspot.services.datasource.ConnectionProvider;
import name.matco.hotspot.services.datasource.mocks.ConnectionProviderMock;

public class RepositoryTest {

	protected ServiceLocator locator;

	@Before
	public void init() throws SQLException {
		//initialize dependency injection
		final ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();
		locator = factory.create(UUID.randomUUID().toString());
		final DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
		final DynamicConfiguration config = dcs.createDynamicConfiguration();
		config.bind(BuilderHelper.link(ConnectionProviderMock.class).to(ConnectionProvider.class).in(Singleton.class).build());
		config.bind(BuilderHelper.link(UserRepositoryDb.class).to(UserRepository.class).in(Singleton.class).build());
		config.bind(BuilderHelper.link(StashRepositoryDb.class).to(StashRepository.class).in(Singleton.class).build());
		config.bind(BuilderHelper.link(SpotRepositoryDb.class).to(SpotRepository.class).in(Singleton.class).build());
		config.commit();
		//initialize database
		InitDatabase.createDatabase(locator.getService(ConnectionProvider.class));
	}
}
