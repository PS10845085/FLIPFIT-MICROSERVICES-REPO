
package com.lti.flipfit.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitScheduler;
import com.lti.flipfit.repository.GymFlipFitSchedulerRepository;

@Service
public class GymFlipFitSchedulerServiceImpl implements GymFlipFitSchedulerService {

	private static final Logger logger = LoggerFactory.getLogger(GymFlipFitSchedulerServiceImpl.class);
	private final GymFlipFitSchedulerRepository schedulerRepository;

	public GymFlipFitSchedulerServiceImpl(GymFlipFitSchedulerRepository schedulerRepository) {
		this.schedulerRepository = schedulerRepository;
	}

	/** Fetch all schedules */
	@Override
	public List<GymFlipFitScheduler> getAllSchedules() {
		logger.info("Fetching all schedules");
		return schedulerRepository.findAll();
	}

	/** Fetch schedule by ID */
	@Override
	public GymFlipFitScheduler getScheduleById(Long id) {
		logger.info("Fetching schedule with ID: {}", id);
		return schedulerRepository.findById(id).orElseThrow(() -> {
			logger.warn("Schedule not found with ID: {}", id);
			return new RuntimeException("Schedule not found");
		});
	}

	/** Create a new schedule */
	@Override
	public GymFlipFitScheduler createSchedule(GymFlipFitScheduler scheduler) {
		logger.info("Creating new schedule: {}", scheduler);
		return schedulerRepository.save(scheduler);
	}

	/** Update an existing schedule */
	@Override
	public GymFlipFitScheduler updateSchedule(Long id, GymFlipFitScheduler scheduler) {
		logger.info("Updating schedule with ID: {}", id);
		GymFlipFitScheduler existingSchedule = getScheduleById(id);
		existingSchedule.setValidFrom(scheduler.getValidFrom());
		existingSchedule.setValidTill(scheduler.getValidTill());
		existingSchedule.setSlot(scheduler.getSlot());
		return schedulerRepository.save(existingSchedule);
	}

	/** Delete a schedule */
	@Override
	public String deleteSchedule(Long id) {
		logger.info("Deleting schedule with ID: {}", id);
		if (!schedulerRepository.existsById(id)) {
			logger.error("Schedule not found for deletion with ID: {}", id);
			throw new RuntimeException("Schedule not found");
		}
		schedulerRepository.deleteById(id);
		return "Schedule deleted successfully with ID: " + id;
	}
}
