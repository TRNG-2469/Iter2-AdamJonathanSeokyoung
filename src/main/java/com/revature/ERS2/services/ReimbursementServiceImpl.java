package com.revature.ERS2.services;

import com.revature.ERS2.dtos.requests.CreateReimbursementReq;
import com.revature.ERS2.dtos.responses.ReimbursementResponse;
import com.revature.ERS2.exceptions.ReimbursementNotFoundException;
import com.revature.ERS2.exceptions.UserNotFoundException;
import com.revature.ERS2.models.Reimbursement;
import com.revature.ERS2.models.ReimbursementStatus;
import com.revature.ERS2.models.User;
import com.revature.ERS2.repositories.ReimbursementRepository;
import com.revature.ERS2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final UserRepository userRepository;

    public ReimbursementServiceImpl(ReimbursementRepository reimbursementRepository,
                                    UserRepository userRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ReimbursementResponse> getAllReimbursements() {
        return transformReimbursementToResponse(reimbursementRepository.findAll());
    }

    @Override
    public ReimbursementResponse getReimbursementById(int reimbursementID) {
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementID)
                .orElseThrow(() -> new ReimbursementNotFoundException(reimbursementID));
        return transformReimbursementToResponse(reimbursement);
    }

    //Will not return an ordered history
    @Override
    public List<ReimbursementResponse> getReimbursementHistory() {
        List<ReimbursementResponse> approved = getReimbursementsByStatus(ReimbursementStatus.APPROVED);
        List<ReimbursementResponse> denied = getReimbursementsByStatus(ReimbursementStatus.DENIED);
        return Stream.of(approved, denied)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<ReimbursementResponse> getReimbursements(ReimbursementStatus status, Integer departmentId) {

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
    public List<ReimbursementResponse> getReimbursementsByStatus(ReimbursementStatus status) {
        return transformReimbursementToResponse(reimbursementRepository.findByStatus(status));
    }

    @Override
    public List<ReimbursementResponse> getReimbursementsByDepartment(Integer departmentId) {
        return transformReimbursementToResponse(reimbursementRepository.findByAuthor_Department_DepartmentId(departmentId));
    }

    @Override
    public List<ReimbursementResponse> getReimbursementsByStatusAndDepartment(ReimbursementStatus status, Integer departmentId) {
        return transformReimbursementToResponse(reimbursementRepository.findByStatusAndAuthor_Department_DepartmentId(status, departmentId));
    }

    @Override
    public List<ReimbursementResponse> getReimbursementsByAuthor(int authorId) {

        //Todo: use getById to verify if user exists
        //userRepository.findById(authorId).orElseThrow( () -> throw new RuntimeException("Author was not found"))

        return transformReimbursementToResponse(reimbursementRepository.findByAuthor(authorId));
    }

    @Override
    public List<ReimbursementResponse> getReimbursementsByAuthorAndStatus(int authorId, ReimbursementStatus status) {
        //Todo: again, once getById is implemented, uncomment
        //userRepository.findById(authorId).orElseThrow( () -> throw new RuntimeException("Author was not found"))

        return transformReimbursementToResponse(reimbursementRepository.findByAuthorAndStatus(authorId, status));
    }

    @Override
    public ReimbursementResponse createReimbursement(CreateReimbursementReq rDTO, String username) {

        //First, gets logged in user's username and sees if the user even exists
        User author = userRepository.findByUsername(username).
                orElseThrow(() -> new UserNotFoundException(username));

        //Creates reimbursement object (special constructor for making new one from POST)
        //This nulls things users shouldn't touch like resolved time, id, etc.
        Reimbursement r = new Reimbursement(author, rDTO.getAmount(), ReimbursementStatus.PENDING,
                rDTO.getType(), rDTO.getDescription(), LocalDateTime.now());

        Reimbursement savedR = reimbursementRepository.save(r);

        //Constructs a reimbursement response (doesn't expose entire user object, just their id for frontend)
        //Wrote a helper method cuz it was repetitive and I think we only have one model for Response
        return transformReimbursementToResponse(savedR);
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
        if (reimbursementRepository.existsById(reimbursementID)) {
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

    /**
     * Transforms a reimbursement to a reimbursement response
     * (Does not expose user details)
     *
     * @param r Reimbursement
     * @return
     */
    public static ReimbursementResponse transformReimbursementToResponse(Reimbursement r) {

        Integer resolverId;

        if (r.getResolver() == null) {
            resolverId = null;
        } else {
            resolverId = r.getResolver().getId();
        }

        return new ReimbursementResponse(r.getId(), r.getAuthor().getId(),
                resolverId, r.getAmount(), r.getStatus(), r.getType(),
                r.getDescription(), r.getSubmittedAt(), r.getResolvedAt());
    }


    /**
     * Well, transforms a list of reimbursements to list of reimbursementResponse
     *
     * @param rList
     * @return
     */
    public static List<ReimbursementResponse> transformReimbursementToResponse(List<Reimbursement> rList) {

        List<ReimbursementResponse> responseList = new ArrayList<>();

        for (Reimbursement r : rList) {

            Integer resolverId;

            if (r.getResolver() == null) {
                resolverId = null;
            } else {
                resolverId = r.getResolver().getId();
            }

            responseList.add(new ReimbursementResponse(r.getId(), r.getAuthor().getId(),
                    resolverId, r.getAmount(), r.getStatus(), r.getType(),
                    r.getDescription(), r.getSubmittedAt(), r.getResolvedAt()));
        }

        return responseList;
    }



}
