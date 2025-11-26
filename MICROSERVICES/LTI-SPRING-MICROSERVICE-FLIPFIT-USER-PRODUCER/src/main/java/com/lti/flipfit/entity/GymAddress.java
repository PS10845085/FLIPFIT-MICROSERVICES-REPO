/**
 * 
 */
package com.lti.flipfit.entity;
import jakarta.persistence.Column;
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
@Table(name = "gymaddress")
public class GymAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String line1;

    @Column(length = 45)
    private String line2;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String state;

    @Column(length = 45)
    private String postalCode;

    @Column(length = 45)
    private String country;

}