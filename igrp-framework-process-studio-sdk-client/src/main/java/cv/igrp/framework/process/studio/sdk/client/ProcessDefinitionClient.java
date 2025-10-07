package cv.igrp.framework.process.studio.sdk.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import cv.igrp.framework.process.management.integration.core.adapter.IProcessDefinitionAdapter;
import cv.igrp.framework.process.management.integration.core.model.IgrpProcessDefinitionRepresentation;
import cv.igrp.framework.process.management.integration.core.model.ProcessDefinitionRepresentation;
import cv.igrp.framework.process.studio.sdk.client.constants.ProcessDefinitionClientConstants;
import cv.igrp.framework.process.studio.sdk.client.dto.ProcessDefinitionRequest;
import cv.igrp.framework.process.studio.sdk.client.dto.ProcessDefinitionResponse;
import cv.igrp.framework.process.studio.sdk.client.exception.ProcessDefinitionClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ProcessDefinitionClient implements IProcessDefinitionAdapter {

    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String authToken;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionClient.class);


    private ProcessDefinitionClient(String baseUrl, HttpClient httpClient, ObjectMapper objectMapper, String authToken) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.authToken = authToken;
    }

    @Override
    public ProcessDefinitionRepresentation deploy(ProcessDefinitionRepresentation processDefinitionRepresentation)  {
        try {

            LOGGER.info("Starting deployment of process definition with key: {}", processDefinitionRepresentation.getKey());

            ProcessDefinitionRequest deployProcessRequest = ProcessDefinitionRequest.builder()
                    .name(processDefinitionRepresentation.getName())
                    .description(processDefinitionRepresentation.getDescription())
                    .key(processDefinitionRepresentation.getKey())
                    .resourceName(processDefinitionRepresentation.getResourceName())
                    .bpmnXml(processDefinitionRepresentation.getBpmnXml())
                    .applicationBase(processDefinitionRepresentation.getApplicationBase())
                    .build();

            String json = objectMapper.writeValueAsString(deployProcessRequest);

            LOGGER.info("Deploy request payload: {}", objectMapper.writeValueAsString(deployProcessRequest));

            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + ProcessDefinitionClientConstants.DEPLOY_ENDPOINT))
                    .header("Content-Type", ProcessDefinitionClientConstants.CONTENT_TYPE_JSON);

            if (authToken != null && !authToken.isBlank()) {
                builder.header("Authorization", "Bearer " + authToken);
            }

            HttpRequest request = builder
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new ProcessDefinitionClientException("Failed to deploy process: " + response.body());
            }

            LOGGER.info("Deploy response payload: {}", response.body());


            ProcessDefinitionResponse deployProcessResponse = objectMapper.readValue(response.body(), ProcessDefinitionResponse.class);

            LOGGER.info("deployProcessResponse obj ::: {}", deployProcessResponse);

            return IgrpProcessDefinitionRepresentation.builder()
                    .id(deployProcessResponse.getId())
                    .key(deployProcessResponse.getKey())
                    .name(deployProcessResponse.getName())
                    .description(deployProcessResponse.getDescription())
                    .version(String.valueOf(deployProcessResponse.getVersion()))
                    .bpmnXml(deployProcessResponse.getBpmnXml())
                    .bpmnSourceType(deployProcessResponse.getBpmnSourceType())
                    .deployed(deployProcessResponse.isDeployed())
                    .deploymentId(deployProcessResponse.getDeploymentId())
                    .deployedAt(deployProcessResponse.getDeployedAt())
                    .build();

        } catch (Exception e) {
            throw new ProcessDefinitionClientException("Error while deploying process", e);
        }
    }




    @Override
    public void undeploy(String deploymentId) {
        String endpoint = String.format(ProcessDefinitionClientConstants.UNDEPLOY_ENDPOINT, deploymentId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .DELETE()
                .build();
        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 204) {
                throw new ProcessDefinitionClientException("Failed to undeploy process definition. Status code: " + response.statusCode());
            }
        } catch (Exception ex) {
            throw new ProcessDefinitionClientException("Error while undeploying process definition", ex);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String baseUrl;
        private HttpClient httpClient;
        private ObjectMapper objectMapper;
        private String authToken;

        public Builder authToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder objectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        public ProcessDefinitionClient build() {
            if (baseUrl == null || baseUrl.isBlank()) {
                throw new IllegalArgumentException("Base URL is required");
            }

            return new ProcessDefinitionClient(
                    baseUrl,
                    httpClient != null ? httpClient : HttpClient.newHttpClient(),
                    objectMapper != null ? objectMapper : new ObjectMapper(),
                    authToken
            );
        }
    }

}
