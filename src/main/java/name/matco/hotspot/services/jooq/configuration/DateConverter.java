package name.matco.hotspot.services.jooq.configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.jooq.Converter;

public class DateConverter implements Converter<LocalDateTime, ZonedDateTime> {

	private static final long serialVersionUID = 2872785290137634886L;

	@Override
	public ZonedDateTime from(final LocalDateTime time) {
		if(time == null) {
			return null;
		}
		return time.atZone(ZoneId.of("UTC"));
	}

	@Override
	public LocalDateTime to(final ZonedDateTime time) {
		if(time == null) {
			return null;
		}
		return time.toLocalDateTime();
	}

	@Override
	public Class<LocalDateTime> fromType() {
		return LocalDateTime.class;
	}

	@Override
	public Class<ZonedDateTime> toType() {
		return ZonedDateTime.class;
	}

}
