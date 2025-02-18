package ks.training.entity;

public class Report {
    private int id;
    private String reportType; // Số lượng giao dịch, Doanh thu
    private int month;
    private int year;
    private int totalTransactions;
    private double totalRevenue;
    private String createdAt;

    public Report(int id, String reportType, int month, int year, int totalTransactions, double totalRevenue, String createdAt) {
        this.id = id;
        this.reportType = reportType;
        this.month = month;
        this.year = year;
        this.totalTransactions = totalTransactions;
        this.totalRevenue = totalRevenue;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
