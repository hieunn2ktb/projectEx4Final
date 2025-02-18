package ks.training.entity;

public class Property {
    private int id;
    private String title;
    private String description;
    private double price;
    private String address;
    private String propertyType;
    private int createdBy;
    private String createdAt;
    private int acreage;

    public Property(int id, String title, String description, double price, String address, String propertyType, int createdBy, String createdAt, int acreage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.address = address;
        this.propertyType = propertyType;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.acreage = acreage;
    }

    public double getAcreage() {
        return acreage;
    }

    public void setAcreage(int acreage) {
        this.acreage = acreage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
