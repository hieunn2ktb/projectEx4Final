package ks.training.dto;

import java.io.InputStream;
import java.util.List;

public class PropertyResponse {

    private String title;
    private String description;
    private double price;
    private String address;
    private String propertyType;
    private double acreage;
    private int createdBy;
    private List<InputStream> imageStreams;

    public PropertyResponse() {
    }


    public List<InputStream> getImageStreams() {
        return imageStreams;
    }

    public void setImageStreams(List<InputStream> imageStreams) {
        this.imageStreams = imageStreams;
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

    public double getAcreage() {
        return acreage;
    }

    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
