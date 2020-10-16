package name.matco.hotspot.model;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class Spot implements Displayable {

	@NotNull
	private long pk;

	@NotNull
	private long userFk;

	@NotNull
	private String uuid;

	@NotNull
	@Size(min = 1, max = 200)
	private String name;

	@NotNull
	private double latitude;

	@NotNull
	private double longitude;

	private String description;

	private Set<String> labels;

	public final long getPk() {
		return pk;
	}

	public final void setPk(final long pk) {
		this.pk = pk;
	}

	public final long getUserFk() {
		return userFk;
	}

	public final void setUserFk(final long userFk) {
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

	public final double getLatitude() {
		return latitude;
	}

	public final void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public final double getLongitude() {
		return longitude;
	}

	public final void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	@Override
	public final String getDescription() {
		return description;
	}

	public final void setDescription(final String description) {
		this.description = description;
	}

	public final Set<String> getLabels() {
		return labels;
	}

	public final void setLabels(final Set<String> labels) {
		this.labels = labels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		final Spot other = (Spot) obj;
		if(uuid == null) {
			if(other.uuid != null) {
				return false;
			}
		}
		else if(!uuid.equals(other.uuid)) {
			return false;
		}
		return true;
	}
}
