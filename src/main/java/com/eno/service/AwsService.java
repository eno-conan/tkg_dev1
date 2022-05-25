package com.eno.service;

import java.net.URI;
import java.util.Date;

import org.springframework.stereotype.Service;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Response;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;

@Service
public class AwsService {

	@SuppressWarnings("rawtypes")
	public String callApiTest() {
//
		AWS4Signer signer = new AWS4Signer();

		AWSCredentialsProvider credentials = new ProfileCredentialsProvider("user1");
		String awsAccessKey = credentials.getCredentials().getAWSAccessKeyId();
		String awsSecretKey = credentials.getCredentials().getAWSSecretKey();
//		String sessionToken = "<セッショントークン>";

//		AWSCredentials cred = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

		AWSSecurityTokenService sts_client = AWSSecurityTokenServiceClientBuilder.standard()
				.withCredentials(credentials).withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
						"sts.ap-northeast-1.amazonaws.com", "ap-northeast-1"))
				.build();

		GetSessionTokenRequest session_token_request = new GetSessionTokenRequest();
		session_token_request.setDurationSeconds(7200); // optional.

		GetSessionTokenResult session_token_result = sts_client.getSessionToken(session_token_request);

		Credentials session_creds = session_token_result.getCredentials();

		BasicSessionCredentials cred = new BasicSessionCredentials(session_creds.getAccessKeyId(),
				session_creds.getSecretAccessKey(), session_creds.getSessionToken());

		DefaultRequest req = new DefaultRequest("execute-api");
		req.setEndpoint(URI.create("https://2rtbj6q2i0.execute-api.ap-northeast-1.amazonaws.com/stg/test"));
		req.setResourcePath("/stg/test");
		req.setHttpMethod(HttpMethodName.GET);
		req.addHeader("Content-Type", "application/json");

		signer.setOverrideDate(new Date());
		signer.setRegionName("ap-northeast-1");
		signer.setServiceName("execute-api");
		signer.sign(req, cred);

		ClientConfiguration clientConfiguration = new ClientConfiguration();
		AmazonHttpClient client = new AmazonHttpClient(clientConfiguration);

		Response<AmazonWebServiceResponse<String>> res = client.requestExecutionBuilder().request(req)
				.execute(new StringResponseHandler());

		System.out.println(res.getAwsResponse().getResult());

		return "";
	}

}
