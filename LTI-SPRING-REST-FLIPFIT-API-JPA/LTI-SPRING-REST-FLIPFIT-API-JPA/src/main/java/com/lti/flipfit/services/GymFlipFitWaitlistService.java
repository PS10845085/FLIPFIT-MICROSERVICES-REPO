/**
 * 
 */
package com.lti.flipfit.services;

import java.util.List;
import java.util.Optional;

import com.lti.flipfit.entity.GymFlipFitWaitList;

/**
 * 
 */
public interface GymFlipFitWaitlistService {

	public GymFlipFitWaitList join(Long userId, Long centerId, Long slotId);

	public Optional<GymFlipFitWaitList> promoteNext(Long centerId, Long slotId);

	public List<GymFlipFitWaitList> list(Long centerId, Long slotId);
}
