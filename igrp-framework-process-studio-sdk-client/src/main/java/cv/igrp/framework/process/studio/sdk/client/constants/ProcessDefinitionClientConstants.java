package cv.igrp.framework.process.studio.sdk.client.constants;


public final class ProcessDefinitionClientConstants {

    private ProcessDefinitionClientConstants() {}

    public static final String BASE_URL = "http://localhost:8080/api/v1"; // You can override this via constructor or config
    public static final String DEPLOY_ENDPOINT = "/process-definitions/deploy";
    public static final String UNDEPLOY_ENDPOINT = "/process-definitions/%s";
    public static final String CONTENT_TYPE_JSON = "application/json";

}
