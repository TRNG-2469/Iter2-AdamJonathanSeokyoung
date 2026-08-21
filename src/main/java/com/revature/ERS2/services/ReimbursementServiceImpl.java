package com.revature.ERS2.services;

import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;
import com.revature.ERS2.models.User;
import com.revature.ERS2.repositories.ReimbursementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;

    public ReimbursementServiceImpl(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }


    @Override
    public List<Reimbursement> getAllReimbursements() {
        return List.of();
    }

    @Override
    public Reimbursement getReimbursementById(int reimbursementID) {
        return null;
    }

    @Override
    public List<Reimbursement> getReimbursementHistory() {
        return List.of();
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
    public void createReimbursement(Reimbursement r, User author) {
        if (Objects.equals(author.getId(), r.getAuthor().getId())) {
            reimbursementRepository.save(r);
        }
    }

    @Override
    public void updateReimbursement(Reimbursement r) {
        Reimbursement existingReimbursement = reimbursementRepository.findById(r.getId()).orElse(null);
        if (existingReimbursement != null) {
            existingReimbursement.setAuthor(r.getAuthor());
            existingReimbursement.setResolver(r.getResolver());
            existingReimbursement.setAmount(r.getAmount());
            existingReimbursement.setStatus(r.getStatus());
            existingReimbursement.setType(r.getType());
            existingReimbursement.setDescription(r.getDescription());
            existingReimbursement.setSubmittedAt(r.getSubmittedAt());
            existingReimbursement.setResolvedAt(r.getResolvedAt());
            reimbursementRepository.save(existingReimbursement);
        }
    }

    @Override
    public void deleteReimbursement(int reimbursementID) {
        if (reimbursementRepository.existsById(reimbursementID)){
            reimbursementRepository.deleteById(reimbursementID);
        }
    }

    @Override
    public void resolveReimbursement(User resolver, int reimbursementId, ReimbursementStatus status) {
        Reimbursement existingReimbursement = reimbursementRepository.findById(reimbursementId).orElse(null);
        if (existingReimbursement != null) {
            existingReimbursement.setResolver(resolver);
            existingReimbursement.setStatus(status);
            reimbursementRepository.save(existingReimbursement);
        }
    }
}
