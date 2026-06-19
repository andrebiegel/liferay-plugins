package io.github.andrebiegel.boundary;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import io.github.andrebiegel.entity.Drink;

/**
 * @author André Biegel
 */
@Component(property = {
		"osgi.jaxrs.application.base=/greetings",
		"osgi.jaxrs.name=Greetings.Rest",
		"liferay.access.control.disable=true", // deactivating access control for demo
		"auth.verifier.guest.allowed=true" // enabling public access
}, immediate = true,service = Application.class)
public class GreetingApplication extends Application {

	private Client remote;
	private static final String REST_SERVICE_URL = "http://localhost:8080";

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Activate
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
	@Produces(MediaType.APPLICATION_JSON)
	public void morningWitRemote(@PathParam("name") @DefaultValue("dummy") String name, @Suspended AsyncResponse async) {

		CompletableFuture<Drink> future = remote.target(REST_SERVICE_URL).path("/o/greetings").path("/drinks").path("/{name}")
				.resolveTemplate("name", name).request().rx().get(Drink.class).toCompletableFuture();
		
		future.thenAcceptAsync(drinkItem-> async.resume(new ResponseDTO("Good Morning " + name + ". Would you like some " + drinkItem.name + "?"))).exceptionallyAsync(
				t -> {async.resume(Response.serverError());
				return null;});
	}
}
