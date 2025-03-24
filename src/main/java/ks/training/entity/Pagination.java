package ks.training.entity;


public class Pagination {
    private int currentPage;
    private int totalRecords;
    private final int recordsPerPage = 5;
    private int totalPages;


    public Pagination(int currentPage, int totalRecords) {
        this.totalRecords = totalRecords;
        this.totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        this.totalPages = Math.max(this.totalPages, 1);
        setCurrentPage(currentPage);
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 1) {
            this.currentPage = 1;
        } else if (currentPage > totalPages) {
            this.currentPage = totalPages;
        } else {
            this.currentPage = currentPage;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public int getOffset() {
        return (currentPage - 1) * recordsPerPage;
    }
}