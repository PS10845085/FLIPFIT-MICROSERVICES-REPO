
package com.lti.flipfit.service;

import java.util.List;
import com.lti.flipfit.entity.GymFlipFitScheduler;

public interface GymFlipFitSchedulerService {
	List<GymFlipFitScheduler> getAllSchedules();

	GymFlipFitScheduler getScheduleById(Long id);

	GymFlipFitScheduler createSchedule(GymFlipFitScheduler scheduler);

	GymFlipFitScheduler updateSchedule(Long id, GymFlipFitScheduler scheduler);

	String deleteSchedule(Long id);
}
