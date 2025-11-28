/**
 * 
 */
package com.lti.flipfit.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration.
 * Minimal GymUser fields + GymCustomer + Address fields.
 *
 * Usage:
 *   @PostMapping("/register")
 *   public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserDto userDto) { ... }
 */
public class UserDto {

    // ---- GymUser (minimal) ----
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Role is required")
    private Long roleid;

    // Optional: if center is part of your domain (not in gymcustomer DDL)
    private Long centerid;

    // ---- GymCustomer ----
    @NotBlank(message = "First name is required")
    private String firstname;

    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Mobile is required")
    private Long mobile;

    // Optional: customer status (defaults to PENDING in service)
    private String status;

    // ---- Address (GymAddress) ----
    @NotBlank(message = "Address line1 is required")
    private String line1;

    private String line2;

    @NotBlank(message = "City is required")
    private String city;

    private String state;

    @NotBlank(message = "Postal code is required")
    private String postalcode;

    @NotBlank(message = "Country is required")
    private String country;

    // ---- Getters & Setters ----

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleid() {
        return roleid;
    }
    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getCenterid() {
        return centerid;
    }
    public void setCenterid(Long centerid) {
        this.centerid = centerid;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobile() {
        return mobile;
    }
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
        return postalcode;
    }
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
