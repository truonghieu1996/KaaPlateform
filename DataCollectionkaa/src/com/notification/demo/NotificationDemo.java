package com.notification.demo;

import java.util.ArrayList;
import java.util.List;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.notification.NotificationListener;
import org.kaaproject.kaa.client.notification.UnavailableTopicException;
import org.kaaproject.kaa.common.endpoint.gen.SubscriptionType;
import org.kaaproject.kaa.common.endpoint.gen.Topic;
import org.kaaproject.kaa.schema.example.Notification;

public class NotificationDemo {

	//private static final Logger LOG = LoggerFactory.getLogger(NotificationDemo.class);
	private static List<Topic> topics;
	private static KaaClient kaaClient;
	
	public static void main(String[] args) {	
		kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener(), true);
		kaaClient.addNotificationListener(new NotificationListener() {	
			@Override
			public void onNotification(long topicId, Notification notification) {
				System.out.println("Recived a notification of all: "+ notification.toString() + "with topic id: "+ topicId);
			}
		});
		kaaClient.start();
		topics = kaaClient.getTopics();
		showTopics();
		subscribeTopics(topics.get(2).getId());
	}
	
	private static void showTopics() {
		
		if(topics == null || topics.isEmpty()) {
			System.out.println("Topic list is empty!");
			return;
		}
		
		System.out.println("Available topics: ");
		for (Topic topic : topics) {
			System.out.println("Id: "+ topic.getId() + " name: "+ topic.getName()+" type: "+
					topic.getSubscriptionType());
		}
		
		System.out.println("Subcribed topics: ");
		for(Topic topic:  getTypeOneTopics(SubscriptionType.MANDATORY_SUBSCRIPTION)) {
			System.out.println("Id: "+ topic.getId() + " name: "+ topic.getName()+" type: "+
					topic.getSubscriptionType());
		}
		
		System.out.println("Unsubcribed topics: ");
		for(Topic topic:  getTypeOneTopics(SubscriptionType.OPTIONAL_SUBSCRIPTION)) {
			System.out.println("Id: "+ topic.getId() + " name: "+ topic.getName()+" type: "+
					topic.getSubscriptionType());
		}
	}
	
	private static List<Topic> getTypeOneTopics(SubscriptionType type) {
		List<Topic> res = new ArrayList<>();
		for(Topic topic : NotificationDemo.topics) {
			if(topic.getSubscriptionType() == type) {
				res.add(topic);
			}	
		}
		return res;
	}
	
	private static void subscribeTopics(long topicId) {
		try {
			kaaClient.subscribeToTopic(topicId);
		} catch (UnavailableTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
