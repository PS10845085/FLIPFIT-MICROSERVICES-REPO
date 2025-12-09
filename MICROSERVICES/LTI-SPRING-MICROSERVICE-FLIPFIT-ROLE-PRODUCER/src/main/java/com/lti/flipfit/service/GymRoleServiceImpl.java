
package com.lti.flipfit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.flipfit.entity.GymRole;
import com.lti.flipfit.repository.GymRoleRepository;
import com.lti.flipfit.service.GymRoleService;

/**
 * Service implementation for role metadata operations.
 * <p>
 * This service is responsible for fetching role entities (e.g., OWNER, ADMIN, CUSTOMER)
 * from the persistence layer via {@link GymRoleRepository}. It is designed to be read-only
 * for most operations, given that role definitions typically change infrequently.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Provide a read-only API to list all available roles.</li>
 *   <li>Delegate data access to {@link GymRoleRepository}.</li>
 *   <li>Optionally support different fetching strategies (derived query, native SQL) based on repository methods.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Marked as a Spring {@link Service} for DI and component scanning.</li>
 *   <li>Methods that read data are annotated with {@link Transactional} and {@code readOnly=true} to optimize performance.</li>
 *   <li>Consider caching results (e.g., with {@code @Cacheable("roles")}) since roles are relatively static.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
public class GymRoleServiceImpl implements GymRoleService {

    private final GymRoleRepository gymRoleRepository;

/**
     * Constructs the {@code GymRoleServiceImpl} with required repository dependency.
     *
     * @param gymRoleRepository the repository used to access {@link GymRole} entities
     */

    public GymRoleServiceImpl(GymRoleRepository gymRoleRepository) {
        this.gymRoleRepository = gymRoleRepository;
    }

    /**
        * Retrieves all roles available in the system in an ascending order by role name.
        * <p>
        * Uses a JPA derived query method {@link GymRoleRepository#findAllByOrderByRoleNameAsc()}
        * to fetch roles sorted by their name. If you prefer a native SQL approach for
        * database-specific sorting or performance characteristics, you can switch to
        * a repository method that executes a native query (see commented alternative).
        * </p>
        *
        * <h3>Transactional Behavior</h3>
        * <ul>
        *   <li>Marked {@code readOnly=true} to avoid unnecessary write overhead and improve query performance.</li>
        * </ul>
        *
        * <h3>Returns</h3>
        * <ul>
        *   <li>A non-null {@link List} of {@link GymRole} entities. The list may be empty if no roles are configured.</li>
        * </ul>
        *
        * <h3>Example</h3>
        * <pre>{@code
        * List<GymRole> roles = gymRoleService.getAllRoles();
        * roles.forEach(r -> System.out.println(r.getRoleName()));
        * }</pre>
        *
        * @return a list of {@link GymRole} sorted by role name in ascending order
        */

    @Override
    @Transactional(readOnly = true)
    public List<GymRole> getAllRoles() {
        // Option 1: JPA derived method with ordering
        return gymRoleRepository.findAllByOrderByRoleNameAsc();

        // Option 2 (alternative): Native SQL
        // return gymRoleRepository.findAllNative();
    }
}
