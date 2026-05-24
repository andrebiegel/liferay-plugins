
package io.github.andrebiegel.boundary.actions;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;

import org.osgi.service.component.annotations.Component;

@Component(service = LifecycleAction.class, immediate = true, property = { "key=servlet.service.events.pre" })
public class ServletServicePreAction implements LifecycleAction, EnumerationStreamSupport {

    private static final String LIFECYCLE_ACTION_TYPE = "servlet.service.events.pre";

    @Override
    public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {

        printElementsNonNull(lifecycleEvent.getIds(), "ids", LIFECYCLE_ACTION_TYPE);
        printElementsNonNull(lifecycleEvent.getRequest(), "Request", LIFECYCLE_ACTION_TYPE);
        printElementsNonNull(lifecycleEvent.getSession(), "Session", LIFECYCLE_ACTION_TYPE);
    }
}
