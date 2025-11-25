package com.lti.flipfit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitCenter;

@Service
public class GymFlipFitCenterServiceImpl implements GymFlipFitCenterService {

	private final List<GymFlipFitCenter> centers = new ArrayList<>();
	private final AtomicLong counter = new AtomicLong();
 
	@Override
	public List<GymFlipFitCenter> findAllCenters() {
		return new ArrayList<>(centers);
	}
 
	@Override
	public Optional<GymFlipFitCenter> findCenterById(Long id) {
		return centers.stream().filter(c -> c.getId().equals(id)).findFirst();
 
	}
 
	@Override
	public GymFlipFitCenter saveCenter(GymFlipFitCenter gymFlipFitCenter) {
		gymFlipFitCenter.setId(counter.incrementAndGet());
		centers.add(gymFlipFitCenter);
		return gymFlipFitCenter;
	}
 
	@Override
	public GymFlipFitCenter updateCenter(Long id, GymFlipFitCenter gymFlipFitCenter) {
		return findCenterById(id).map(center -> {
			// booking.setBookedAt(gymFlipFitCenter.getBookedAt());
			center.setId(gymFlipFitCenter.getId());
			center.setName(gymFlipFitCenter.getName());
			center.setOwner(gymFlipFitCenter.getOwnerId());
			center.setPhoneNo(gymFlipFitCenter.getPhoneNo());
			center.setStatus(gymFlipFitCenter.getStatus());
			center.setEmailId(gymFlipFitCenter.getEmailId());
			center.setAdmin(gymFlipFitCenter.getAdminId());
			center.setAddress(gymFlipFitCenter.getAddress());
 
			return center;
		}).orElse(null);
	}
 
	@Override
	public void deleteCenter(Long id) {
		centers.removeIf(c -> c.getId().equals(id));
	}

}
