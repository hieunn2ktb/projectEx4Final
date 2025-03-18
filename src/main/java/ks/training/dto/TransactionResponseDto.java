package ks.training.dto;
import java.sql.Timestamp;
public class TransactionResponseDto {
    private int id;
    private String buyerName;
    private String propertyName;
    private String transactionType;
    private String status;
    private Timestamp createdAt;
    private int propertyId;
    private int userId;
    public TransactionResponseDto() {
    }

    public TransactionResponseDto(int id, String buyerName, String propertyName, String transactionType, String status, Timestamp createdAt, int propertyId, int userId) {
        this.id = id;
        this.buyerName = buyerName;
        this.propertyName = propertyName;
        this.transactionType = transactionType;
        this.status = status;
        this.createdAt = createdAt;
        this.propertyId = propertyId;
        this.userId = userId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TransactionResponseDto{" +
                "id=" + id +
                ", buyerName='" + buyerName + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", propertyId=" + propertyId +
                ", userId=" + userId +
                '}';
    }
}
