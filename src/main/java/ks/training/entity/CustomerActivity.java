public class CustomerActivity {
    private int id;
    private int customerId;
    private int propertyId;
    private String viewedAt;

    public CustomerActivity(int id, int customerId, int propertyId, String viewedAt) {
        this.id = id;
        this.customerId = customerId;
        this.propertyId = propertyId;
        this.viewedAt = viewedAt;
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

    public String getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(String viewedAt) {
        this.viewedAt = viewedAt;
    }
}
