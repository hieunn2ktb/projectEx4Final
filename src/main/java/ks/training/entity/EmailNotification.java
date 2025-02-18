package ks.training.entity;

public class EmailNotification {
    private int id;
    private int transactionId;
    private String recipientEmail;
    private String subject;
    private String body;
    private String sentAt;

    public EmailNotification(int id, int transactionId, String recipientEmail, String subject, String body, String sentAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.body = body;
        this.sentAt = sentAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

}
