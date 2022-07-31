package com.poc.pocmastertest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
class PocMasterTestApplicationTests {

	@Autowired
	HttpClient httpClient;

	@Autowired
	ObjectMapper objectMapper;

	@Value("${test.url:}")
	private String testUrl;

	@Test
	void shouldGetUserJobWithUserIdAndName() throws IOException, InterruptedException {
		String requestBody = "{\n"
							+ "    \"userId\": 1,\n"
							+ "    \"name\": \"Alex Robinson\"\n"
							+ "\n"
							+ "}";
		JsonNode jsonNode = sendRequestAndGetResponse(testUrl+"/user-job", requestBody);
		assertNotNull(jsonNode);
		assertTrue(jsonNode.has("job"));
		assertThat(jsonNode.get("job").asText()).isEqualTo("CEO");
	}

	@Test
	void shouldGetUserAddressWithUserIdAndName() throws IOException, InterruptedException {
		String requestBody = "{\n"
			+ "    \"userId\": 2,\n"
			+ "    \"name\": \"John Barnett\"\n"
			+ "\n"
			+ "}";
		JsonNode jsonNode = sendRequestAndGetResponse(testUrl+"/user-address", requestBody);
		assertNotNull(jsonNode);
		assertTrue(jsonNode.has("address"));
		assertTrue(jsonNode.path("address").has("state"));
		assertThat(jsonNode.path("address").get("state").asText()).isEqualTo("CA");
	}

	public HttpRequest getHttpRequest(String url, String data) {
		return HttpRequest.newBuilder()
			.POST(HttpRequest.BodyPublishers.ofString(data))
			.uri(URI.create(url))
			.header("Accept", "application/json")
			.header("Content-Type", "application/json")
			.build();

	}

	public JsonNode sendRequestAndGetResponse(String url, String requestBody) throws IOException, InterruptedException {
		HttpRequest request = getHttpRequest(url, requestBody);
		HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
		return objectMapper.readTree(response.body());
	}

}
