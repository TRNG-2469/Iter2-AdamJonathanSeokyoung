package com.revature.ERS2.controllers;


import com.revature.ERS2.dtos.requests.CreateReimbursementReq;
import com.revature.ERS2.dtos.requests.PatchReimbursementReq;
import com.revature.ERS2.dtos.requests.ResolveReimbursementReq;
import com.revature.ERS2.dtos.responses.ReimbursementResponse;
import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;
import com.revature.ERS2.services.ReimbursementService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    @GetMapping("/reimbursements/{id}")
    public ReimbursementResponse getReimbursementById(@PathVariable int id){
        return reimbursementService.getReimbursementById(id);
    }

    @GetMapping("/reimbursements/history")
    public List<ReimbursementResponse> getReimbursementHistory(){
        return reimbursementService.getReimbursementHistory();
    }


    @GetMapping("/reimbursements")
    public ResponseEntity<List<ReimbursementResponse>> getReimbursements(@RequestParam(required = false) ReimbursementStatus status,
                                                                 @RequestParam(required = false) Integer departmentId) {

        List<ReimbursementResponse> reimbursements = reimbursementService.getReimbursements(status, departmentId);
        return ResponseEntity.ok(reimbursements);
    }

    @PostMapping("/reimbursements")
    public ResponseEntity<ReimbursementResponse> createReimbursement(@Valid @RequestBody CreateReimbursementReq rDTO,
                                                             Authentication authentication) {

        String loggedInUsername = authentication.getName();
        ReimbursementResponse responseR = reimbursementService.createReimbursement(rDTO, loggedInUsername);

        return ResponseEntity.status(201).body(responseR);
    }

    @DeleteMapping("/reimbursements/{id}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable Integer id, Authentication authentication) {

        String loggedInUsername = authentication.getName();

        //uncomment when delete is finished
        //reimbursementService.deleteReimbursement(id, username);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reimbursements/{id}")
    public ResponseEntity<ReimbursementResponse> patchReimbursement(@Valid @RequestBody PatchReimbursementReq rDTO,
                                                                    @PathVariable("id") Integer reimbursementId,
                                                                    Authentication authentication) {

        String loggedInUsername = authentication.getName();
        //uncomment when patch is finished
        //ReimbursementResponse rResponse = reimbursementService.updateReimbursement(rDTO, reimbursementId, username);
        //return ResponseEntity.ok(rResponse);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reimbursements/{id}/status")
    public ResponseEntity<ReimbursementResponse> resolveReimbursement(@Valid @RequestBody ResolveReimbursementReq rDTO,
                                                                      @PathVariable("id") Integer reimbursementId,
                                                                      Authentication authentication) {

        String loggedInUsername = authentication.getName();
        //uncomment when resolved finished
        //ReimbursementResponse rResponse = reimbursementService.resolveReimbursement(rDTO, reimbursementId, loggedInUsername);
        //return ResponseEntity.ok(rResponse);

        return ResponseEntity.noContent().build();
    }
}
