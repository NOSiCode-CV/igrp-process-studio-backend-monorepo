package cv.igrp.framework.process.studio.sdk.client.dto;


import cv.igrp.framework.process.management.integration.core.model.BpmnSourceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProcessDefinitionResponse {

    private String id;
    private String key;
    private String name;
    private String description;
    private String version;

    private String bpmnXml;
    private String bpmnUrl;
    private BpmnSourceType bpmnSourceType;
    private String resourceName;

    private boolean deployed;
    private String deploymentId;
    private LocalDateTime deployedAt;

}
