package io.github.andrebiegel;

import org.osgi.service.component.annotations.Component;

import static java.util.UUID.randomUUID;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;

/**
 * Liferay LifeCycle Action which is bound to the phase of post.login
 * 
 * @author André Biegel
 */
@Component(immediate = true, property = { "key=login.events.post" }, service = LifecycleAction.class)
public class PostLoginAction implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent event) throws ActionException {
		event.getRequest().getSession().setAttribute("liferay-tracking-token", randomUUID());
		System.out.println("liferay-tracking-token added");
	}
}