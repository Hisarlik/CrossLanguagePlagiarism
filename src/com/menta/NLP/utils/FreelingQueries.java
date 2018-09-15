package com.menta.NLP.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.google.gson.Gson;
import com.menta.NLP.model.Document;
import com.menta.NLP.model.TextFragment;
import com.menta.NLP.model.Token;

public class FreelingQueries {
	
	public static void query(Document textF){
		
		Document doc = null;
	
	try {
		
		// Properties file
		ApplicationGetProperties properties = new ApplicationGetProperties();
		String urlFreeling = properties.getInstance("config.properties").getProperty("urlFreeling");
		String analysisFreeling = properties.getInstance("config.properties").getProperty("analysisFreeling");
		int fragmentWindow = Integer.parseInt(properties.getInstance("config.properties").getProperty("fragmentWindow"));
		int fragmentJump = Integer.parseInt(properties.getInstance("config.properties").getProperty("fragmentJump"));
		
		// Post query to Freeling
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(120);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(120);

		HttpClientBuilder builder = HttpClientBuilder.create();     
		builder.setDefaultRequestConfig(requestBuilder.build());
		HttpClient httpClient = builder.build();
		
		
		
		//HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(urlFreeling+analysisFreeling);
		String text = textF.getDocument().replace("\"", "");
		text = text.replace("\n", "").replace("\r", "");
		StringEntity input = new StringEntity("{\"text\":\""+text+"\", \"fragmentWindow\":"+fragmentWindow+",\"fragmentJump\":"+fragmentJump+"}","UTF8");
		input.setContentType("application/json");
		postRequest.setEntity(input);

		HttpResponse response = httpClient.execute(postRequest);
		BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

		StringBuilder content = new StringBuilder();
		System.out.println("Output from Server .... \n");
		String line;
		while (null != (line = br.readLine())) {
		    content.append(line);
		}

		//Format response
		Gson gson = new Gson();
		doc = gson.fromJson(content.toString(),Document.class);
		textF.setFragments(doc.getFragments());
		textF.setLan(doc.getLan());
		

		

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
	


	}

}
