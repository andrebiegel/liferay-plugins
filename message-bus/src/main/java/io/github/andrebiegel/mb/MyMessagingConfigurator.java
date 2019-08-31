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

    private int _MAXIMUM_QUEUE_SIZE = 3;

    @Activate
    protected void activate(BundleContext bundleContext) {

        _bundleContext = bundleContext;

        // Create a DestinationConfiguration for parallel destinations.

        /**
         * Here are the primary destination types:
        Parallel Destination
        Messages sent here are queued.
        Multiple worker threads from a thread pool deliver each message to a registered message listener. There’s one worker thread per message per message listener.
        Serial Destination
        Messages sent here are queued.
        Worker threads from a thread pool deliver the messages to each registered message listener, one worker thread per message.
        Synchronous Destination
        Messages sent here are directly delivered to message listeners.
        The thread sending the message here delivers the message to all message listeners also.
         */

        DestinationConfiguration destinationConfiguration =
        
            new DestinationConfiguration(
                DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
                    "myDestinationName");
        /**
         *  or via static method
         */
            //DestinationConfiguration.createParallelDestinationConfiguration("myDestinationName");

        // Set the DestinationConfiguration's max queue size and
        // rejected execution handler.

        //initial number of worker threads for processing messages.
        //destinationConfiguration.setWorkersCoreSize(workersCoreSize);
        // limits the number of worker threads for processing messages.
        destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

        /**
         * Rejected Execution Handler:
         * A com.liferay.portal.kernel.concurrent.RejectedExecutionHandler instance can take action (e.g., log warnings) regarding rejected messages when the destination queue is full.
         */

        RejectedExecutionHandler rejectedExecutionHandler =
            new CallerRunsPolicy() {

                @Override
                public void rejectedExecution(
                    Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

                    if (_log.isWarnEnabled()) {
                        _log.warn(
                            "The current thread will handle the request " +
                                "because the graph walker's task queue is at " +
                                    "its maximum capacity");
                    }

                    super.rejectedExecution(runnable, threadPoolExecutor);
                }

            };

       destinationConfiguration.setRejectedExecutionHandler(
            rejectedExecutionHandler);

       // Create the destination

       Destination destination = _destinationFactory.createDestination(
            destinationConfiguration);

        // Add the destination to the OSGi service registry

        Dictionary<String, Object> properties = new HashMapDictionary<>();

        properties.put("destination.name", destination.getName());

        ServiceRegistration<Destination> serviceRegistration =
            _bundleContext.registerService(
                Destination.class, destination, properties);

        // Track references to the destination service registrations 

        _serviceRegistrations.put(destination.getName(),    
            serviceRegistration);
    }

    @Deactivate
    protected void deactivate() {

        // Unregister and destroy destinations this component unregistered

        for (ServiceRegistration<Destination> serviceRegistration : 
        _serviceRegistrations.values()) {

            Destination destination = _bundleContext.getService(
                serviceRegistration.getReference());

            serviceRegistration.unregister();

            destination.destroy();

        }

        _serviceRegistrations.clear();

     }

    @Reference
    private DestinationFactory _destinationFactory;

    private BundleContext _bundleContext;
    // Liferay Logging
    private static final Log _log = LogFactoryUtil.getLog(MyMessagingConfigurator.class);

    private final Map<String, ServiceRegistration<Destination>>
        _serviceRegistrations = new HashMap<>();
}