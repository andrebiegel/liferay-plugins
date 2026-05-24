package io.github.andrebiegel.scheduler;

import java.util.Date;
import java.util.Map;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true, property = {
		"cron.expression=0 0 0 * * ?",
		"destination.name=github/my_message_processor"
},
		service = MessageListener.class
)
public class MyTaskMessageListener extends BaseMessageListener {
	
	/**
	 * doReceive: This is where the magic happens, this is where you want to do the work for the scheduled job.
	 *
	 * @param message This is the message object tied to the job.  If you stored data with the job, the message will
	 * contain that data.
	 * @throws Exception In case there is some sort of error processing the task.
	 */
	@Override
	protected void doReceive(Message message) throws Exception {
		
		_log.info("Scheduled task executed...");
		System.out.println("Scheduled task executed...");
	}
	
	/**
	 * activate: Called whenever the properties for the component change (aka Config Admin) or OSGi is activating the
	 * component.
	 *
	 * @param properties The properties map from Config Admin.
	 * @throws SchedulerException in case of error.
	 */
	@Activate
	@Modified
	protected void activate(BundleContext bundleContext, Map<String, Object> properties) throws SchedulerException {
		
		// extract the cron expression from the properties
		String cronExpression = GetterUtil.getString(properties.get("cron.expression"), _DEFAULT_CRON_EXPRESSION);
		
		_destinationCreator.createDestination(
				bundleContext, _destinationFactory,
				"github/my_message_processor");
		
		Class<?> clazz = getClass();
		
		_trigger = _triggerFactory.createTrigger(
				clazz.getName(), clazz.getName(), new Date(), null,
				cronExpression);
		
		_schedulerEngineHelper.schedule(
				_trigger, StorageType.PERSISTED, null,
				"github/my_message_processor",
				null);
	}
	
	/**
	 * deactivate: Called when OSGi is deactivating the component.
	 */
	@Deactivate
	protected void deactivate() {
		try {
			if (_destinationCreator != null) {
				_destinationCreator.removeDestination();
				
				_destinationCreator = null;
			}
			
			if (_trigger == null) {
				return;
			}
			
			_schedulerEngineHelper.delete(
					_trigger.getJobName(), _trigger.getGroupName(),
					StorageType.PERSISTED);
		}
		catch (Exception exception) {
			_log.error(exception);
		}	}
	
	/**
	 * setModuleServiceLifecycle: So this requires some explanation...
	 *
	 * OSGi will start a component once all of it's dependencies are satisfied.  However, there are times where you want
	 * to hold off until the portal is completely ready to go.
	 *
	 * This reference declaration is waiting for the ModuleServiceLifecycle's PORTAL_INITIALIZED component which will
	 * not be available until, surprise surprise, the portal has finished initializing.
	 *
	 * With this reference, this component activation waits until portal initialization has completed.
	 *
	 * @param moduleServiceLifecycle
	 */
	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(ModuleServiceLifecycle moduleServiceLifecycle) {
	}
	
	// the default cron expression is to run daily at midnight
	private static final String _DEFAULT_CRON_EXPRESSION = "0 0 0 * * ?";
	
	private static final Log _log = LogFactoryUtil.getLog(MyTaskMessageListener.class);
	
	@Reference
	private TriggerFactory _triggerFactory;
	
	private Trigger _trigger;

	private DestinationCreator _destinationCreator = new DestinationCreator();

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;
}
