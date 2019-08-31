package io.github.andrebiegel.liferay.plugins;

import java.util.Date;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André Biegel
 */
@Component( immediate = true)
public class CustomUserNotificationTrigger {

	public static final Log LOGGER = LogFactoryUtil.getLog(CustomUserNotificationTrigger.class);

	// for Creating the notifyxcation itself
	@Reference
	private UserNotificationEventLocalService notificationService;

	// fetching users
	@Reference
	private UserLocalService userService;

	// festching the
	@Reference
	private CompanyLocalService companyService;

   /**
     * With this reference, this component activation waits until portal initialization has completed.
     */
    @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
    protected void setModuleServiceLifecycle(ModuleServiceLifecycle moduleServiceLifecycle) {
    }


	public void notifyUser() {
		try {
			String type = CustomUserNotificationHandler.PORLET_ID;
			long timestamp = new Date().getTime();
			Company company = companyService.getCompanyByMx(PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));
			User testUser = userService.getUserByScreenName(company.getCompanyId(), "test");

			long deliverBy = testUser.getUserId(), userId = testUser.getUserId();
			ServiceContext serviceContext = new ServiceContext();
			int deliveryType = UserNotificationDeliveryConstants.TYPE_WEBSITE;

			String payload;
			payload = JSONFactoryUtil.createJSONObject()
					.put("HEAD", "New Custom Nofication" )
					.put("BODY", "Get out and don´t hack that much !!")
					.put("JUMP_URL", "https://www.google.com/")
					.toJSONString();

			boolean archived = false;

			notificationService.addUserNotificationEvent(userId, type, timestamp, deliveryType, deliverBy, payload,
					archived, serviceContext);

		} catch (Exception e) {
			LOGGER.error("error while adding a UserNotificationEvent", e);
		}
	}
}