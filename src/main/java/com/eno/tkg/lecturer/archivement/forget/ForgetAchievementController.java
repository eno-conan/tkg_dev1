package com.eno.tkg.lecturer.archivement.forget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
public class ForgetAchievementController {

	@Autowired
	private UploadFileToS3Service uploadFileToS3Service;

	@GetMapping("/lecturer/forget-achievement")
	void uploadFileTos3() {
		uploadFileToS3Service.uploadFileToS3();
	}

}
