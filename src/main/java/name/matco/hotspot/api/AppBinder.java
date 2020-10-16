package name.matco.hotspot.api;

import jakarta.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import name.matco.hotspot.api.security.tokens.JWTService;
import name.matco.hotspot.api.security.tokens.RevokedTokenCleaner;
import name.matco.hotspot.api.security.tokens.RevokedTokenRepository;
import name.matco.hotspot.repositories.SpotRepository;
import name.matco.hotspot.repositories.StashRepository;
import name.matco.hotspot.repositories.UserRepository;
import name.matco.hotspot.repositories.db.SpotRepositoryDb;
import name.matco.hotspot.repositories.db.StashRepositoryDb;
import name.matco.hotspot.repositories.db.UserRepositoryDb;
import name.matco.hotspot.services.datasource.ConnectionProvider;
import name.matco.hotspot.services.datasource.ConnectionProviderDb;

public class AppBinder extends AbstractBinder {

	@Override
	protected void configure() {
		//model
		bind(ConnectionProviderDb.class).to(ConnectionProvider.class).in(Singleton.class);
		bind(UserRepositoryDb.class).to(UserRepository.class).in(Singleton.class);
		bind(StashRepositoryDb.class).to(StashRepository.class).in(Singleton.class);
		bind(SpotRepositoryDb.class).to(SpotRepository.class).in(Singleton.class);
		//api
		bind(RevokedTokenRepository.class).to(RevokedTokenRepository.class).in(Singleton.class);
		bind(RevokedTokenCleaner.class).to(RevokedTokenCleaner.class).in(Singleton.class);
		bind(JWTService.class).to(JWTService.class).in(Singleton.class);
	}

}
