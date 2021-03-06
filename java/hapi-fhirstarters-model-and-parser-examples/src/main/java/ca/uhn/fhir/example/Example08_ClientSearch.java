package ca.uhn.fhir.example;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

public class Example08_ClientSearch {
	public static void main(String[] theArgs) {
		FhirContext ctx = FhirContext.forDstu3();
		IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu3");

		// Log requests and responses (very verbose for testing!)
		client.registerInterceptor(new LoggingInterceptor(true));

		// Build a search and execute it
		Bundle response = client.search()
				.forResource(Patient.class)
				.where(Patient.NAME.matches().value("Test"))
				.and(Patient.BIRTHDATE.before().day("2014-01-01"))
				.count(100)
				.returnBundle(Bundle.class)
				.execute();

		// How many resources did we find?
		System.out.println("Responses: " + response.getTotal());

		// Print the ID of the first one
		System.out.println(response.getEntry().get(0).getResource().getId());
	}
}
