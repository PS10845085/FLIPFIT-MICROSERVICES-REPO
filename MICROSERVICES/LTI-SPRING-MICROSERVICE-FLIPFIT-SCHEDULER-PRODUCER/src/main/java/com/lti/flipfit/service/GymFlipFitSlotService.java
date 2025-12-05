/**
 * 
 */
package com.lti.flipfit.service;

import java.util.List;

import com.lti.flipfit.entity.GymFlipFitSlot;

public interface GymFlipFitSlotService {
    List<GymFlipFitSlot> getAllSlots();
    GymFlipFitSlot getSlotById(Long id);
    GymFlipFitSlot createSlot(GymFlipFitSlot slot);
    GymFlipFitSlot updateSlot(Long id, GymFlipFitSlot slot);
    String deleteSlot(Long id);
}
