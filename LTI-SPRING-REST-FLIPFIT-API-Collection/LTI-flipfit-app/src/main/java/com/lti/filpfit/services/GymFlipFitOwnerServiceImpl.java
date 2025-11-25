package com.lti.filpfit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lti.filpfit.beans.GymFlipFitOwner;
import com.lti.filpfit.beans.GymFlipFitSlot;
import com.lti.filpfit.exception.OwnerNotFoundException;


/**
 * Service implementation for managing gym owners in the GymFlipFit application.
 *
 * <p>This service provides methods to perform CRUD operations on gym owners,
 * including creating, updating, retrieving, and deleting owner records.</p>
 */
@Service
public class GymFlipFitOwnerServiceImpl implements GymFlipFitOwnerService {
	
	@Autowired
	private GymFlipFitSlotService slotService;

	private final List<GymFlipFitOwner> owners = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    /**
     * Retrieves all gym owners.
     *
     * @return a list of {@link GymFlipFitOwner} objects representing all owners
     */
    @Override
    public List<GymFlipFitOwner> findAllOwners() {
        return new ArrayList<>(owners); // Return a copy to prevent external modification
    }


    /**
     * Finds a gym owner by their unique ID.
     *
     * @param id the unique identifier of the owner
     * @return an {@link Optional} containing the {@link GymFlipFitOwner} if found, otherwise empty
     */
    @Override
    public Optional<GymFlipFitOwner> findOwnerById(Long id) {
    	return Optional.ofNullable(owners.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(()-> new OwnerNotFoundException(String.format("Owner not found for the given id: %s",id))));
    }


    /**
     * Saves a new gym owner to the system.
     *
     * <p>The owner is assigned a unique ID before being added to the list.</p>
     *
     * @param owner the {@link GymFlipFitOwner} object to save
     * @return the saved {@link GymFlipFitOwner} object
     */
    @Override
    public GymFlipFitOwner saveOwner(GymFlipFitOwner owner) {
        owner.setId(counter.incrementAndGet());
        owners.add(owner);
        return owner;
    }


    /**
     * Updates the details of an existing gym owner.
     *
     * @param id           the unique identifier of the owner to update
     * @param ownerDetails the {@link GymFlipFitOwner} object containing updated details
     * @return the updated {@link GymFlipFitOwner} object if found, otherwise null
     */
    @Override
    public GymFlipFitOwner updateOwner(Long id, GymFlipFitOwner ownerDetails) {
        return findOwnerById(id).map(owner -> {
            owner.setFirstName(ownerDetails.getFirstName());
            owner.setLastName(ownerDetails.getLastName());
            owner.setEmail(ownerDetails.getEmail());
            return owner;
        }).orElse(null);
    }


    /**
     * Deletes a gym owner by their unique ID.
     *
     * @param id the unique identifier of the owner to delete
     */
    @Override
    public void deleteOwner(Long id) {
        owners.removeIf(c -> c.getId().equals(id));
    }

    /**
	 * saveSlot
	 *
	 * Purpose: Implements saveSlot functionality.
	 * @param centerId Long
	 * @param date Date
	 * @param slots List<GymFlipFitSlot>
	 * @return List<GymFlipFitSlot>
	 */
	@Override
	public GymFlipFitSlot saveSlot(GymFlipFitSlot slots) {
		return slotService.createSlot(slots);
	}


	@Override
	public GymFlipFitSlot updateSlot(GymFlipFitSlot slot) {
		return slotService.updateSlot(slot);
	}

}
