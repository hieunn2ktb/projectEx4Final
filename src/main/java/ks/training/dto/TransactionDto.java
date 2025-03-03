package ks.training.dto;

public class TransactionDto {
    private String title;
    private double price;
    private String address;
    private String sellerName;
    private int sellerId;
    public TransactionDto(String title, double price, String address, String sellerName) {
        this.title = title;
        this.price = price;
        this.address = address;
        this.sellerName = sellerName;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public TransactionDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
