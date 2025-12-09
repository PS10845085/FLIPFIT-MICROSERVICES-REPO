package com.lti.flipfit.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.constants.ConstantLists;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymUserRepository;
import jakarta.transaction.Transactional;

/**
 * Service implementation for user administration operations.
 * <p>
 * This service coordinates with {@link GymUserRepository} to update and retrieve
 * {@link GymUser} entities. Methods are transactional to ensure atomic writes
 * and consistent reads.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Update a user's status by id (e.g., ACTIVE, INACTIVE, PENDING).</li>
 *   <li>Return the freshly loaded user after mutation to ensure latest state.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider validating the incoming status against allowed values to avoid invalid state.</li>
 *   <li>Prefer domain-specific exceptions for not-found scenarios if you want fine-grained error mapping.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
@Transactional
public class GymFlipFitUserServiceImpl implements GymFlipFitUserService {

	@Autowired
	private GymUserRepository userRepository;

    /**
     * Constructs the {@code GymFlipFitUserServiceImpl} with the required repository dependency.
     *
     * @param userRepository repository providing access to {@link GymUser} entities
     */

	public GymFlipFitUserServiceImpl(GymUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	

    /**
     * Updates a user's status by id and returns the freshly loaded entity.
     * <p>
     * Performs a repository-level update to set the status and then reloads the entity
     * to return the latest state to callers. Throws {@link IllegalArgumentException}
     * if no record is updated (i.e., no matching user id), and {@link IllegalStateException}
     * if the entity cannot be reloaded (unexpected).
     * </p>
     *
     * <h3>Transactional Behavior</h3>
     * <ul>
     *   <li>Method is transactional to ensure atomic mutation and consistent subsequent read.</li>
     * </ul>
     *
     * <h3>Valid Values</h3>
     * <ul>
     *   <li>The {@code status} should match your domain’s allowed values (e.g., {@code ACTIVE}, {@code INACTIVE}, {@code PENDING}).</li>
     * </ul>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * GymUser updated = userService.updateUserStatusById(3L, "ACTIVE");
     * }</pre>
     *
     * @param id     the user id whose status should be updated
     * @param status the new status value; must be a valid domain status string
     * @return the freshly loaded, updated {@link GymUser}
     * @throws IllegalArgumentException if no user exists for the provided {@code id}
     * @throws IllegalStateException    if the user cannot be reloaded after update
     */

@Transactional
    public GymUser updateUserStatusById(Long id, String status) {
        int updated = userRepository.updateStatusById(id, status);
        if (updated == 0) {
            throw new IllegalArgumentException("No GymUser found with id=" + id);
        }
        // Load fresh entity (so you return the latest state)
        return userRepository.findById(id).orElseThrow(() ->
            new IllegalStateException("Updated user not found: id=" + id));
    }

 


	
}
