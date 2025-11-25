/**
 * 
 */
package com.lti.flipfit.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lti.flipfit.entity.GymFlipFitSlot;

/**
 * 
 */
public interface GymFlipFitSlotService {
	public GymFlipFitSlot createSlot(GymFlipFitSlot slot);
    public List<GymFlipFitSlot> getSlotsForDate(Long centerId, Date date);
    public Optional<GymFlipFitSlot> getSlotById(Long slotId);
	/**
	 * Update Slot Info.
	 *
	 * @param slot
	 * @return updated slot
	 */
	public GymFlipFitSlot updateSlot(GymFlipFitSlot slot);
	/**
	 * Update Slot Info.
	 *
	 * @param slot
	 * @return updated slot
	 */
	GymFlipFitSlot bookSlot(GymFlipFitSlot slot);
}
