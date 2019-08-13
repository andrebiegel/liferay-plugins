package io.github.andrebiegel.boundary.events;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class SpringInitializedEvent {

    // Liferay lifecycle service
    @Reference(target = ModuleServiceLifecycle.SPRING_INITIALIZED)
    private ModuleServiceLifecycle _portalInitialized;

    @Activate
    public void init() {
        System.out.println("Liferay ModulLifecycle" +ModuleServiceLifecycle.SPRING_INITIALIZED);
    }
}