package com.waiter.server.utils.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shahenpoghosyan
 */
public class RequestSender {

    public static final String PARAM_DELIMITER = "&";
    public static final String FIRST_DELIMITER = "?";
    public static final String EQUAL_DELIMITER = "=";
    private static final Logger logger = Logger.getLogger(RequestSender.class);
    private static final Map<String, String> DEFAULT_HEADERS = new HashMap<>();
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        DEFAULT_HEADERS.put("Content-Type", "application/x-www-form-urlencoded");
        DEFAULT_HEADERS.put("charset", "utf-8");

    }

    public RequestSender() {

    }

    public JsonNode getRequest(String path, Map<String, String> params) throws IOException {
        HttpURLConnection response = getRequest(path, params, DEFAULT_HEADERS);
        return parseResponse(response);
    }

    public JsonNode postRequest(String path, Map<String, String> params) throws IOException {
        System.out.println(path);
        return postRequest(path, constructQuery(params));
    }

    public JsonNode postRequest(String path, String body) throws IOException {
        HttpURLConnection response = postRequest(path, body, DEFAULT_HEADERS);
        return parseResponse(response);
    }

    public HttpURLConnection getRequest(String host, Map<String, String> params, Map<String, String> headers)
            throws IOException {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(host).append(FIRST_DELIMITER).append(constructQuery(params));
        URL url = new URL(urlBuilder.toString());
        logger.debug("getById request: " + urlBuilder);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //add request headers
        if (headers != null) {
            for (Map.Entry<String, String> header : params.entrySet()) {
                connection.addRequestProperty(header.getKey(), header.getValue().toString());
            }
        }
        connection.connect();
        return connection;
    }

    public HttpURLConnection postRequest(String host, String body, Map<String, String> headers)
            throws IOException {

        URL url = new URL(host);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.addRequestProperty(header.getKey(), header.getValue().toString());
            }
        }

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(body);
        writer.flush();
        writer.close();
        os.close();
        return connection;
    }

    private String constructQuery(Map<String, String> params) {
        StringBuilder queryBuilder = new StringBuilder();
        if (params != null) {
            boolean first = true;
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (param.getValue() != null) {
                    if (first) {
                        first = false;
                    } else {
                        queryBuilder.append(PARAM_DELIMITER);
                    }
                    try {
                        queryBuilder.append(param.getKey()).append(EQUAL_DELIMITER)
                                .append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        // ignore
                    }
                }
            }
        }
        return queryBuilder.toString();
    }

    private JsonNode parseResponse(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        if (status >= 300)
            throw new IOException("Server returned status: " + status);
        JsonNode result;
        try {
            result = objectMapper.readTree(connection.getInputStream());
        } catch (JsonProcessingException e) {
            throw new IOException(e);
        }
        return result;
    }
}
