
package com.lti.flipfit.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.constant.Status;
import com.lti.flipfit.dto.CreateCenterRequest;
import com.lti.flipfit.entity.GymFlipFitAddress;
import com.lti.flipfit.entity.GymFlipFitCenter;
import com.lti.flipfit.exception.GymCenterNotFoundException;
import com.lti.flipfit.exception.ResourceNotFoundException;
import com.lti.flipfit.repository.GymFlipFitCenterRepository;

import jakarta.transaction.Transactional;

/**
 * Service implementation for managing Gym/Center entities.
 * <p>
 * This class provides CRUD-style operations and status updates for
 * {@link GymFlipFitCenter}, delegating persistence to
 * {@link GymFlipFitCenterRepository}. It also handles common error
 * scenarios (e.g., not-found) via domain-specific exceptions.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>List all centers or only ACTIVE centers.</li>
 *   <li>Fetch a center by id and handle not-found cases.</li>
 *   <li>Create and update centers from DTO/domain payloads.</li>
 *   <li>Delete centers and update center status atomically.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Uses {@link jakarta.transaction.Transactional} for status updates to ensure atomicity.</li>
 *   <li>Prefer throwing domain exceptions (e.g., {@link GymCenterNotFoundException}) for clearer error mapping.</li>
 *   <li>Consider validation in DTOs (e.g., {@code @NotBlank}, {@code @NotNull}) to avoid invalid state entering the service.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
public class GymFlipFitCenterServiceImpl implements GymFlipFitCenterService {

	@Autowired
	private GymFlipFitCenterRepository centerRepository;


    /**
     * Fetches all gym centers from the repository.
     * <p>
     * If you want only ACTIVE centers, switch to repository method:
     * {@code centerRepository.findByStatus(Status.ACTIVE)}.
     * </p>
     *
     * @return a non-null list of {@link GymFlipFitCenter}; may be empty
     */

	@Override
	public List<GymFlipFitCenter> findAllCenters() {
		
		List<GymFlipFitCenter> allCenters =centerRepository.findAll();
		return allCenters;
	}

/**
     * Fetches all gym centers that are currently marked {@link Status#ACTIVE}.
     *
     * @return a non-null list of ACTIVE {@link GymFlipFitCenter}; may be empty
     */

	@Override
	public List<GymFlipFitCenter> findAllActiveCenters() {
		
		List<GymFlipFitCenter> allActiveCenters =centerRepository.findByStatus(Status.ACTIVE);
		return allActiveCenters;
	}

/**
     * Finds a center by its unique id.
     * <p>
     * Wraps the result into {@link Optional} and throws {@link ResourceNotFoundException}
     * when the repository does not contain the requested id.
     * </p>
     *
     * @param id the center id to look up; must not be null
     * @return an {@link Optional} containing the center if found
     * @throws ResourceNotFoundException when no center exists with the provided id
     */

	@Override
	public Optional<GymFlipFitCenter> findCenterById(Long id) {
		return Optional.ofNullable(centerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Center not found with id: " + id)));
	}


	 /**
	     * Creates and persists a new center from the provided DTO.
	     * <p>
	     * Maps fields from {@link CreateCenterRequest} into a {@link GymFlipFitCenter} domain entity,
	     * including nested address details.
	     * </p>
	     *
	     * @param createCenterRequest the DTO containing center details to be created
	     * @return the saved {@link GymFlipFitCenter} entity
	     */

	@Override
	public GymFlipFitCenter saveCenter(CreateCenterRequest createCenterRequest) {
		GymFlipFitCenter gymFlipFitCenter = new GymFlipFitCenter();
		gymFlipFitCenter.setName(createCenterRequest.getName());
		gymFlipFitCenter.setEmailId(createCenterRequest.getEmailId());
		gymFlipFitCenter.setPhoneNo(createCenterRequest.getPhoneNo());

		// Map address details from request (prefer a mapper if this grows)
		GymFlipFitAddress address = new GymFlipFitAddress();
		address.setId(createCenterRequest.getAddress().getId());
		address.setCity(createCenterRequest.getAddress().getCity());
		address.setCountry(createCenterRequest.getAddress().getCountry());
		address.setLine1(createCenterRequest.getAddress().getLine1());
		address.setLine2(createCenterRequest.getAddress().getLine2());
		address.setState(createCenterRequest.getAddress().getState());
		address.setPostalCode(createCenterRequest.getAddress().getPostalCode());
		gymFlipFitCenter.setAddress(address);

		gymFlipFitCenter.setOwnerId(createCenterRequest.getOwnerId());

		return centerRepository.save(gymFlipFitCenter);
	}


	 /**
	     * Updates an existing center by id using the provided payload.
	     * <p>
	     * Loads the center, applies updates to select fields (name, owner, phone,
	     * email, status), and persists changes. If address is provided, replaces it.
	     * </p>
	     *
	     * @param id               the id of the center to update
	     * @param gymFlipFitCenter the payload containing updated fields
	     * @return the updated and saved {@link GymFlipFitCenter} entity
	     * @throws GymCenterNotFoundException when the center id does not exist
	     */

	@Override
	public GymFlipFitCenter updateCenter(Long id, GymFlipFitCenter gymFlipFitCenter) {
		return findCenterById(id).map(center -> {
			center.setName(gymFlipFitCenter.getName());
			center.setOwnerId(gymFlipFitCenter.getOwnerId());
			center.setPhoneNo(gymFlipFitCenter.getPhoneNo());
			center.setStatus(gymFlipFitCenter.getStatus());
			center.setEmailId(gymFlipFitCenter.getEmailId());

			// Update address if provided
			if (gymFlipFitCenter.getAddress() != null) {
				center.setAddress(gymFlipFitCenter.getAddress());
			}

			return centerRepository.save(center);
		}).orElseThrow(() -> new GymCenterNotFoundException("Gym center not found with id: " + id));
	}


	 /**
	     * Deletes a center by its id.
	     * <p>
	     * Throws {@link GymCenterNotFoundException} if the id does not exist.
	     * Returns a simple confirmation message on success.
	     * </p>
	     *
	     * @param id the id of the center to delete
	     * @return a confirmation message indicating successful deletion
	     * @throws GymCenterNotFoundException when the center id does not exist
	     */

	@Override
	public String deleteCenter(Long id) {
		if (!centerRepository.existsById(id)) {
			throw new GymCenterNotFoundException("Gym center not found with id: " + id);
		}
		centerRepository.deleteById(id);
		return "Center deleted successfully with id: " + id;
	}

	  /**
	     * Atomically updates the center's status by id.
	     * <p>
	     * Uses a repository-level UPDATE for efficiency, then reloads the entity to
	     * return the latest state. Throws exceptions when the update affects no rows
	     * or the entity cannot be reloaded (should not happen under normal conditions).
	     * </p>
	     *
	     * @param id     the center id whose status must be updated
	     * @param status the new {@link Status} to apply
	     * @return the freshly loaded, updated {@link GymFlipFitCenter} entity
	     * @throws IllegalArgumentException if no center matches the provided id
	     * @throws IllegalStateException    if the center cannot be reloaded post-update
	     */

	@Transactional
    public GymFlipFitCenter updateCenterStatusById(Long id, Status status) {
        int updated = centerRepository.updateStatusById(id, status);
        if (updated == 0) {
            throw new IllegalArgumentException("No Gym Center found with id=" + id);
        }
        // Load fresh entity (so you return the latest state)
        return centerRepository.findById(id).orElseThrow(() ->
            new IllegalStateException("Updated Center not found: id=" + id));
    }

}
