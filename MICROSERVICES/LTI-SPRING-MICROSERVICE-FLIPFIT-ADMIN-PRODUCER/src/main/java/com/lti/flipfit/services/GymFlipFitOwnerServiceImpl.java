package com.lti.flipfit.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.constants.ConstantLists;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymOwnerRepository;
import com.lti.flipfit.repository.GymUserRepository;
import jakarta.transaction.Transactional;

/**
 * Service implementation responsible for owner-related administration operations.
 * <p>
 * This service interacts with {@link GymOwnerRepository} to retrieve {@link GymOwner}
 * entities and is designed to be transactional for consistent reads/writes.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Fetch all owners filtered by the OWNER role id.</li>
 *   <li>Return domain entities (e.g., including eager address details if repository provides).</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Prefer domain-specific exceptions (e.g., {@code OwnersNotFoundException}) over generic {@link IllegalArgumentException} for clearer error mapping.</li>
 *   <li>Consider pagination/filters in case the dataset becomes large.</li>
 *   <li>Use a constant (e.g., {@link ConstantLists#OWNER_ROLE_ID}) rather than a magic number for role id checks.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
@Transactional
public class GymFlipFitOwnerServiceImpl implements GymFlipFitOwnerService {

	@Autowired
	private GymOwnerRepository ownerRepository;

    /**
     * Constructs the {@code GymFlipFitOwnerServiceImpl} with repository dependency.
     *
     * @param ownerRepository repository providing access to {@link GymOwner} entities
     */

	public GymFlipFitOwnerServiceImpl(GymOwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}


    /**
     * Retrieves the list of owners filtered by the OWNER role id and includes address information.
     * <p>
     * Delegates to {@link GymOwnerRepository#findByRoleIdWithAddress(Integer)} to fetch owners
     * belonging to {@link ConstantLists#OWNER_ROLE_ID}, and throws {@link IllegalArgumentException}
     * if the repository returns empty data.
     * </p>
     *
     * <h3>Transactional Behavior</h3>
     * <ul>
     *   <li>Method executes within a transaction to ensure consistent reads; consider {@code readOnly=true} if no writes involved.</li>
     * </ul>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * List<GymOwner> owners = ownerService.getAllOwnerList();
     * owners.forEach(o -> System.out.println(o.getFirstname()));
     * }</pre>
     *
     * @return a non-null list of {@link GymOwner} entities; never null
     * @throws IllegalArgumentException if no owners are found for the configured role id
     */

	
	 @Transactional()
	    public List<GymOwner> getAllOwnerList() {
		 
		 List<GymOwner> owner = ownerRepository.findByRoleIdWithAddress(ConstantLists.OWNER_ROLE_ID)
	                .orElseThrow(() -> new IllegalArgumentException("User not found for RoleId: " + 3));
	        
		    // Return success response
		    return owner;
		    
	    }


	
}
