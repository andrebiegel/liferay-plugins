package io.github.andrebiegel.portlet.action;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import io.github.andrebiegel.constants.NavigationBarKeys;
import io.github.andrebiegel.display.context.NavigationBarDisplayContextFactory;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + NavigationBarKeys.NAVIGATIONBAR, "mvc.command.name=/",
		"mvc.command.name=view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	private static final Log _log = LogFactoryUtil.getLog(
		ViewMVCRenderCommand.class);
	@Reference
	private NavigationBarDisplayContextFactory displayContextFactory;

	@Override
	public String render(
			final RenderRequest renderRequest, final RenderResponse renderResponse)
		throws PortletException {
			_log.info("ViewMVCRenderCommand called");
			renderRequest.setAttribute("CUSTOM_DISPLAY_CONTEXT", displayContextFactory.provide());
		return "/view.jsp";
	}

}