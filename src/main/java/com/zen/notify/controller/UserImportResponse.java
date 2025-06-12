package com.zen.notify.controller;

import java.util.List;

import com.zen.notify.utility.UserImportFailure;

public class UserImportResponse {
    private int totalRecords;
    private int successCount;
    private int failureCount;
    private List<UserImportFailure> failedRecords;
    
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	public List<UserImportFailure> getFailedRecords() {
		return failedRecords;
	}
	public void setFailedRecords(List<UserImportFailure> failedRecords) {
		this.failedRecords = failedRecords;
	}
    
    
}
