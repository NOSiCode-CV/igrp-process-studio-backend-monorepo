package cv.igrp.framework.process.management.integration.core.model;

import java.time.LocalDateTime;

public interface ProcessVersionRepresentation {

    String getVersion();
    String getDeploymentId();
    String getBpmnXml();
    LocalDateTime getDeployedAt();
    boolean isActive();

}
