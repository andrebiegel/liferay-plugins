package io.github.andrebiegel.portlet;

import javax.portlet.Portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import org.osgi.service.component.annotations.Component;

import io.github.andrebiegel.constants.NavigationBarKeys;

/**
 * @author André Biegel
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.init-param.always-display-default-configuration-icons=true",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"javax.portlet.display-name=NavigationBar",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.name=" + NavigationBarKeys.NAVIGATIONBAR,
		"javax.portlet.resource-bundle=content.Language",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NavigationBarPortlet extends MVCPortlet {
	
}