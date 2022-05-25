package com.eno.service;

import java.io.IOException;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.util.IOUtils;

public class StringResponseHandler implements HttpResponseHandler<AmazonWebServiceResponse<String>> {

    @Override
    public AmazonWebServiceResponse<String> handle(com.amazonaws.http.HttpResponse response) throws IOException {

        AmazonWebServiceResponse<String> awsResponse = new AmazonWebServiceResponse<>();
        awsResponse.setResult(IOUtils.toString(response.getContent()));
        return awsResponse;
    }

    @Override
    public boolean needsConnectionLeftOpen() {
        return false;
    }

}
