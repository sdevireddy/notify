package com.zen.notify.controller;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class TaskController {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String taskOwner;
    private String subject;
    private LocalDateTime dueDate;
    private String status;
    private String priority;
    private Boolean reminder;
    private Boolean repeatTask;
    
    @Enumerated(EnumType.STRING)
    private AssociatedEntity associatedWith; // Can be 'Account', 'Deal', 'Product', etc.
    
    @Enumerated(EnumType.STRING)
    private RelatedEntity relatedTo; // If 'Contact', can be 'Deal', 'Invoice', 'Purchase Order', etc.

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTaskOwner() { return taskOwner; }
    public void setTaskOwner(String taskOwner) { this.taskOwner = taskOwner; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Boolean getReminder() { return reminder; }
    public void setReminder(Boolean reminder) { this.reminder = reminder; }

    public Boolean getRepeatTask() { return repeatTask; }
    public void setRepeatTask(Boolean repeatTask) { this.repeatTask = repeatTask; }

    public AssociatedEntity getAssociatedWith() { return associatedWith; }
    public void setAssociatedWith(AssociatedEntity associatedWith) { this.associatedWith = associatedWith; }

    public RelatedEntity getRelatedTo() { return relatedTo; }
    public void setRelatedTo(RelatedEntity relatedTo) { this.relatedTo = relatedTo; }
}

enum AssociatedEntity {
    ACCOUNT, DEAL, PRODUCT, PURCHASE_ORDER, SALES_ORDER, QUOTE, INVOICE
}

enum RelatedEntity {
    ACCOUNT, DEAL, PRODUCT, PURCHASE_ORDER, SALES_ORDER, QUOTE, INVOICE
}
