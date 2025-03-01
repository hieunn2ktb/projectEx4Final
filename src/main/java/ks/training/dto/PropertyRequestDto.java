package ks.training.dto;

import java.io.InputStream;
import java.util.List;

public class PropertyRequestDto {
    private String title;
    private String description;
    private double price;
    private String address;
    private String propertyType;
    private double acreage;
    private int createdBy;
    private List<InputStream> imageStreams;

}
