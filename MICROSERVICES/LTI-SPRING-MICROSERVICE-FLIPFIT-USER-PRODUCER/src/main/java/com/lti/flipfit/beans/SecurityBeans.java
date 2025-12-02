package com.lti.flipfit.beans;

import com.lti.flipfit.entity.GymUser; 
import com.lti.flipfit.repository.GymUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.lti.flipfit.utils.RoleUtils;

import java.util.List;

@Configuration
public class SecurityBeans {

    /**
     * UserDetailsService backed by GymUserRepository.
     *
     * It looks up a GymUser by username and maps it into Spring Security's UserDetails.
     * Roles/authorities and status mapping can be adjusted to your domain needs.
     */
    @Bean
    public UserDetailsService userDetailsService(GymUserRepository gymUserRepository) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                GymUser gymUser = gymUserRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

                // --- Status check ---
                // TODO: If you have statuses like "ACTIVE", "INACTIVE", etc., enforce here:
                if (gymUser.getStatus() != null && !"ACTIVE".equalsIgnoreCase(gymUser.getStatus())) {
                    // You can throw DisabledException to indicate account is not enabled
                    throw new DisabledException("User status is not ACTIVE");
                }

                // --- Authority mapping ---
                // You only have roleid (Long) in GymUser; map it to a role name.
                // TODO: Replace this mapping with your actual role logic or a join to a Role table.
                String roleName = RoleUtils.mapRoleIdToRoleName(gymUser.getRoleid()); // e.g., "ROLE_USER"
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

                // Build the Spring Security UserDetails object
                return org.springframework.security.core.userdetails.User
                        .withUsername(gymUser.getUsername())
                        .password(gymUser.getPassword()) // already hashed in DB (BCrypt recommended)
                        .authorities(authorities)
                        .accountExpired(false)     // TODO: wire to DB flags if available
                        .accountLocked(false)      // TODO: wire to DB flags if available
                        .credentialsExpired(false) // TODO: wire to DB flags if available
                        .disabled(false)           // control via status above
                        .build();
            }

            
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Ensure the passwords in `gymuser.password` are stored as BCrypt hashes.
        return new BCryptPasswordEncoder();
    }
}

