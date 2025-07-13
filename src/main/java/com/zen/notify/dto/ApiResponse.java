package com.zen.notify.dto;

import java.time.ZonedDateTime;

public class ApiResponse<T> {

    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private long totalRecords;
    private int pageSize;
    private int currentPage;
    private int totalPages;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(ZonedDateTime timestamp, int status, String error, String path,
                       long totalRecords, int pageSize, int currentPage, int totalPages, T data) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.totalRecords = totalRecords;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.data = data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private ZonedDateTime timestamp;
        private int status;
        private String error;
        private String path;
        private long totalRecords;
        private int pageSize;
        private int currentPage;
        private int totalPages;
        private T data;

        public Builder<T> timestamp(ZonedDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> error(String error) {
            this.error = error;
            return this;
        }

        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        public Builder<T> totalRecords(long totalRecords) {
            this.totalRecords = totalRecords;
            return this;
        }

        public Builder<T> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> currentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder<T> totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(timestamp, status, error, path, totalRecords, pageSize, currentPage, totalPages, data);
        }
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data, String path) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .error(null)
                .path(path)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure(int status, String errorMessage, String path) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(status)
                .error(errorMessage)
                .path(path)
                .data(null)
                .build();
    }
}
