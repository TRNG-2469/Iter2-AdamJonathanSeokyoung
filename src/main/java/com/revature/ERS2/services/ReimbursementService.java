package com.revature.ERS2.services;

import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;

import java.util.List;

public interface ReimbursementService {

    //TODO: Jonathan
    List<Reimbursement> getAllReimbursements();
    Reimbursement getReimbursementById(int reimbursementID);
    List<Reimbursement> getReimbursementHistory();

    //TODO: Rest seoky
    //Status or department, or both
    //For branching
    List<Reimbursement> getReimbursements(ReimbursementStatus status, Integer departmentId);

    List<Reimbursement> getReimbursementsByStatus(ReimbursementStatus status);
    List<Reimbursement> getReimbursementsByDepartment(Integer departmentId);
    List<Reimbursement> getReimbursementsByStatusAndDepartment(ReimbursementStatus status, Integer departmentId);
    List<Reimbursement> getReimbursementsByAuthor(int authorId);
    List<Reimbursement> getReimbursementsByAuthorAndStatus(int authorId, ReimbursementStatus status);

    //TODO: Adam
    void createReimbursement(Reimbursement r, int authorId);
    void updateReimbursement(Reimbursement r);
    void deleteReimbursement(int reimbursementID);

    void resolveReimbursement(int resolverId, int reimbursementId, ReimbursementStatus status);

}



