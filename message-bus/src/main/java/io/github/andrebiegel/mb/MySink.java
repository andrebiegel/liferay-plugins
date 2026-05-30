package io.github.andrebiegel.mb;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = { "destination.name=github/message_me" },
		service = MessageListener.class
)
public class MySink extends BaseMessageListener {
	
	@Override
	protected void doReceive(Message message) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(getClass().getName() + " destination.name=github/message_me received ..." + message.get("foo"));
		}
	}
	
	private static final Log _log = LogFactoryUtil.getLog(
			MySink.class);
	
}
