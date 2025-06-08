package com.zen.dto;

import java.util.List;

public class PaginatedResponse<T> {
	
	 private int totalRecords;
	    private int pageSize;
	    private List<T> data;

	    public PaginatedResponse(int totalRecords, int pageSize, List<T> data) {
	        this.totalRecords = totalRecords;
	        this.pageSize = pageSize;
	        this.data = data;
	    }

		public int getTotalRecords() {
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
	    
	    

}
