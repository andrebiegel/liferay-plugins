package io.github.andrebiegel;


import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.AuthFailure;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Liferay Action which is bound to the phase of  auth.failure
 * 
 * @author André Biegel
 */
@Component(
	immediate = true, property = "key=auth.failure", service = AuthFailure.class
)
public class LoginFailure implements AuthFailure {

	@Reference
	private UserLocalService service;

	// Liferay Logging
	private static final Log logger = LogFactoryUtil.getLog(LoginFailure.class);

	@Override
	public void onFailureByEmailAddress(long companyId, String emailAddress, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap) throws AuthException {
				try {
					User user = service.getUserByEmailAddress(
						companyId, emailAddress);
		
					int failures = user.getFailedLoginAttempts();
		
					if (logger.isInfoEnabled()) {
						logger.info(
							"onFailureByEmailAddress: " + emailAddress +
								" has failed to login " + failures + " times");
					}
				}
				catch (PortalException pe) {
					logger.error(pe.getMessage(), pe);
		}
	}

	@Override
	public void onFailureByScreenName(long companyId, String screenName, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap) throws AuthException {
				try {
					User user = service.getUserByScreenName(
						companyId, screenName);
		
					int failures = user.getFailedLoginAttempts();
		
					if (logger.isInfoEnabled()) {
						logger.info(
							"onFailureByScreenName: " + screenName +
								" has failed to login " + failures + " times");
					}
				}
				catch (PortalException pe) {
					logger.error(pe.getMessage(), pe);
		}
	}

	@Override
	public void onFailureByUserId(long companyId, long userId, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap) throws AuthException {
				try {
					User user = service.getUserById(userId);
		
					int failures = user.getFailedLoginAttempts();
		
					if (logger.isInfoEnabled()) {
						logger.info(
							"onFailureByUserId: userId " + userId +
								" has failed to login " + failures + " times");
					}
				}
				catch (PortalException pe) {
					logger.error(pe.getMessage(), pe);
		}
	}

}