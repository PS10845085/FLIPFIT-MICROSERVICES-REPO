
package com.lti.flipfit.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitSlot;
import com.lti.flipfit.repository.GymFlipFitSlotRepository;

@Service
public class GymFlipFitSlotServiceImpl implements GymFlipFitSlotService {

	private static final Logger logger = LoggerFactory.getLogger(GymFlipFitSlotServiceImpl.class);
	private final GymFlipFitSlotRepository slotRepository;

	public GymFlipFitSlotServiceImpl(GymFlipFitSlotRepository slotRepository) {
		this.slotRepository = slotRepository;
	}

	/** Fetch all slots */
	@Override
	public List<GymFlipFitSlot> getAllSlots() {
		logger.info("Fetching all slots");
		return slotRepository.findAll();
	}

	/** Fetch slot by ID */
	@Override
	public GymFlipFitSlot getSlotById(Long id) {
		logger.info("Fetching slot with ID: {}", id);
		return slotRepository.findById(id).orElseThrow(() -> {
			logger.warn("Slot not found with ID: {}", id);
			return new RuntimeException("Slot not found");
		});
	}

	/** Create a new slot */
	@Override
	public GymFlipFitSlot createSlot(GymFlipFitSlot slot) {
		logger.info("Creating new slot: {}", slot);
		return slotRepository.save(slot);
	}

	/** Update an existing slot */
	@Override
	public GymFlipFitSlot updateSlot(Long id, GymFlipFitSlot slot) {
		logger.info("Updating slot with ID: {}", id);
		GymFlipFitSlot existingSlot = getSlotById(id);
		existingSlot.setStartTime(slot.getStartTime());
		existingSlot.setEndTime(slot.getEndTime());
		existingSlot.setCapacity(slot.getCapacity());
		existingSlot.setBookedCount(slot.getBookedCount());
		existingSlot.setSlotDate(slot.getSlotDate());
		existingSlot.setStatus(slot.getStatus());
		return slotRepository.save(existingSlot);
	}

	/** Delete a slot */
	@Override
	public String deleteSlot(Long id) {
		logger.info("Deleting slot with ID: {}", id);
		if (!slotRepository.existsById(id)) {
			logger.error("Slot not found for deletion with ID: {}", id);
			throw new RuntimeException("Slot not found");
		}
		slotRepository.deleteById(id);
		return "Slot deleted successfully with ID: " + id;
	}
}
