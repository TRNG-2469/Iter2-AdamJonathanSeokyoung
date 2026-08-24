package com.revature.ERS2.controllers;


import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;
import com.revature.ERS2.services.ReimbursementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    // API endpoint GET /reimbursements is also mapped to Young's getReimbursements() method
    // which is rerouted in the service based on if the method has query parameters, so commenting out for now
    /*@GetMapping("/reimbursements")
    public List<Reimbursement> getAllReimbursements(){
        return reimbursementService.getAllReimbursements();
    } */

    @GetMapping("/reimbursements/{id}")
    public Reimbursement getReimbursementById(@PathVariable int id){
        return reimbursementService.getReimbursementById(id);
    }

    @GetMapping("/reimbursements/history")
    public List<Reimbursement> getReimbursementHistory(){
        return reimbursementService.getReimbursementHistory();
    }


    @GetMapping("/reimbursements")
    public ResponseEntity<List<Reimbursement>> getReimbursements(@RequestParam(required = false) ReimbursementStatus status,
                                                                 @RequestParam(required = false) Integer departmentId) {

        List<Reimbursement> reimbursements = reimbursementService.getReimbursements(status, departmentId);
        return ResponseEntity.ok(reimbursements);
    }



}
