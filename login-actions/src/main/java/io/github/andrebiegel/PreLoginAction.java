package io.github.andrebiegel;

import org.osgi.service.component.annotations.Component;

import java.util.UUID;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * Liferay LifeCycle Action which is bound to the phase of pre.login
 * @author André Biegel
 */
@Component( immediate = true,
property = {
	"key=login.events.pre"
}, service = LifecycleAction.class)
public class PreLoginAction implements LifecycleAction {

	// Liferay Logging
	private static final Log logger = LogFactoryUtil.getLog(PreLoginAction.class);
	@Override
	public void processLifecycleEvent(LifecycleEvent event) throws ActionException {
		logger.info(event.getRequest().getRemoteHost() + " tries to acces the portal");
		System.out.println(event.getRequest().getRemoteHost() + " tries to acces the portal");
	}
}