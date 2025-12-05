
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

@RestController
@RequestMapping("/slots")
public class GymFlipFitSlotController {

    private final GymFlipFitSlotService slotService;

    public GymFlipFitSlotController(GymFlipFitSlotService slotService) {
        this.slotService = slotService;
    }

    /** Get all slots */
    @RequestMapping(value = "/getAllSlots", method = RequestMethod.GET)
    public ResponseEntity<List<GymFlipFitSlot>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    /** Get slot by ID */
    @RequestMapping(value = "/getSlotById/{id}", method = RequestMethod.GET)
    public ResponseEntity<GymFlipFitSlot> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(slotService.getSlotById(id));
    }

    /** Create a new slot */
    @RequestMapping(value = "/createSlot", method = RequestMethod.POST)
    public ResponseEntity<GymFlipFitSlot> createSlot(@RequestBody GymFlipFitSlot slot) {
        return new ResponseEntity<>(slotService.createSlot(slot), HttpStatus.CREATED);
    }

    /** Update slot */
    @RequestMapping(value = "/updateSlot/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GymFlipFitSlot> updateSlot(@PathVariable Long id, @RequestBody GymFlipFitSlot slot) {
        return ResponseEntity.ok(slotService.updateSlot(id, slot));
    }

    /** Delete slot */
    @RequestMapping(value = "/deleteSlot/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSlot(@PathVariable Long id) {
        return ResponseEntity.ok(slotService.deleteSlot(id));
    }
}
