
package com.lti.flipfit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.dto.CreateCenterRequest;
import com.lti.flipfit.entity.GymFlipFitAddress;
import com.lti.flipfit.entity.GymFlipFitCenter;
import com.lti.flipfit.exception.GymCenterNotFoundException;
import com.lti.flipfit.exception.ResourceNotFoundException;
import com.lti.flipfit.repository.GymFlipFitCenterRepository;

@Service
public class GymFlipFitCenterServiceImpl implements GymFlipFitCenterService {

	@Autowired
	private GymFlipFitCenterRepository centerRepository;

	// Fetch all gym centers
	@Override
	public List<GymFlipFitCenter> findAllCenters() {
		return centerRepository.findAll();
	}

	// Find a center by ID or throw exception if not found
	@Override
	public Optional<GymFlipFitCenter> findCenterById(Long id) {
		return Optional.ofNullable(centerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Center not found with id: " + id)));
	}

	// Save a new gym center using request DTO
	@Override
	public GymFlipFitCenter saveCenter(CreateCenterRequest createCenterRequest) {
		GymFlipFitCenter gymFlipFitCenter = new GymFlipFitCenter();
		gymFlipFitCenter.setName(createCenterRequest.getName());
		gymFlipFitCenter.setEmailId(createCenterRequest.getEmailId());
		gymFlipFitCenter.setPhoneNo(createCenterRequest.getPhoneNo());

		// Map address details from request
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

	// Update an existing center by ID
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

	// Delete a center by ID
	@Override
	public String deleteCenter(Long id) {
		if (!centerRepository.existsById(id)) {
			throw new GymCenterNotFoundException("Gym center not found with id: " + id);
		}
		centerRepository.deleteById(id);
		return "Center deleted successfully with id: " + id;
	}
}
