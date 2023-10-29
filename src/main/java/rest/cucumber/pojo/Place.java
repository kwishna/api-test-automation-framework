package rest.cucumber.pojo;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"place name",
		"longitude",
		"state",
		"state abbreviation",
		"latitude"
})
public class Place {

	@JsonProperty("place name")
	private String placeName;
	@JsonProperty("longitude")
	private String longitude;
	@JsonProperty("state")
	private String state;
	@JsonProperty("state abbreviation")
	private String stateAbbreviation;
	@JsonProperty("latitude")
	private String latitude;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("place name")
	public String getPlaceName() {
		return placeName;
	}

	@JsonProperty("place name")
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	@JsonProperty("longitude")
	public String getLongitude() {
		return longitude;
	}

	@JsonProperty("longitude")
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("state abbreviation")
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	@JsonProperty("state abbreviation")
	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	@JsonProperty("latitude")
	public String getLatitude() {
		return latitude;
	}

	@JsonProperty("latitude")
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Place{" +
				"placeName='" + placeName + '\'' +
				", longitude='" + longitude + '\'' +
				", state='" + state + '\'' +
				", stateAbbreviation='" + stateAbbreviation + '\'' +
				", latitude='" + latitude + '\'' +
				", additionalProperties=" + additionalProperties +
				'}';
	}
}
