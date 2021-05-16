package com.mb.base.idp.config.oauth.jwt;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;

@Component
public class TokenGenerator {
	
	public String getToken() {
		return getTokenInfo("http://localhost:9090/oauth/token", "clientapp1", "alguna.clave.secreta.12345678")
				.getBody().getAccess_token();
	}
	
	public ResponseEntity<AccessToken> getTokenInfo(String tokenEndpoint, String clientId, String secret) {
		ResponseEntity<AccessToken> responseBody = new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("grant_type", "client_credentials"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

            HttpPost httpPost = new HttpPost(tokenEndpoint);
            httpPost.setEntity(entity);
            
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(clientId, secret);
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
            // Create a custom response handler
            ResponseHandler<ResponseEntity<AccessToken>> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                HttpEntity httpResponse = response.getEntity();
                String stringResponse = httpResponse != null ? EntityUtils.toString(httpResponse) : null;
                AccessToken tokenEntity = null;
                Gson gson = new Gson();
                // Resolve response http status
                if (status >= 200 && status < 300) {
                	tokenEntity = gson.fromJson(stringResponse, AccessToken.class);
                } else {
                	System.out.println("----------------------------------------");
                	System.out.println("ERROR: " + status);
                	tokenEntity = new AccessToken();
                }
                return new ResponseEntity(tokenEntity, HttpStatus.resolve(status));
            };
            responseBody = httpclient.execute(httpPost, responseHandler);
            httpclient.close();
            //System.out.println(responseBody);
        }
		catch(Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
}
