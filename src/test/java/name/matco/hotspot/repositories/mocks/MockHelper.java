package name.matco.hotspot.repositories.mocks;

import org.apache.commons.lang3.StringUtils;

import name.matco.hotspot.model.Displayable;

public class MockHelper {

	public static boolean testSearch(Displayable displayable, String search) {
		if(StringUtils.isNotBlank(search)) {
			return StringUtils.containsIgnoreCase(displayable.getName(), search) || StringUtils.containsIgnoreCase(displayable.getDescription(), search);
		}
		return true;
	}
}
