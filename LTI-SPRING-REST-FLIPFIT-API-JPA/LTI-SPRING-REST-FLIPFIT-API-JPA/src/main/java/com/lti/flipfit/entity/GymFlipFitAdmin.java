package com.lti.flipfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "gymadmin")
public class GymFlipFitAdmin extends GymFlipFitUser{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer Id;

}
