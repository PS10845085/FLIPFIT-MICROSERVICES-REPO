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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String postalcode;

    @Column(length = 45)
    private String country;
    
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public GymAddress(Integer id, String line1, String line2, String city, String state, String postalcode,
			String country) {
		super();
		this.id = id;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.postalcode = postalcode;
		this.country = country;
	}

	public GymAddress() {
		// TODO Auto-generated constructor stub
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

	public String getPostalCode() {
		return postalcode;
	}

	public void setPostalCode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


}