package com.revature.ERS2.services;

import com.revature.ERS2.exceptions.ReimbursementNotFoundException;
import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;
import com.revature.ERS2.models.User;
import com.revature.ERS2.repositories.ReimbursementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    //Will not return an ordered history
    @Override
    public List<Reimbursement> getReimbursementHistory() {
        return reimbursementRepository.getResolvedHistory(
                List.of(ReimbursementStatus.APPROVED, ReimbursementStatus.DENIED));
    }

    @Override
    public List<Reimbursement> getReimbursements(ReimbursementStatus status, Integer departmentId) {

        //Todo: implement authorization so that this reroutes to author, authorStatus if user is EMPLOYEE
        //if (role == EMPLOYEE)
        // getReimbursementsByAuthor(authorId)
        //etc

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
        return reimbursementRepository.findByStatus(status);
    }

    @Override
    public List<Reimbursement> getReimbursementsByDepartment(Integer departmentId) {
        return reimbursementRepository.findByAuthor_Department_DepartmentId(departmentId);
    }

    @Override
    public List<Reimbursement> getReimbursementsByStatusAndDepartment(ReimbursementStatus status, Integer departmentId) {
        return reimbursementRepository.findByStatusAndAuthor_Department_DepartmentId(status, departmentId);
    }

    @Override
    public List<Reimbursement> getReimbursementsByAuthor(int authorId) {

        //Todo: use getById to verify if user exists
        //userRepository.findById(authorId).orElseThrow( () -> throw new RuntimeException("Author was not found"))

        return reimbursementRepository.findByAuthor(authorId);
    }

    @Override
    public List<Reimbursement> getReimbursementsByAuthorAndStatus(int authorId, ReimbursementStatus status) {
        //Todo: again, once getById is implemented, uncomment
        //userRepository.findById(authorId).orElseThrow( () -> throw new RuntimeException("Author was not found"))

        return reimbursementRepository.findByAuthorAndStatus(authorId, status);
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
