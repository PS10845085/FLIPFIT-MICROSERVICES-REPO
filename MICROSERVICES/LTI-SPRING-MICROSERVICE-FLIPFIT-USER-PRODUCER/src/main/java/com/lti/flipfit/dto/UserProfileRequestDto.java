package com.lti.flipfit.dto;

import jakarta.validation.constraints.NotNull;

public class UserProfileRequestDto {

    @NotNull(message = "userid is required")
    private Long userid;

    public UserProfileRequestDto() {}

    public UserProfileRequestDto(Long userid) {
        this.userid = userid;
    }

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}


}

