package io.github.yhsj.axiom;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Custom Appender for logback that logs data to Axiom.co
 */
public class AxiomAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private Encoder<ILoggingEvent> encoder;
    private String endpointUrl;
    private String apiToken;

    @Override
    protected void append(ILoggingEvent eventObject) {
        byte[] message = encoder.encode(eventObject);
        postToAxiom(message);
    }

    /**
     * Send byte array to Axiom
     *
     * @param data byte array to be sent
     */
    private void postToAxiom(byte[] data) {
        URI endpoint = URI.create(endpointUrl);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(endpoint)
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .setHeader("Authorization", "Bearer " + apiToken)
                .setHeader("Content-Type", "application/x-ndjson")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                addError(String.format("Failed to post to Axiom (HTTP Code: %s) Response body:\n%s", response.statusCode(), response.body()));
        } catch (IOException | InterruptedException e) {
            addError("Failed to post to Axiom.");
        }
    }

    @Override
    public void start() {
        super.start();

        this.encoder.setContext(getContext());
    }

    @SuppressWarnings("unused")
    public String getEndpointUrl() {
        return endpointUrl;
    }

    @SuppressWarnings("unused")
    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    @SuppressWarnings("unused")
    public String getApiToken() {
        return apiToken;
    }

    @SuppressWarnings("unused")
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @SuppressWarnings("unused")
    public Encoder<ILoggingEvent> getEncoder() {
        return encoder;
    }

    @SuppressWarnings("unused")
    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }
}
