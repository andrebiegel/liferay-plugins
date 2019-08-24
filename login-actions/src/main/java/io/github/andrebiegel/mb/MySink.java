package io.github.andrebiegel.mb;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;

@Component (
    immediate = true,
    property = {"destination.name=myDestinationName"},
    service = MessageListener.class
)
public class MySink implements MessageListener {

   public void receive(Message message) {
       System.out.println(getClass().getName()+  " destination.name=myDestinationName received ..." +message.get("foo"));
   }
}