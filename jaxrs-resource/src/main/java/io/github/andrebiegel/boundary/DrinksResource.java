package io.github.andrebiegel.boundary;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import io.github.andrebiegel.entity.Drink;

/**
 * @author André Biegel
 */
@Component(service = DrinksResource.class,
      scope = ServiceScope.PROTOTYPE, property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Greetings.Rest)",
		"osgi.jaxrs.resource=true"
		})
@Path("/drinks")
public class DrinksResource {

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Drink deliver(
		@PathParam("name") @DefaultValue("water") String name) {
		return new Drink(name);
	}

}
