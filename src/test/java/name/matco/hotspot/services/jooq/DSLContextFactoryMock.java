package name.matco.hotspot.services.jooq;

import jakarta.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import name.matco.hotspot.services.datasource.ConnectionProvider;

public class DSLContextFactoryMock implements Factory<DSLContext> {

	@Inject
	private ConnectionProvider connectionProvider;

	@Override
	public DSLContext provide() {
		return DSL.using(connectionProvider.getDataSource(), SQLDialect.H2, DSLContextFactory.getSettings());
	}

	@Override
	public void dispose(final DSLContext instance) {
		//nothing to do here
	}
}
