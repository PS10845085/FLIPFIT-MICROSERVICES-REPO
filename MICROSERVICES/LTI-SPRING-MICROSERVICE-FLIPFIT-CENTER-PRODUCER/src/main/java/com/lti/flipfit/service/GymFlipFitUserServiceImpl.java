
package com.lti.flipfit.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitUser;
import com.lti.flipfit.repository.GymFlipFitUserRepository;

@Service
public class GymFlipFitUserServiceImpl implements GymFlipFitUserService {

	private static final Logger logger = LoggerFactory.getLogger(GymFlipFitUserServiceImpl.class);
	private final GymFlipFitUserRepository userRepository;

	public GymFlipFitUserServiceImpl(GymFlipFitUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/** Fetch all users */
	@Override
	public List<GymFlipFitUser> getAllUsers() {
		logger.info("Fetching all users");
		return userRepository.findAll();
	}

	/** Fetch user by ID */
	@Override
	public GymFlipFitUser getUserById(Long id) {
		logger.info("Fetching user with ID: {}", id);
		return userRepository.findById(id).orElseThrow(() -> {
			logger.warn("User not found with ID: {}", id);
			return new RuntimeException("User not found");
		});
	}

	/** Create a new user */
	@Override
	public GymFlipFitUser createUser(GymFlipFitUser user) {
		logger.info("Creating new user: {}", user.getEmail());
		return userRepository.save(user);
	}

	/** Update an existing user */
	@Override
	public GymFlipFitUser updateUser(Long id, GymFlipFitUser user) {
		logger.info("Updating user with ID: {}", id);
		GymFlipFitUser existingUser = getUserById(id);
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setPhoneNo(user.getPhoneNo());
		existingUser.setStatus(user.getStatus());
		existingUser.setAddresses(user.getAddresses());
		existingUser.setUpdatedAt(new java.util.Date());
		return userRepository.save(existingUser);
	}

	/** Delete a user */
	@Override
	public String deleteUser(Long id) {
		logger.info("Deleting user with ID: {}", id);
		if (!userRepository.existsById(id)) {
			logger.error("User not found for deletion with ID: {}", id);
			throw new RuntimeException("User not found");
		}
		userRepository.deleteById(id);
		return "User deleted successfully with ID: " + id;
	}
}
