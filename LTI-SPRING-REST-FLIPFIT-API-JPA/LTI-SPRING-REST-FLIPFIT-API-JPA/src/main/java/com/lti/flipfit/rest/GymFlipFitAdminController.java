/**
 * 
 */
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitAdmin;
import com.lti.flipfit.entity.GymFlipFitCustomer;
import com.lti.flipfit.services.GymFlipFitAdminService;

import jakarta.ws.rs.core.MediaType;

/**
 * REST controller for managing Admin, slots related operations in the GymFlipFit application.
 *
 * <p>This controller provides endpoints to retrieve customer information and other
 * administrative functionalities. All responses are returned in JSON format.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Handle HTTP requests for customer data</li>
 *     <li>Delegate business logic to {@code AdminService}</li>
 * </ul>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>GET /customer/{id}</b> - Fetch customer details by ID</li>
 * </ul>
**/

@RestController
@RequestMapping("/admin")
public class GymFlipFitAdminController {
	
	@Autowired
	private GymFlipFitAdminService adminService;
	
	/*
	 * @Method: getAllCustomer
	 * @Description: listing all the customers
	 * @MethodParameters: No Args
	 * @Exception
	 */
	@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitCustomer> getAllCustomer() {
		return adminService.findAllCustomers();
	}
	
	/*
	 * @Method: getCustomer
	 * @Description: displaying specific customer data
	 * @MethodParameters: Customer id
	 * @Exception : Id not Found
	 */
	@RequestMapping(value = "/customer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitCustomer> getCustomerById(@PathVariable Long id) {
		return adminService.findCustomerById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	/*
	 * @Method: updateCustomer
	 * @Description: updating specific customer with help of Id
	 * @MethodParameters: Customer id
	 * @Exception : Id not Found
	 */
	@RequestMapping(value = "/updateCustomer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitCustomer> updateCustomer(@PathVariable Long id, @RequestBody GymFlipFitCustomer customerDetails) {
		GymFlipFitCustomer updatedCustomer = adminService.updateCustomer(id, customerDetails);
		if (updatedCustomer != null) {
			return ResponseEntity.ok(updatedCustomer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/*
	 * @Method: getAllCustomer
	 * @Description: Fetch all customers
	 * @MethodParameters: No args
	 * @Exception 
	 */
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitAdmin> getAllAdmins() {
		return adminService.findAllAdmins();
	}

	/*
	 * @Method: getCustomerById
	 * @Description: Fetch a customer details with id
	 * @MethodParameters: id
	 * @Exception 
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitAdmin> getAdminById(@PathVariable Long id) {
		return adminService.findAdminById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	
	@RequestMapping(value = "/createAdmin", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitAdmin> createAdmin(@RequestBody GymFlipFitAdmin admin) {
		GymFlipFitAdmin createdAdmin = adminService.createAdmin(admin);
		return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateAdmin/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitAdmin> updateCustomer(@PathVariable Long id, @RequestBody GymFlipFitAdmin admin) {
		GymFlipFitAdmin updatedAdmin = adminService.updateAdmin(id, admin);
		if (updatedAdmin != null) {
			return ResponseEntity.ok(updatedAdmin);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/deleteAdmin/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return ResponseEntity.noContent().build();
	}
	
	
	// update the slot by id
	/*
	 * @Method: updateSlot
	 * @Description: updating specific slot with help of Id
	 * @MethodParameters: Slot id
	 * @Exception : Id not Found
	 */
	/*
	 * @RequestMapping(value = "/updateSlot/{id}", produces =
	 * MediaType.APPLICATION_JSON, method = RequestMethod.PUT) public
	 * ResponseEntity<GymFlipFitSlot> updateSlot(@PathVariable Long id, @RequestBody
	 * GymFlipFitSlot slotDetails) { GymFlipFitSlot updatedSlot =
	 * adminService.updateSlot(id, slotDetails); if (updatedSlot != null) { return
	 * ResponseEntity.ok(updatedSlot); } else { return
	 * ResponseEntity.notFound().build(); } }
	 */

	// deleting the slot by id
	/*@Method: updateSlot
	 * @Description: updating specific slot with help of Id
	 * @MethodParameters: Slot id
	 * @Exception : Id not Found
	 */
	/*
	 * @RequestMapping(value = "/deleteSlot/{id}", produces =
	 * MediaType.APPLICATION_JSON, method = RequestMethod.DELETE) public
	 * ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
	 * adminService.deleteSlot(id); return ResponseEntity.noContent().build(); }
	 */

}
