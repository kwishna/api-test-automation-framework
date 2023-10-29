package rest.cucumber.data_provider;

import net.datafaker.Faker;

import java.util.Locale;

import static rest.cucumber.config.Configuration.configuration;

/**
 * Generates Fake Data.
 */
public class FakerDataFactory {

	private static final Faker faker = new Faker(new Locale(configuration().faker()));
	
	private FakerDataFactory() {
		
	}

	public static String getCompanyName() {
		return faker.company().name().replaceAll("[^a-zA-Z ]", "");
	}

	public static String getUrl() {
		return faker.company().url();
	}

	public static String getAddress() {
		return faker.address().streetAddress();
	}
	
	public static String getCity() {
		return faker.address().city();
	}

	public static String getCountry() {
		return faker.country().name();
	}

	public static String getFirstName() {
		return faker.name().firstName();
	}
	
	public static String getLastName() {
		return faker.name().lastName();
	}
	
	public static String getEmailAddress() {
		return faker.internet().emailAddress();
	}
	
	public static String getContactNumber() {
		return faker.phoneNumber().cellPhone();
	}

	public static String getBankAccountNumber() {
		return ""+faker.number().randomNumber(8, false);
	}
	
	public static String getSwiftCode() {
		return ""+faker.number().randomNumber(4, false);
	}
	public static String getTaxNumber() {
		return ""+faker.number().randomNumber(7, false);
	}
	
	public static String getRating() {
		return faker.options().option("Hot","Warm", "Cold");
	}
	
	public static String getSalutation() {
		return faker.options().option("Mr.","Ms.", "Mrs.", "Dr.", "Prof.");
	}
	
	

}
