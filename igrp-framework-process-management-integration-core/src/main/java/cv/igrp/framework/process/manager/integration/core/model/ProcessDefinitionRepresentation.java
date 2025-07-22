package cv.igrp.framework.process.manager.integration.core.model;

import java.time.LocalDateTime;
import java.util.List;

public interface ProcessDefinitionRepresentation {

    String getId();
    String getKey();
    String getName();
    String getDescription();
    String getVersion();
    String getBpmnXml();
    boolean isDeployed();
    String getDeploymentId();
    List<VariableRepresentation> getVariables();
    List<TaskRepresentation> getTasks();

    List<ProcessVersionRepresentation> getVersionHistory();

    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

}
