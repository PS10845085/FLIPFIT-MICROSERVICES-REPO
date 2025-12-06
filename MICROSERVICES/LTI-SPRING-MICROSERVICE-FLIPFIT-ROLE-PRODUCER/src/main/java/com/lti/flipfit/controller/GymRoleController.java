
package com.lti.flipfit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymRole;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.service.GymRoleService;

@RestController
@RequestMapping("/api/roles")
public class GymRoleController {

    private final GymRoleService gymRoleService;

    public GymRoleController(GymRoleService gymRoleService) {
        this.gymRoleService = gymRoleService;
    }

    @GetMapping
    public ResponseEntity<RestApiResponse> getAllRoles() {
        List<GymRole> roles = gymRoleService.getAllRoles();
        RestApiResponse response = new RestApiResponse("SUCCESS", "Roles fetched successfully !", roles);

		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
