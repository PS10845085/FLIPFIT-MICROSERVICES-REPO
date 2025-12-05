
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitScheduler;
import com.lti.flipfit.service.GymFlipFitSchedulerService;

@RestController
@RequestMapping("/scheduler")
public class GymFlipFitSchedulerController {

	private final GymFlipFitSchedulerService schedulerService;

	public GymFlipFitSchedulerController(GymFlipFitSchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	/** Get all schedules */
	@RequestMapping(value = "/getAllSchedule", method = RequestMethod.GET)
	public ResponseEntity<List<GymFlipFitScheduler>> getAllSchedules() {
		return ResponseEntity.ok(schedulerService.getAllSchedules());
	}

	/** Get schedule by ID */
	@RequestMapping(value = "/getScheduleById/{id}", method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitScheduler> getScheduleById(@PathVariable Long id) {
		return ResponseEntity.ok(schedulerService.getScheduleById(id));
	}

	/** Create a new schedule */
	@RequestMapping(value = "/createSchedule", method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitScheduler> createSchedule(@RequestBody GymFlipFitScheduler scheduler) {
		return new ResponseEntity<>(schedulerService.createSchedule(scheduler), HttpStatus.CREATED);
	}

	/** Update schedule */
	@RequestMapping(value = "/updateSchedule/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitScheduler> updateSchedule(@PathVariable Long id,
			@RequestBody GymFlipFitScheduler scheduler) {
		return ResponseEntity.ok(schedulerService.updateSchedule(id, scheduler));
	}

	/** Delete schedule */
	@RequestMapping(value = "/deleteScheduleById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
		return ResponseEntity.ok(schedulerService.deleteSchedule(id));
	}
}
