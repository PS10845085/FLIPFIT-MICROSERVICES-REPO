
package com.lti.flipfit.services;

import java.util.Date;
import java.util.List;

import com.lti.flipfit.entity.GymFlipFitScheduler;

public interface GymFlipFitSchedulerService {

	public List<GymFlipFitScheduler> getSchedules(Date startTime, Date endTime);

	public GymFlipFitScheduler scheduleSlot(GymFlipFitScheduler scheduler);

	public GymFlipFitScheduler updateSchedule(GymFlipFitScheduler scheduler, int scheduleId);

	public GymFlipFitScheduler removeSchedule(int scheduleId);
}
