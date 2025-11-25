package com.lti.filpfit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.lti.filpfit.beans.GymFlipFitNotification;


/**
 * Service implementation for managing notifications in the GymFlipFit application.
 *
 * <p>This service provides functionality to send notifications and maintain a list of sent notifications.</p>
 */
@Service
public class GymFlipFitNotificationServiceImpl implements GymFlipFitNotificationService {
	
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
		notification.setId(counter.incrementAndGet());
		notifications.add(notification);
        return notification;

	}

}
