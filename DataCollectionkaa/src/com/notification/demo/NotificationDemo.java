package com.notification.demo;

import java.util.List;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.notification.NotificationListener;
import org.kaaproject.kaa.common.endpoint.gen.Topic;
import org.kaaproject.kaa.schema.example.Notification;

public class NotificationDemo {

	//private static final Logger LOG = LoggerFactory.getLogger(NotificationDemo.class);
	
	public static void main(String[] args) {
		
		KaaClient kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener(), true);
		
		kaaClient.addNotificationListener(new NotificationListener() {
			
			@Override
			public void onNotification(long topicId, Notification notification) {
				System.out.println(notification.toString());
				
			}
		});
		
		kaaClient.start();

		List<Topic> topics = kaaClient.getTopics();
		for (Topic topic : topics) {
			System.out.println("Id: "+ topic.getId() + " name: "+ topic.getName()+" type: "+
					topic.getSubscriptionType());
		}
	}
}
