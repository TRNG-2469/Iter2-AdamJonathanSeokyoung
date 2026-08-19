package com.revature.ERS2.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reimbursement {
    private int id;
    private int authorId;
    private Integer resolverId;

    private BigDecimal amount;

    private ReimbursementStatus status;
    private ReimbursementType type;

    private String description;
    private LocalDateTime submittedAt;
    private LocalDateTime resolvedAt;

    public Reimbursement() {
    }

    public Reimbursement(int reimbursementId, int authorId, BigDecimal amount, ReimbursementStatus status,
                         ReimbursementType type, String description, LocalDateTime submittedAt, LocalDateTime resolvedAt,
                         Integer resolverId){
        this(authorId, amount, status, type, description, submittedAt, resolvedAt, resolverId);
        this.id = reimbursementId;
    }

    // constructor for creating reimbursements (auto generate id)
    public Reimbursement(int authorId, BigDecimal amount, ReimbursementStatus status, ReimbursementType type,
                         String description, LocalDateTime submittedAt, LocalDateTime resolvedAt, Integer resolverId) {
        this.authorId = authorId;
        this.amount = amount;
        this.status = status;
        this.type = type;
        this.description = description;
        this.submittedAt = submittedAt;
        this.resolvedAt = resolvedAt;
        this.resolverId = resolverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() { return authorId;}

    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ReimbursementStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbursementStatus status) {
        this.status = status;
    }

    public ReimbursementType getType() {
        return type;
    }

    public void setType(ReimbursementType type) {
        this.type = type;
    }

    public LocalDateTime getSubmittedAt() { return submittedAt; }

    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }

    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }

    public Integer getResolverId() {
        return resolverId;
    }

    public void setResolverId(Integer resolverId) {
        this.resolverId = resolverId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", amount=" + amount +
                ", status='" + status.toString() + '\'' +
                ", status='" + type.toString() + '\'' +
                ", description='" + description + '\'' +
                ", submittedAt=" + submittedAt +
                ", resolvedAt=" + resolvedAt +
                '}';
    }
}




