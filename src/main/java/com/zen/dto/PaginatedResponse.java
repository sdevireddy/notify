package com.zen.dto;

import java.util.List;

public class PaginatedResponse<T> {
	
	 	private long totalRecords;
	    private int pageSize;
	    private int currentPage;
	    private int totalPages;
	    private List<T> data;

	    public PaginatedResponse(long totalRecords, int pageSize, int currentPage, int totalPages, List<T> data) {
	        this.totalRecords = totalRecords;
	        this.pageSize = pageSize;
	        this.currentPage = currentPage;
	        this.totalPages = totalPages;
	        this.data = data;
	    }

		public long getTotalRecords() {
			return totalRecords;
		}

		public void setTotalRecords(int totalRecords) {
			this.totalRecords = totalRecords;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public List<T> getData() {
			return data;
		}

		public void setData(List<T> data) {
			this.data = data;
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

		public void setTotalRecords(long totalRecords) {
			this.totalRecords = totalRecords;
		} 

}
