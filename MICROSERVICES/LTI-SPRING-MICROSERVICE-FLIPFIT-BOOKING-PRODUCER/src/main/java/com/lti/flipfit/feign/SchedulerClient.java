package com.lti.flipfit.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lti.flipfit.entity.GymFlipFitScheduler;

@FeignClient(name = "Scheduler-Service", url = "http://localhost:8083/api")
public interface SchedulerClient {

	/** Get schedule by ID */
	@RequestMapping(value = "/getScheduleById/{id}", method = RequestMethod.GET)
	ResponseEntity<GymFlipFitScheduler> getSchedulerById(@PathVariable Long id);
}
