
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitUser;
import com.lti.flipfit.service.GymFlipFitUserService;

@RestController
@RequestMapping("/users")
public class GymFlipFitUserController {

	private final GymFlipFitUserService userService;

	public GymFlipFitUserController(GymFlipFitUserService userService) {
		this.userService = userService;
	}

	/** Get all users */
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<GymFlipFitUser>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	/** Get user by ID */
	@RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitUser> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	/** Create a new user */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitUser> createUser(@RequestBody GymFlipFitUser user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	/** Update user */
	@RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitUser> updateUser(@PathVariable Long id, @RequestBody GymFlipFitUser user) {
		return ResponseEntity.ok(userService.updateUser(id, user));
	}

	/** Delete user */
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}
}
