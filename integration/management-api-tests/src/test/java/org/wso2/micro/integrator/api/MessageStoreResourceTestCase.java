package org.wso2.micro.integrator.api;

import org.apache.http.HttpResponse;
import org.awaitility.Awaitility;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.clients.SimpleHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MessageStoreResourceTestCase extends ESBIntegrationTest {

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
    }

    @Test(groups = { "wso2.esb" }, description = "Test get Message Stores resource")
    public void retrieveMessageStores() throws IOException {

        if (!isManagementApiAvailable) {
            Awaitility.await().pollInterval(50, TimeUnit.MILLISECONDS).atMost(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                    until(isManagementApiAvailable());
        }

        String accessToken = TokenUtil.getAccessToken(hostName, portOffset);
        Assert.assertNotNull(accessToken);

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);

        String endpoint = "https://" + hostName + ":" + (DEFAULT_INTERNAL_API_HTTPS_PORT + portOffset) + "/management/"
                + "message-stores";

        SimpleHttpClient client = new SimpleHttpClient();

        HttpResponse response = client.doGet(endpoint, headers);
        String responsePayload = client.getResponsePayload(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(responsePayload);
        Assert.assertEquals(jsonResponse.get("count"), 3);
        Assert.assertTrue(jsonResponse.get("list").toString().contains("AbcMessageStore"));
        Assert.assertTrue(jsonResponse.get("list").toString().contains("HelloMessageStore"));
        Assert.assertTrue(jsonResponse.get("list").toString().contains("teststore"));
    }

    @Test(groups = { "wso2.esb" }, description = "Test get Message Stores resource for search key")
    public void retrieveSearchedMessageStores() throws IOException {

        if (!isManagementApiAvailable) {
            Awaitility.await().pollInterval(50, TimeUnit.MILLISECONDS).atMost(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                    until(isManagementApiAvailable());
        }

        String accessToken = TokenUtil.getAccessToken(hostName, portOffset);
        Assert.assertNotNull(accessToken);

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);

        String endpoint = "https://" + hostName + ":" + (DEFAULT_INTERNAL_API_HTTPS_PORT + portOffset) + "/management/"
                + "message-stores?searchKey=HelloMessage";

        SimpleHttpClient client = new SimpleHttpClient();

        HttpResponse response = client.doGet(endpoint, headers);
        String responsePayload = client.getResponsePayload(response);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(responsePayload);
        Assert.assertEquals(jsonResponse.get("count"), 1);
        Assert.assertTrue(jsonResponse.get("list").toString().contains("HelloMessageStore"));
    }

    @AfterClass(alwaysRun = true)
    public void cleanState() throws Exception {
        super.cleanup();
    }

}
