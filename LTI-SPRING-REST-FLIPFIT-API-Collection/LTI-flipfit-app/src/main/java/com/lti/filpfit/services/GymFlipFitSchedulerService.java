
package com.lti.filpfit.services;

import java.util.Date;
import java.util.List;

import com.lti.filpfit.beans.GymFlipFitScheduler;

public interface GymFlipFitSchedulerService {

	public List<GymFlipFitScheduler> getSchedules(Date startTime, Date endTime);

	public GymFlipFitScheduler scheduleSlot(GymFlipFitScheduler scheduler);

	public GymFlipFitScheduler updateSchedule(GymFlipFitScheduler scheduler, int scheduleId);

	public GymFlipFitScheduler removeSchedule(int scheduleId);
}
