package io.github.andrebiegel.boundary;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import io.github.andrebiegel.entity.Drink;

/**
 * @author André Biegel 
 */
//@Component(service = DrinksResource.class,
//      scope = ServiceScope.PROTOTYPE)
//@JaxrsResource 
//@Path("/drinks")
//@JaxrsApplicationSelect("(osgi.jaxrs.name=Greetings.Rest)")
public class DrinksResource {

	@GET
	@Path("/drinks/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Drink deliver(
		@PathParam("name") @DefaultValue("water") String name) {
		return new Drink(name);
	}

}