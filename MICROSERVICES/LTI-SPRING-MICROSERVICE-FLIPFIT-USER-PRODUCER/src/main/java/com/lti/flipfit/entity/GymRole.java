/**
 * 
 */
package com.lti.flipfit.entity;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "gymrole")
public class GymRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleid;
    private String rolename;
    private String description;
}

