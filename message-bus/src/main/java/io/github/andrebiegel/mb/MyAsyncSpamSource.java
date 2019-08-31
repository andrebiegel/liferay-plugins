package io.github.andrebiegel.mb;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    service = MyAsyncSpamSource.class)
public class MyAsyncSpamSource {
    @Activate
    public void sendSomeMessage() {
        Message message = new Message();
        message.put("foo", "bar from " + getClass().getName());
        SingleDestinationMessageSender messageSender = 
        _messageSenderFactory.createSingleDestinationMessageSender("myDestinationName");
         messageSender.send(message);
    }
    @Reference
    private SingleDestinationMessageSenderFactory _messageSenderFactory;

    /**
     * With this reference, this component activation waits until portal initialization has completed.
     */
    @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
    protected void setModuleServiceLifecycle(ModuleServiceLifecycle moduleServiceLifecycle) {
    }
  
}