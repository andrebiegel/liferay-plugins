package io.github.andrebiegel.boundary;

import java.util.Collections;
import java.util.Set;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

import org.osgi.service.jakartars.client.PromiseRxInvoker;
import org.osgi.util.promise.Promise;

import io.github.andrebiegel.entity.Drink;

/**
 * @author André Biegel
 */
@Component(property = {
		"osgi.jaxrs.application.base=/greetings",
		"osgi.jaxrs.name=Greetings.Rest",
		"liferay.oauth2=false" // deactivating oauth for demo
}, service = Application.class)
public class GreetingApplication extends Application {

	private Client remote;
	private static final String REST_SERVICE_URL = "http://localhost:8080";

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	public void init() {
		this.remote = ClientBuilder.newClient();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String working() {
		return "It works!";
	}

	@GET
	@Path("/morning")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Good morning!";
	}

	@GET
	@Path("/morning/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String morning(@PathParam("name") @DefaultValue("dummy") String name,
			@QueryParam("drink") @DefaultValue("water") String drink) {

		String greeting = "Good Morning " + name;

		if (drink != null) {
			greeting += ". Would you like some " + drink + "?";
		}

		return greeting;
	}

	@GET
	@Path("/remote-morning/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public void morningWitRemote(@PathParam("name") @DefaultValue("dummy") String name,
			@QueryParam("drink") @DefaultValue("water") String drink, @Suspended AsyncResponse async) {

		Promise<Drink> promise = remote.target(REST_SERVICE_URL).path("/o/greetings").path("/drinks").path("/{name}")
				.resolveTemplate("name", name).request().rx(PromiseRxInvoker.class).get(Drink.class);
		promise.onSuccess(element -> async.resume("Good Morning " + name + ". Would you like some " + element.name + "?"))
				.onFailure(t -> async.resume(Response.serverError()));
		
	}
	//@GET
	//@Path("/drinks/{name}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Drink deliver(
		@PathParam("name") @DefaultValue("water") String name) {
		return new Drink(name);
	}

}
