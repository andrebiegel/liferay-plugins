package io.github.andrebiegel.mb;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;

import org.osgi.service.component.annotations.Component;
/**
 * The Message Bus notifies Message Bus Event Listeners when destinations are added and removed. 
 */
@Component(
    immediate = true,
    service = MessageBusEventListener.class
)
public class MyMessageBusEventListener implements MessageBusEventListener {

    @Override
    public void destinationAdded(Destination destination) {
        System.out.println(destination.getName() + " has been added");
    }

    @Override
    public void destinationRemoved(Destination destination) {
        System.out.println(destination.getName() + " has been removed");
    }
}