package io.github.andrebiegel.application.list;

import io.github.andrebiegel.constants.CustomAppPanelCategoryKeys;
import io.github.andrebiegel.constants.NavigationBarKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ab
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + CustomAppPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class CustomAppPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return NavigationBarKeys.NAVIGATIONBAR;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + NavigationBarKeys.NAVIGATIONBAR + ")",
		unbind = "-"
	)
	public void setPortlet(final Portlet portlet) {
		super.setPortlet(portlet);
	}

}