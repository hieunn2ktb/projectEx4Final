package ks.training.entity;

import java.time.LocalDateTime;

public class CustomerActivity {
    private int id;
    private int customerId;
    private int propertyId;
    private LocalDateTime viewedAt;

    public CustomerActivity(int id, int customerId, int propertyId, LocalDateTime viewedAt) {
        this.id = id;
        this.customerId = customerId;
        this.propertyId = propertyId;
        this.viewedAt = viewedAt;
    }

    public CustomerActivity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    @Override
    public String toString() {
        return "CustomerActivity{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", propertyId=" + propertyId +
                ", viewedAt=" + viewedAt +
                '}';
    }
}
