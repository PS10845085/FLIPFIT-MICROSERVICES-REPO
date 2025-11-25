package com.lti.flipfit.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitOwner;
import com.lti.flipfit.entity.GymFlipFitWaitList;


/**
 * Service implementation for managing waitlists in the GymFlipFit application.
 *
 * <p>This service handles operations such as joining a waitlist, promoting the next user,
 * and listing all users in a waitlist for a given slot and center.</p>
 *
**/
@Service
public class GymFlipFitWaitlistServiceImpl implements GymFlipFitWaitlistService {

	private final Map<Long, java.util.List<GymFlipFitWaitList>> waitlists = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();



/**
 * Service implementation for managing waitlists in the GymFlipFit application.
 *
 * <p>This service handles operations such as joining a waitlist, promoting the next user,
 * and listing all users in a waitlist for a given slot and center.</p>
 *
 /**
 * Adds a user to the waitlist for a specific center and slot.
 *
 * @param userId   the ID of the user joining the waitlist
 * @param centerId the ID of the gym center
 * @param slotId   the ID of the slot
 *
 @return the {@link GymFlipFitWaitList} entry created for the user
 */


public GymFlipFitWaitList join(Long userId, Long centerId, Long slotId) {
        List<GymFlipFitWaitList> list = waitlists.computeIfAbsent(slotId, k -> new java.util.ArrayList<>());
        GymFlipFitWaitList wait = new GymFlipFitWaitList();
        wait.setId(counter.incrementAndGet());
        wait.setUserId(userId);
        wait.setCenterId(centerId);
        wait.setSlotId(slotId);
        wait.setPosition(list.size() + 1);
        wait.setStatus("WAITING");
        wait.setCreatedAt(new Date());
        list.add(wait);
        return wait;
    }

    /**
     * Promotes the next user in the waitlist for a given center and slot.
     *
     * <p>The first user with status "WAITING" is promoted to "PROMOTED".</p>
     *
     * @param centerId the ID of the gym center
     * @param slotId   the ID of the slot
     * @return an {@link Optional} containing the promoted {@link GymFlipFitWaitList} entry, or empty if none found
     */

    public Optional<GymFlipFitWaitList> promoteNext(Long centerId, Long slotId) {
        List<GymFlipFitWaitList> list = waitlists.getOrDefault(slotId, new java.util.ArrayList<>());
        return list.stream().filter(wait -> wait.getStatus().equals("WAITING"))
                .sorted(java.util.Comparator.comparingInt(GymFlipFitWaitList::getPosition)).findFirst().map(map -> {
                    map.setStatus("PROMOTED");
                    map.setPromotedAt(new Date());
                    return map;
                });
    }


    /**
     * Retrieves the list of users in the waitlist for a given center and slot.
     *
     * @param centerId the ID of the gym center
     * @param slotId   the ID of the slot
     * @return a list of {@link GymFlipFitWaitList} entries for the specified slot
     */

    public List<GymFlipFitWaitList> list(Long centerId, Long slotId) {
        return new ArrayList<>(waitlists.getOrDefault(slotId, new ArrayList<>()));
    }

}
