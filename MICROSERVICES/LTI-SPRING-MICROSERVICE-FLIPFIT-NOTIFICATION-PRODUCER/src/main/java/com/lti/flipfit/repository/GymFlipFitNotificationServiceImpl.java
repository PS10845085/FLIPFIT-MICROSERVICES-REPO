package com.lti.flipfit.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lti.flipfit.bean.GymFlipFitNotification;
import com.lti.flipfit.service.GymFlipFitNotificationService;

@Service
public class GymFlipFitNotificationServiceImpl implements GymFlipFitNotificationService {
	
	private static final Logger logger = LoggerFactory.getLogger(GymFlipFitNotificationService.class);
	private final List<GymFlipFitNotification> notifications = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();


    /**
     * Sends a notification to the user and stores it in the system.
     *
     * <p>The notification is assigned a unique ID before being added to the list of notifications.</p>
     *
     * @param notification the {@link GymFlipFitNotification} object containing notification details
     * @return the {@link GymFlipFitNotification} object after assigning an ID and saving it
     */
    @Override
	public GymFlipFitNotification sendNotification(GymFlipFitNotification notification) {
    	logger.info(notification + "sending notification");
		notification.setId(counter.incrementAndGet());
		notifications.add(notification);
        return notification;

	}

}
