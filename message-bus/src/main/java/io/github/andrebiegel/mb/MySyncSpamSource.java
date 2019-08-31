package io.github.andrebiegel.mb;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = MySyncSpamSource.class)
public class MySyncSpamSource {

    @Activate
    public void sendSomeMessage() {

        Message message = new Message();

        message.put("foo", "bar from " + getClass().getName());

        SingleDestinationSynchronousMessageSender messageSender = _messageSenderFactory
                .createSingleDestinationSynchronousMessageSender("myDestinationName",
                        SynchronousMessageSender.Mode.DEFAULT);

        try {
            long timeout = 1000;
            messageSender.send(message, timeout);
        } catch (MessageBusException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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