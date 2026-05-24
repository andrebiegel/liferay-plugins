package io.github.andrebiegel.scheduler;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class DestinationCreator {
	public void createDestination(
			BundleContext bundleContext, DestinationFactory destinationFactory,
			String destinationName) {
		
		Destination destination = destinationFactory.createDestination(
				DestinationConfiguration.createSerialDestinationConfiguration(
						destinationName));
		
		_serviceRegistration = bundleContext.registerService(
				Destination.class, destination,
				HashMapDictionaryBuilder.<String, Object>put(
						"destination.name", destination.getName()
				).build());
	}
	
	public void removeDestination() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}
	
	private ServiceRegistration<Destination> _serviceRegistration;
}
