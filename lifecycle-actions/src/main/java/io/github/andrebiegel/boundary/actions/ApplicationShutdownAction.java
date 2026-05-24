package io.github.andrebiegel.boundary.actions;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;

import com.liferay.portal.kernel.util.PropsKeys;
import org.osgi.service.component.annotations.Component;

@Component(service = LifecycleAction.class, immediate = true, property = { "key=application.shutdown.events" })
public class ApplicationShutdownAction implements LifecycleAction, EnumerationStreamSupport {
    
    private static final String LIFECYCLE_ACTION_TYPE = "application.shutdown.events";
	
    @Override
    public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
		
        printElementsNonNull(lifecycleEvent.getIds(), "ids", LIFECYCLE_ACTION_TYPE);
        printElementsNonNull(lifecycleEvent.getRequest(), "Request", LIFECYCLE_ACTION_TYPE);
        printElementsNonNull(lifecycleEvent.getSession(), "Session", LIFECYCLE_ACTION_TYPE);
    }
}
