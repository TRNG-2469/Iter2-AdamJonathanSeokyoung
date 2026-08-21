package com.revature.ERS2.services;

import com.revature.ERS2.exceptions.ReimbursementNotFoundException;
import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;
import com.revature.ERS2.repositories.ReimbursementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;

    public ReimbursementServiceImpl(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }


    @Override
    public List<Reimbursement> getAllReimbursements() {
        return reimbursementRepository.findAll();
    }

    @Override
    public Reimbursement getReimbursementById(int reimbursementID) {
        return reimbursementRepository.findById(reimbursementID)
                .orElseThrow(() -> new ReimbursementNotFoundException(reimbursementID));
    }

    @Override
    public List<Reimbursement> getReimbursementHistory() {
        List<Reimbursement> approved = getReimbursementsByStatus(ReimbursementStatus.APPROVED);
        List<Reimbursement> denied = getReimbursementsByStatus(ReimbursementStatus.DENIED);
        return Stream.of(approved, denied)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<Reimbursement> getReimbursements(ReimbursementStatus status, Integer departmentId) {

        //Branch to the appropiate method based on the query parameters
        if (status != null && departmentId != null) {
            return getReimbursementsByStatusAndDepartment(status, departmentId);
        }

        if (status != null) {
            return getReimbursementsByStatus(status);
        }

        if (departmentId != null) {
            return getReimbursementsByDepartment(departmentId);
        }

        return getAllReimbursements();
    }

    @Override
    public List<Reimbursement> getReimbursementsByStatus(ReimbursementStatus status) {
        //reimbursementRepository.findByStatus(status);
        return List.of();
    }

    @Override
    public List<Reimbursement> getReimbursementsByDepartment(Integer departmentId) {
        //reimbursementRepository.findByDepartment(departmentId);
        return List.of();
    }

    @Override
    public List<Reimbursement> getReimbursementsByStatusAndDepartment(ReimbursementStatus status, Integer departmentId) {
        //reimbursementRepository.findByStatusAndDepartment(status, department)
        return List.of();
    }

    @Override
    public List<Reimbursement> getReimbursementsByAuthor(int authorId) {
        //reimbursementRepository.findByAuthor(authorId).orElseThrow( () -> new RuntimeException("implement custom"));
        return List.of();
    }

    @Override
    public List<Reimbursement> getReimbursementsByAuthorAndStatus(int authorId, ReimbursementStatus status) {
        //reimbursementRepository.findByAuthorAndStatus(authorId, status).orElseThrow( () -> new RuntimeException("implement custom");
        return List.of();
    }

    @Override
    public void createReimbursement(Reimbursement r, int authorId) {

    }

    @Override
    public void updateReimbursement(Reimbursement r) {

    }

    @Override
    public void deleteReimbursement(int reimbursementID) {

    }

    @Override
    public void resolveReimbursement(int resolverId, int reimbursementId, ReimbursementStatus status) {

    }
}
