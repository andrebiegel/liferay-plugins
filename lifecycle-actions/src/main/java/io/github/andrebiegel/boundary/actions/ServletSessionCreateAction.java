
package io.github.andrebiegel.boundary.actions;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;

import org.osgi.service.component.annotations.Component;

@Component(service = LifecycleAction.class, immediate = true, property = { "key=servlet.session.create.events" })
public class ServletSessionCreateAction implements LifecycleAction, EnumerationStreamSupport {

    private static final String LIFECYCLE_ACtION_TYPE = "servlet.session.create.events";

    @Override
    public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {

        printElementsNonNull(lifecycleEvent.getIds(), "ids", LIFECYCLE_ACtION_TYPE);
        printElementsNonNull(lifecycleEvent.getRequest(), "Request", LIFECYCLE_ACtION_TYPE);
        printElementsNonNull(lifecycleEvent.getSession(), "Session", LIFECYCLE_ACtION_TYPE);
    }
}
