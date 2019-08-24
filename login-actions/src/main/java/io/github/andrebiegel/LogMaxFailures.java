package io.github.andrebiegel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.AuthFailure;
import com.liferay.portal.kernel.service.UserLocalService;

/**
 * Liferay Action which is bound to the phase of  auth.max.failures
 * 
 * @author André Biegel
 */
@Component(
	immediate = true, property = "key=auth.max.failures", service = AuthFailure.class
)
public class LogMaxFailures implements AuthFailure {

	@Reference
	private UserLocalService service;

	// Liferay Logging
	private static final Log logger = LogFactoryUtil.getLog(LogMaxFailures.class);

	@Override
	public void onFailureByEmailAddress(
			long companyId, String emailAddress,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			User user = service.getUserByEmailAddress(
				companyId, emailAddress);

			boolean lockout = user.isLockout();

			if (logger.isInfoEnabled()) {
				logger.info(
					"onFailureByEmailAddress: " + emailAddress + " is " +
						(lockout ? "" : "not") + " locked out.");
			}
		}
		catch (PortalException pe) {
			logger.error(pe.getMessage(), pe);
		}
	}

	@Override
	public void onFailureByScreenName(
			long companyId, String screenName, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			User user = service.getUserByScreenName(
				companyId, screenName);

			boolean lockout = user.isLockout();

			if (logger.isInfoEnabled()) {
				logger.info(
					"onFailureByScreenName: " + screenName + " is " +
						(lockout ? "" : "not") + " locked out.");
			}
		}
		catch (PortalException pe) {
			logger.error(pe.getMessage(), pe);
		}
	}

	@Override
	public void onFailureByUserId(
			long companyId, long userId, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			User user = service.getUserById(userId);

			boolean lockout = user.isLockout();

			if (logger.isInfoEnabled()) {
				logger.info(
					"onFailureById: userId " + userId + " is " +
						(lockout ? "" : "not") + " locked out.");
			}
		}
		catch (PortalException pe) {
			logger.error(pe.getMessage(), pe);
		}
}

}