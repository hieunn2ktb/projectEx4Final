package ks.training.entity;

import java.sql.Timestamp;

public class PropertyImage {
    private int id;
    private int propertyId;
    private byte[] imageData;
    private Timestamp uploadedAt;

    public PropertyImage() {}

    public PropertyImage(int id, int propertyId, byte[] imageData, Timestamp uploadedAt) {
        this.id = id;
        this.propertyId = propertyId;
        this.imageData = imageData;
        this.uploadedAt = uploadedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
