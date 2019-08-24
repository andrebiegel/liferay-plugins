package io.github.andrebiegel.boundary;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.client.PromiseRxInvoker;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.util.promise.Promise;

import io.github.andrebiegel.entity.Drink;

/**
 * @author André Biegel
 */
@Component(property = { JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/greetings",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Greetings.Rest", "liferay.oauth2=false" // deactivating oauth for demo
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
	@GET
	@Path("/drinks/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Drink deliver(
		@PathParam("name") @DefaultValue("water") String name) {
		return new Drink(name);
	}

}