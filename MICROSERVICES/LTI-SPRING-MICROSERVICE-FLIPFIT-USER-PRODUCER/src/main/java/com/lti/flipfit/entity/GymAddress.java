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
    
    private Long id;

	@Column(name = "line1")
    private String line1;
	
	@Column(name = "line2")
    private String line2;
	
	@Column(name = "city")
    private String city;
	
	@Column(name = "state")
    private String state;
	
    private String postalCode;
	
	@Column(name = "country")
    private String country;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalcode() {
		return postalCode;
	}
	public void setPostalcode(String postalcode) {
		this.postalCode = postalcode;
	}
	public String getCountry() {
		return country; 
	}
	public void setCountry(String country) {
		this.country = country;
	}

    
    
}