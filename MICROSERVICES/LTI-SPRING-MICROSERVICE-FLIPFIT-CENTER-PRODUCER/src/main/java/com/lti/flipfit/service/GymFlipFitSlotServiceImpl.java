
package com.lti.flipfit.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitSlot;
import com.lti.flipfit.repository.GymFlipFitSlotRepository;

/**
 * Service implementation that handles CRUD operations for {@link GymFlipFitSlot}.
 * <p>
 * This class coordinates with the persistence layer via {@link GymFlipFitSlotRepository}
 * and logs all operations for traceability and diagnostics.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>List all slots or retrieve a slot by its id.</li>
 *   <li>Create new slots and update existing ones.</li>
 *   <li>Delete slots by id while ensuring proper error handling.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider introducing domain-specific exceptions (e.g., {@code SlotNotFoundException}) instead of generic {@link RuntimeException}.</li>
 *   <li>Validation is recommended for create/update payloads (e.g., DTO + {@code @Valid}).</li>
 *   <li>Depending on business rules, you may enforce constraints (e.g., startTime &lt; endTime, capacity &gt;= bookedCount, non-past slotDate).</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
public class GymFlipFitSlotServiceImpl implements GymFlipFitSlotService {

	private static final Logger logger = LoggerFactory.getLogger(GymFlipFitSlotServiceImpl.class);
	private final GymFlipFitSlotRepository slotRepository;

	 /**
	     * Constructs the {@code GymFlipFitSlotServiceImpl} with required repository dependency.
	     *
	     * @param slotRepository the repository providing access to {@link GymFlipFitSlot} entities
	     */

	public GymFlipFitSlotServiceImpl(GymFlipFitSlotRepository slotRepository) {
		this.slotRepository = slotRepository;
	}


    /**
     * Retrieves all slots present in the system.
     * <p>
     * Returns an immutable list in some JPA implementations; do not mutate it directly.
     * </p>
     *
     * @return a non-null list of {@link GymFlipFitSlot}; may be empty
     */
	
	@Override
	public List<GymFlipFitSlot> getAllSlots() {
		logger.info("Fetching all slots");
		return slotRepository.findAll();
	}


	  /**
	     * Retrieves a single slot by its unique identifier.
	     * <p>
	     * Logs a warning and throws a {@link RuntimeException} if the slot is not found.
	     * Prefer replacing with a domain-specific exception for clarity.
	     * </p>
	     *
	     * @param id the slot id to fetch
	     * @return the {@link GymFlipFitSlot} with the provided id
	     * @throws RuntimeException if the slot does not exist
	     */

	@Override
	public GymFlipFitSlot getSlotById(Long id) {
		logger.info("Fetching slot with ID: {}", id);
		return slotRepository.findById(id).orElseThrow(() -> {
			logger.warn("Slot not found with ID: {}", id);
			return new RuntimeException("Slot not found");
		});
	}


	 /**
	     * Creates and persists a new slot.
	     * <p>
	     * Consider validating the payload (e.g., capacity &gt;= 0, start/end times valid) before persisting.
	     * </p>
	     *
	     * @param slot the slot entity to create
	     * @return the created {@link GymFlipFitSlot} entity
	     */

	@Override
	public GymFlipFitSlot createSlot(GymFlipFitSlot slot) {
		logger.info("Creating new slot: {}", slot);
		return slotRepository.save(slot);
	}


	 /**
	     * Updates an existing slot identified by {@code id} using fields from the provided payload.
	     * <p>
	     * Fields updated: startTime, endTime, capacity, bookedCount, slotDate, status.
	     * Throws {@link RuntimeException} when the slot is not found.
	     * </p>
	     *
	     * @param id   the id of the slot to update
	     * @param slot the payload containing fields to update
	     * @return the updated {@link GymFlipFitSlot} entity
	     * @throws RuntimeException if the slot does not exist
	     */

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


	 /**
	     * Deletes a slot by its id.
	     * <p>
	     * Returns a simple confirmation message on success. For REST conventions,
	     * controllers may prefer returning HTTP 204 No Content instead of a message body.
	     * </p>
	     *
	     * @param id the id of the slot to delete
	     * @return confirmation message indicating successful deletion
	     * @throws RuntimeException if the slot is not found
	     */

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
