package com.zen.notify.utility;



public class UserImportFailure {

    private String rawLine;
    private String errorMessage;

    // Constructor
    public UserImportFailure(String rawLine, String errorMessage) {
        this.rawLine = rawLine;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public String getRawLine() {
        return rawLine;
    }

    public void setRawLine(String rawLine) {
        this.rawLine = rawLine;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Optional: toString() for logging/debugging
    @Override
    public String toString() {
        return "UserImportFailure{" +
                "rawLine='" + rawLine + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}


