/**
 * 
 */
package com.lti.flipfit.service;

import org.springframework.stereotype.Service;

import com.lti.flipfit.bean.GymFlipFitNotification;

@Service
public class GymFlipFitNotificationServiceImpl {

    public GymFlipFitNotification sendNotification(GymFlipFitNotification notification) {
        // Here you can add logic like saving to DB or sending via a channel
        notification.setId(null); // or generate ID
//        notification.setCreatedAt(LocalDateTime.now().toString());
        notification.setMessage("Testing");
        return notification;
    }
}
