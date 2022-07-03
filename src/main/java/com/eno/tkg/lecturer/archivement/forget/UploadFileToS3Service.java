package com.eno.tkg.lecturer.archivement.forget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.eno.tkg.util.UseOverFunction;

@Service
public class UploadFileToS3Service {

	@Value("${region:}")
	private String region;

	@Value("${forgetAchivementBucketName:}")
	private String forgetAchivementBucketName;

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	AWSCredentialsProvider provider = new ProfileCredentialsProvider("user1");
	private String accessKey = provider.getCredentials().getAWSAccessKeyId();
	private String secretKey = provider.getCredentials().getAWSSecretKey();

	void uploadFileToS3() {

		// reference
		// https://g-weblog.com/blog/26#contents-2-4
		// 認証
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				// リージョンを東京に指定
				.withRegion("ap-northeast-1").build();

		String bucketName = forgetAchivementBucketName;
		String fileKey = createFileName();

		Date targetDate = new Date();
		List<StudentScheduleNormal> forgetAchieveClassList = studentScheduleNormalRepository
				.findByStatusAndClassDateBefore(0, targetDate);
		String dateStr = UseOverFunction.dateToDateStr(targetDate);

		try {
			s3.putObject(new PutObjectRequest(bucketName, fileKey, createFileContent(forgetAchieveClassList, dateStr)));
			System.out.println(dateStr + " : " + "s3にファイルを出力しました");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private String createFileName() {
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formatNowDate = dtformat.format(nowDate);
		String key = formatNowDate + ".json";
		return key;
	}

	private static File createFileContent(List<StudentScheduleNormal> forgetAchieveClassList, String dateStr)
			throws IOException {
		File file = File.createTempFile("aws-java-sdk-", ".json");
		file.deleteOnExit();
		List<Map<String, Object>> jsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal cls : forgetAchieveClassList) {
			Map<String, Object> infoMap = new LinkedHashMap<>();
			infoMap.put("id", cls.getId());
			infoMap.put("classDate", UseOverFunction.dateToDateStr(cls.getClassDate()));
			infoMap.put("studentName", cls.getStudent().getStudentName());
			infoMap.put("subjectName", cls.getSubject().getDisplayName());
			infoMap.put("lecturerName", cls.getLecturer().getLecturerName());
			jsonLiteral.add(infoMap);
		}
		Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		String strJson = UseOverFunction.getDataToJsonFormat(jsonLiteral);
		writer.write(strJson);
		writer.close();
		return file;
	}

}
