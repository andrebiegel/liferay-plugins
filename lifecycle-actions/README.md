
#  Liferay Lifecycle Extension Points
A Collections of Lifecycle extensions provided by Liferay and found by myself.

## Liferay Lifecycle Listener

The following dependencies offer Listener  extensions points
'''xml
		<dependency>
			<groupId>com.liferay</groupId>
			<artifactId>com.liferay.portal.instance.lifecycle.api</artifactId>
			<scope>provided</scope>
		</dependency>
'''

* com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener : When new Companies are available or are going to be deleted


## Liferay Modul Lifecycle Events
Liferay also has a Modul Lifecycle, which can be used. 

* module.service.lifecycle=database.initialized
* module.service.lifecycle=portal.initialized
* module.service.lifecycle=portlets.initialized
* module.service.lifecycle=spring.initialized
* module.service.lifecycle=system.check

## Liferay Lifecycle Actions

This example demonstrates the actions and their accessible Objects

The different Liferay DXP livecycle events and their order are (@planetsizebrain thanks for the list):

* Once during a server start/stop cycle:
    - global.startup.events
    - application.startup.events (1 for every portal instance ID)
    - application.shutdown.events (1 for every portal instance ID)
    - global.shutdown.events
* For every login:
    - servlet.service.events.pre
    - servlet.session.create.events
    - servlet.service.events.post
    - login.events.pre
    - login.events.post
* For every logout:
    - servlet.service.events.pre
    - logout.events.pre
    - servlet.session.destroy.events
    - logout.events.post
    - servlet.service.events.post
* For every other HTTP request:
    - servlet.service.events.pre
    - servlet.service.events.post
* For every page that is updated:
    - servlet.service.events.pre
    - layout.configuration.action.update
    - servlet.service.events.post
* For every page that is added/updated:
    - servlet.service.events.pre
    - layout.configuration.action.delete
    - servlet.service.events.post

