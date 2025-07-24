package cv.igrp.framework.process.studio.sdk.client.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProcessDefinitionRequest {

    private String name;
    private String description;
    private String key;
    private String resourceName;
    private String bpmnXml;

}
