package cv.igrp.framework.process.studio.sdk.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProcessDefinitionRequest {

    private String name;
    private String description;
    private String key;
    private String resourceName;
    private String bpmnXml;
    private String applicationBase;

}
