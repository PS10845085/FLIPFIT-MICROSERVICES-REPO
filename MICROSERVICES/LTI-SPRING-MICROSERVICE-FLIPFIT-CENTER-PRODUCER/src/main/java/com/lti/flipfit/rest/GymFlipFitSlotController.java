
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitSlot;
import com.lti.flipfit.service.GymFlipFitSlotService;

/**
 * REST controller that exposes CRUD endpoints for managing gym slots (class/session time blocks).
 * <p>
 * This controller delegates business logic to {@link GymFlipFitSlotService} and returns appropriate
 * HTTP status codes for each operation.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>List all slots or retrieve a slot by id.</li>
 *   <li>Create, update, and delete slots.</li>
 *   <li>Return results as {@link ResponseEntity} with conventional HTTP status codes.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider adding request validation for create/update operations (e.g., using DTO + {@code @Valid}).</li>
 *   <li>Consider authorization for mutating endpoints depending on your roles.</li>
 *   <li>For consistency with other controllers, you may wrap responses in a shared envelope (e.g., {@code RestApiResponse}).</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/slots")
public class GymFlipFitSlotController {

    private final GymFlipFitSlotService slotService;

/**
     * Constructs the {@code GymFlipFitSlotController} with required service dependency.
     *
     * @param slotService service layer handling slot-related operations
     */

    public GymFlipFitSlotController(GymFlipFitSlotService slotService) {
        this.slotService = slotService;
    }


    /**
        * Retrieves all slots in the system.
        * <p>
        * Returns HTTP 200 OK with a list of {@link GymFlipFitSlot}. The list may be empty.
        * </p>
        *
        * <h3>Endpoint</h3>
        * <pre>GET /slots/getAllSlots</pre>
        *
        * @return response entity containing all slots
        */

    @RequestMapping(value = "/getAllSlots", method = RequestMethod.GET)
    public ResponseEntity<List<GymFlipFitSlot>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }


/**
     * Retrieves a single slot by its id.
     * <p>
     * Returns HTTP 200 OK with the slot, or may throw an exception from the service
     * if the id is not found (ensure service handles not-found appropriately).
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /slots/getSlotById/{id}</pre>
     *
     * @param id the unique identifier of the slot to fetch
     * @return response entity with the slot
     */

    @RequestMapping(value = "/getSlotById/{id}", method = RequestMethod.GET)
    public ResponseEntity<GymFlipFitSlot> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(slotService.getSlotById(id));
    }

    /**
        * Creates a new slot.
        * <p>
        * Accepts a {@link GymFlipFitSlot} entity as request body (consider using a DTO + {@code @Valid} for better validation).
        * Returns HTTP 201 Created with the created slot.
        * </p>
        *
        * <h3>Endpoint</h3>
        * <pre>POST /slots/createSlot</pre>
        *
        * @param slot slot payload to create
        * @return response entity with the created slot and 201 status
        */

    @RequestMapping(value = "/createSlot", method = RequestMethod.POST)
    public ResponseEntity<GymFlipFitSlot> createSlot(@RequestBody GymFlipFitSlot slot) {
        return new ResponseEntity<>(slotService.createSlot(slot), HttpStatus.CREATED);
    }


/**
     * Updates an existing slot by its id.
     * <p>
     * Returns HTTP 200 OK with the updated slot. Ensure your service handles not-found
     * scenarios (e.g., throw a specific exception) which can be mapped to 404 by a global handler.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>PUT /slots/updateSlot/{id}</pre>
     *
     * @param id   the id of the slot to update
     * @param slot the fields to update
     * @return response entity containing the updated slot
     */

    @RequestMapping(value = "/updateSlot/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GymFlipFitSlot> updateSlot(@PathVariable Long id, @RequestBody GymFlipFitSlot slot) {
        return ResponseEntity.ok(slotService.updateSlot(id, slot));
    }


/**
     * Deletes a slot by its id.
     * <p>
     * Returns HTTP 200 OK and a simple confirmation message. If you prefer REST conventions,
     * you can return 204 No Content instead.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>DELETE /slots/deleteSlot/{id}</pre>
     *
     * @param id the id of the slot to delete
     * @return response entity with a confirmation message
     */

    @RequestMapping(value = "/deleteSlot/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        return ResponseEntity.ok(slotService.deleteSlot(id));
    }
}
