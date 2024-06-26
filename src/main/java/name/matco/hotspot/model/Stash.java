package name.matco.hotspot.model;

import java.util.Objects;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Stash implements Displayable {

	@NotNull
	private int pk;

	@NotNull
	private int userFk;

	@NotNull
	private String uuid;

	@NotNull
	@Size(min = 1, max = 200)
	private String name;

	private String description;

	private Set<Spot> spots;

	public final int getPk() {
		return pk;
	}

	public final void setPk(final int pk) {
		this.pk = pk;
	}

	public final int getUserFk() {
		return userFk;
	}

	public final void setUserFk(final int userFk) {
		this.userFk = userFk;
	}

	public final String getUuid() {
		return uuid;
	}

	public final void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	@Override
	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	@Override
	public final String getDescription() {
		return description;
	}

	public final void setDescription(final String description) {
		this.description = description;
	}

	public final Set<Spot> getSpots() {
		return spots;
	}

	public final void setSpots(final Set<Spot> spots) {
		this.spots = spots;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Stash other = (Stash) obj;
		return Objects.equals(uuid, other.uuid);
	}

}
