package com.zen.notify.dto;

import java.time.ZonedDateTime;

public class ApiResponse<T> {

    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(ZonedDateTime timestamp, int status, String error, String path, T data) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.data = data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    // ===== Builder class =====
    public static class Builder<T> {
        private ZonedDateTime timestamp;
        private int status;
        private String error;
        private String path;
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

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(timestamp, status, error, path, data);
        }
    }

    // ===== Getters & Setters =====
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
