package cv.igrp.framework.process.management.integration.core.adapter;

import cv.igrp.framework.process.management.integration.core.exception.ProcessDefinitionException;
import cv.igrp.framework.process.management.integration.core.model.ProcessDefinitionRepresentation;
import java.util.List;

/**
 * Adapter interface for abstracting access to process definitions
 * from different workflow engines (e.g., Activiti, Camunda).
 *
 * This interface allows the application to remain decoupled from
 * any specific process engine implementation.
 */
public interface IProcessDefinitionAdapter {

    /**
     * Deploys the given process definition to the underlying workflow engine using
     * the provided BPMN 2.0 XML content.
     *
     * @param processDefinitionRepresentation the process definition representation containing metadata and BPMN XML
     * @return the deployed process definition representation including updated deployment metadata
     * @throws ProcessDefinitionException if deployment fails
     */
    ProcessDefinitionRepresentation deploy(ProcessDefinitionRepresentation processDefinitionRepresentation) throws ProcessDefinitionException;

    /**
     * Undeploys a previously deployed process definition from the workflow engine.
     *
     * @param deploymentId the ID of the deployment to be removed
     * @throws ProcessDefinitionException if undeployment fails
     */
    void undeploy(String deploymentId) throws ProcessDefinitionException;

}

