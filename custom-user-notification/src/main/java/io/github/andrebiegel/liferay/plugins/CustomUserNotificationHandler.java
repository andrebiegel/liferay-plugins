package io.github.andrebiegel.liferay.plugins;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

@Component(service = UserNotificationHandler.class)
public class CustomUserNotificationHandler extends BaseUserNotificationHandler {

    /**
     *
     */

    public static final String PORLET_ID = "com_liferay_hello_world_web_portlet_HelloWorldPortlet";

    public CustomUserNotificationHandler() {
        // A Portlet ID 
        //TODO: debug in which cases it is needed
        setPortletId(PORLET_ID);
        //TODD: debug what that is really for
        setActionable(true);
    }

    /**
     * Defines a Template in HTML , how to Notification Looks like
     */
    @Override
    protected String getBodyTemplate() throws Exception {
        StringBundler sb = new StringBundler();
        sb.append(
                "<div class=\"mail-box-notification\"><div class=\"header\">[$HEAD$]</div><br/><div class=\"message\">[$BODY$]</div></div>");
        sb.append("<div class=\"button-holder\"><a ");
        sb.append("class=\"btn btn-primary ");
        sb.append("user-notification-action\" href=\"[$JUMP_URL$]\">");
        sb.append(" To The Real World</a>");
        return sb.toString();
    }

    /**
     * Consumes the Notification Event , reads the domain info und create a body of the template
     */
    @Override
    protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
            throws Exception {

        JSONObject message = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());
        String head = "";
        String body = "";
        String url = "";
        head = message.getString("HEAD");
        body = message.getString("BODY");
        url = message.getString("JUMP_URL");

        return StringUtil.replace(getBodyTemplate(), new String[] { "[$HEAD$]", "[$BODY$]", "[$JUMP_URL$]" },
                new String[] { head, body, url });
    }

}