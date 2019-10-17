
package io.github.andrebiegel.boundary.listener;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import org.osgi.service.component.annotations.Component;

/**
 * A PortalInstanceLifecycleListener
 * */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class CustomPortalInstanceLifecycleListener extends BasePortalInstanceLifecycleListener {

    @Override
    public void portalInstanceRegistered(Company company) throws Exception {
    }

    @Override
    public void portalInstanceUnregistered(Company company) throws Exception {
    }

}
