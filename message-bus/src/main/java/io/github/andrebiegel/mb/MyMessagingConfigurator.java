package io.github.andrebiegel.mb;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.kernel.concurrent.CallerRunsPolicy;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;

import com.liferay.portal.kernel.util.MapUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Message Bus destinations are based on destination configurations and registered as OSGi services.
 * Message Bus detects the destination services and manages their associated destinations.
 */
@Component(
    immediate = true,
    service = MyMessagingConfigurator .class
)
public class MyMessagingConfigurator {
	
	
	@Activate
	private void _activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
				DestinationConfiguration.createSerialDestinationConfiguration(
						"github/message_me"));
		
		_serviceRegistration = bundleContext.registerService(
				Destination.class, destination,
				MapUtil.singletonDictionary(
						"destination.name", destination.getName()));
	}
	
	@Deactivate
	private void _deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}
	
	@Reference
	private DestinationFactory _destinationFactory;
	
	private ServiceRegistration<Destination> _serviceRegistration;
}
