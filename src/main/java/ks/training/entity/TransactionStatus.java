package ks.training.entity;

public class TransactionStatus {
    private int id;
    private String name; // Đang xử lý, Đã hoàn thành

    public TransactionStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
