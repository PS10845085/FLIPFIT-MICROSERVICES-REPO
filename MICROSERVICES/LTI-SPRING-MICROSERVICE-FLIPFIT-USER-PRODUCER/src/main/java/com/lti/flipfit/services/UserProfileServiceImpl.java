package com.lti.flipfit.services;


import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.repository.GymCustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation responsible for retrieving user profile information.
 * <p>
 * This class interacts with the persistence layer via {@link GymCustomerRepository}
 * to fetch a {@link GymCustomer} profile for a given user id, ensuring the record
 * is in the expected status (e.g., <code>ACTIVE</code>) and includes related address
 * data when needed.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Fetch a customer profile by the linked {@code userId}.</li>
 *   <li>Ensure transactional read consistency using {@link Transactional} with {@code readOnly=true}.</li>
 *   <li>Throw meaningful exceptions when a profile is not found.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Uses a repository method that eagerly fetches the address (e.g., via a join) to avoid N+1 lookups.</li>
 *   <li>Currently filters by <code>status = ACTIVE</code>; adjust as business rules evolve.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final GymCustomerRepository gymCustomerRepository;

    /**
        * Constructs the {@code UserProfileServiceImpl} with the required repository dependency.
        *
        * @param gymCustomerRepository repository providing access to {@link GymCustomer} entities
        */

    public UserProfileServiceImpl(GymCustomerRepository gymCustomerRepository) {
        this.gymCustomerRepository = gymCustomerRepository;
    }

/* Retrieves the profile of a user by their unique {@code userId}.
     * <p>
     * The underlying repository query enforces that the customer record is in
     * {@code ACTIVE} status and loads the associated address eagerly.
     * </p>
     *
     * <h3>Transactional Behavior</h3>
     * <ul>
     *   <li>Marked as read-only to optimize persistence context and avoid unnecessary flushing.</li>
     * </ul>
     *
     * <h3>Exceptions</h3>
     * <ul>
     *   <li>{@link IllegalArgumentException} if the customer profile for the given {@code userId} is not found.</li>
     * </ul>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * GymCustomer customer = userProfileService.getUserProfileById(123L);
     * }</pre>
     *
     * @param userId the id of the authenticated application user (not GymCustomer.id, but the linked user id)
     * @return the {@link GymCustomer} profile, including address information
     * @throws IllegalArgumentException when no active profile exists for the provided {@code userId}
     */

    @Override
    @Transactional(readOnly = true)
    public GymCustomer getUserProfileById(Long userId) {
    	
    	 // Query repository for ACTIVE customer linked to the given userId, including address.
        GymCustomer customer = gymCustomerRepository.findByUserIdAndStatusWithAddress(userId, "ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("User not found for userId: " + userId));
        
     // Return the domain entity as-is; controller/service above can map/serialize as needed.
	    return customer;
	    
    }

}
