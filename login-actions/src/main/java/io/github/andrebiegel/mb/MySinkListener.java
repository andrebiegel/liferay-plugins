package io.github.andrebiegel.mb;

import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {"destination.name=myDestinationName"},
    service = DestinationEventListener.class
)
public class MySinkListener implements DestinationEventListener {


    public void messageListenerRegistered(String destinationName,
                                   MessageListener messageListener) {
        System.out.println(messageListener.getClass().getName() + " added for " + destinationName);                
    }

    public void messageListenerUnregistered(String destinationName,
                                   MessageListener messageListener) {
        System.out.println(messageListener.getClass().getName() + " lost for " + destinationName);
    }
}