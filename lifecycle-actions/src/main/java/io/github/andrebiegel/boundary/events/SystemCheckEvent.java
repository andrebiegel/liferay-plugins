package io.github.andrebiegel.boundary.events;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class SystemCheckEvent {

    // Liferay lifecycle service
    @Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK)
    private ModuleServiceLifecycle _portalInitialized;

    @Activate
    public void init() {
        System.out.println("Liferay ModulLifecycle : " +ModuleServiceLifecycle.SYSTEM_CHECK);
    }
}