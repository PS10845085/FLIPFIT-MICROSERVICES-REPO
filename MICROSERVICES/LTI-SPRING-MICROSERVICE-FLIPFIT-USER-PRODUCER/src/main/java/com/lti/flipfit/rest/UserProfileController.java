
package com.lti.flipfit.rest;


import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.dto.UserProfileRequestDto;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.services.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Profile", description = "Endpoints to fetch user profile")
@SecurityRequirement(name = "BearerAuth") // requires JWT in Swagger UI
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Operation(summary = "Get user profile by id (POST)")
    @PostMapping("/profile")
    public ResponseEntity<RestApiResponse> getProfile(@Valid @RequestBody UserProfileRequestDto req) {
    	GymCustomer profile = userProfileService.getUserProfileById(req.getUserid());
      
        RestApiResponse response = new RestApiResponse("SUCCESS", "User profile fetched successfully ", profile);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
