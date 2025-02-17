package de.jena.sensinact.generic.mqtt.bridge;

import java.io.ByteArrayOutputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sensinact.prototype.model.SensinactModelManager;
import org.eclipse.sensinact.prototype.notification.ResourceDataNotification;
import org.gecko.emf.json.constants.EMFJs;
import org.gecko.osgi.messaging.MessagingService;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.typedevent.TypedEventHandler;
import org.osgi.service.typedevent.propertytypes.EventTopics;

import de.jena.sensinact.mqtt.generic.message.UpdateMessage;
import de.jena.sensinact.mqtt.generic.message.util.MessageUtil;

/**
 * Notified for all data events
 */
@Component
@EventTopics("DATA/*")
public class MqttBridgeNotification implements TypedEventHandler<ResourceDataNotification> {

	private static final Logger logger = System.getLogger(MqttBridgeNotification.class.getName());

	@Reference(target = "(id=full)")
	MessagingService messaging;
	
	@Reference
	ComponentServiceObjects<ResourceSet> setObjects;
	
	@Override
	public void notify(String topic, ResourceDataNotification event) {
		if(logger.isLoggable(Level.DEBUG)) {
			logger.log(Level.DEBUG, "received event on topic %s", topic);
		}
		try {
			UpdateMessage update = MessageUtil.createUpdateMessageForType(event.newValue != null ? event.newValue.getClass() :  event.type);
			update.setTimestamp(event.timestamp);
			update.setResource(event.resource);
			setValue(update, event.oldValue, "oldValue");
			setValue(update, event.newValue, "newValue");
			send(String.format("5g/sensinact/event/data/%s/%s/%s/%s", event.model, event.provider, event.service, event.resource), update );
		} catch (Throwable e) {
			System.err.println("Could not send update message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param update
	 * @param value
	 */
	private void setValue(UpdateMessage update, Object value, String name) {
		EStructuralFeature feature = update.eClass().getEStructuralFeature(name);
		update.eSet(feature, value);
	}

	private void send(String topic, EObject object) {
		if(logger.isLoggable(Level.DEBUG)) {
			logger.log(Level.DEBUG, "forwarding event on topic %s", topic);
		}
		
		ResourceSet set = setObjects.getService();
		
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			Resource resource = set.createResource(URI.createFileURI("temp.json"));
			resource.getContents().add(object);
			resource.save(baos, Collections.singletonMap(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true));
			messaging.publish(topic, ByteBuffer.wrap(baos.toByteArray()));
		} catch (Exception e) {
			logger.log(Level.ERROR, "Could not forward event on topic " + topic, e);
		} finally {
			setObjects.ungetService(set);
		}
		
	}
	
}