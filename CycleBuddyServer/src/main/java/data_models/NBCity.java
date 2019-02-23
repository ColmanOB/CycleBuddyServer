package data_models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NBCity {
	
	public List<Country> countries;

	public class Country {
		@SerializedName("lat")
		@Expose
		public double lat;
		@SerializedName("lng")
		@Expose
		public double lng;
		@SerializedName("zoom")
		@Expose
		public int zoom;
		@SerializedName("name")
		@Expose
		public String name;
		@SerializedName("hotline")
		@Expose
		public String hotline;
		@SerializedName("domain")
		@Expose
		public String domain;
		@SerializedName("language")
		@Expose
		public String language;
		@SerializedName("email")
		@Expose
		public String email;
		@SerializedName("timezone")
		@Expose
		public String timezone;
		@SerializedName("currency")
		@Expose
		public String currency;
		@SerializedName("country_calling_code")
		@Expose
		public String countryCallingCode;
		@SerializedName("system_operator_address")
		@Expose
		public String systemOperatorAddress;
		@SerializedName("country")
		@Expose
		public String country;
		@SerializedName("country_name")
		@Expose
		public String countryName;
		@SerializedName("terms")
		@Expose
		public String terms;
		@SerializedName("policy")
		@Expose
		public String policy;
		@SerializedName("website")
		@Expose
		public String website;
		@SerializedName("show_bike_types")
		@Expose
		public boolean showBikeTypes;
		@SerializedName("show_bike_type_groups")
		@Expose
		public boolean showBikeTypeGroups;
		@SerializedName("show_free_racks")
		@Expose
		public boolean showFreeRacks;
		@SerializedName("booked_bikes")
		@Expose
		public int bookedBikes;
		@SerializedName("set_point_bikes")
		@Expose
		public int setPointBikes;
		@SerializedName("available_bikes")
		@Expose
		public int availableBikes;
		@SerializedName("capped_available_bikes")
		@Expose
		public boolean cappedAvailableBikes;
		@SerializedName("pricing")
		@Expose
		public String pricing;
		@SerializedName("cities")
		@Expose
		public List<City> cities = null;

		public class City {
			@SerializedName("uid")
			@Expose
			public int uid;
			@SerializedName("lat")
			@Expose
			public double lat;
			@SerializedName("lng")
			@Expose
			public double lng;
			@SerializedName("zoom")
			@Expose
			public int zoom;
			@SerializedName("maps_icon")
			@Expose
			public String mapsIcon;
			@SerializedName("alias")
			@Expose
			public String alias;
			@SerializedName("break")
			@Expose
			public boolean _break;
			@SerializedName("name")
			@Expose
			public String name;
			@SerializedName("num_places")
			@Expose
			public String numPlaces;
			@SerializedName("refresh_rate")
			@Expose
			public String refreshRate;
			@SerializedName("bounds")
			@Expose
			public Bounds bounds;
			@SerializedName("booked_bikes")
			@Expose
			public int bookedBikes;
			@SerializedName("set_point_bikes")
			@Expose
			public int setPointBikes;
			@SerializedName("available_bikes")
			@Expose
			public int availableBikes;
			@SerializedName("places")
			@Expose
			public List<Place> places = null;

			public class Bounds {
				@SerializedName("south_west")
				@Expose
				public SouthWest southWest;
				@SerializedName("north_east")
				@Expose
				public NorthEast northEast;

				public class NorthEast {

					@SerializedName("lat")
					@Expose
					public double lat;
					@SerializedName("lng")
					@Expose
					public double lng;
				}

				public class SouthWest {

					@SerializedName("lat")
					@Expose
					public double lat;
					@SerializedName("lng")
					@Expose
					public double lng;

				}
			}

			public class Place {
				@SerializedName("uid")
				@Expose
				public int uid;
				@SerializedName("lat")
				@Expose
				public double lat;
				@SerializedName("lng")
				@Expose
				public double lng;
				@SerializedName("bike")
				@Expose
				public boolean bike;
				@SerializedName("name")
				@Expose
				public String name;
				@SerializedName("address")
				@Expose
				public Object address;
				@SerializedName("spot")
				@Expose
				public boolean spot;
				@SerializedName("number")
				@Expose
				public int number;
				@SerializedName("bikes")
				@Expose
				public int bikes;
				@SerializedName("bike_racks")
				@Expose
				public int bikeRacks;
				@SerializedName("free_racks")
				@Expose
				public int freeRacks;
				@SerializedName("maintenance")
				@Expose
				public boolean maintenance;
				@SerializedName("terminal_type")
				@Expose
				public String terminalType;
				@SerializedName("bike_list")
				@Expose
				public List<BikeList> bikeList = null;
				@SerializedName("bike_numbers")
				@Expose
				public transient List<String> bikeNumbers = null;
				@SerializedName("bike_types")
				@Expose
				public transient BikeTypes bikeTypes;
				@SerializedName("place_type")
				@Expose
				public String placeType;
				@SerializedName("rack_locks")
				@Expose
				public boolean rackLocks;

				public class BikeList {
					@SerializedName("number")
					@Expose
					public String number;
					@SerializedName("bike_type")
					@Expose
					public int bikeType;
					@SerializedName("lock_types")
					@Expose
					public List<String> lockTypes = null;
					@SerializedName("active")
					@Expose
					public boolean active;
					@SerializedName("state")
					@Expose
					public String state;
					@SerializedName("electric_lock")
					@Expose
					public boolean electricLock;
					@SerializedName("boardcomputer")
					@Expose
					public int boardcomputer;
				}

				public class BikeTypes {
					@SerializedName("14")
					@Expose
					public int _14;
				}
			}
		}
	}
}
