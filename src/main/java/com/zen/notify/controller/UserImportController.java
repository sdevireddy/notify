package com.zen.notify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zen.notify.service.UserImportService;

public class UserImportController {
	
	@Autowired
	UserImportService userImportService;
	
	@PostMapping("/import-users")
	public ResponseEntity<UserImportResponse> importUsers(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok(userImportService.importUsersFromCSV(file));
	}

}
