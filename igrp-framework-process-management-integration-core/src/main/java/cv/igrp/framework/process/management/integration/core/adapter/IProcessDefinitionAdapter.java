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
     * @param processDefinitionRepresentation the process definition representation
     *                                        containing metadata and BPMN XML
     * @return the deployed process definition representation including updated
     *         deployment metadata
     * @throws ProcessDefinitionException if deployment fails
     */
    ProcessDefinitionRepresentation deploy(ProcessDefinitionRepresentation processDefinitionRepresentation,
            java.util.Map<String, String> headers) throws ProcessDefinitionException;

    /**
     * Deploys the given process definition without extra headers.
     * 
     * @see #deploy(ProcessDefinitionRepresentation, java.util.Map)
     */
    default ProcessDefinitionRepresentation deploy(ProcessDefinitionRepresentation processDefinitionRepresentation)
            throws ProcessDefinitionException {
        return deploy(processDefinitionRepresentation, java.util.Collections.emptyMap());
    }

    /**
     * Undeploys a previously deployed process definition from the workflow engine.
     *
     * @param deploymentId the ID of the deployment to be removed
     * @param headers      a map of custom headers to be sent with the request
     * @throws ProcessDefinitionException if undeployment fails
     */
    void undeploy(String deploymentId, java.util.Map<String, String> headers) throws ProcessDefinitionException;

    /**
     * Undeploys a previously deployed process definition without extra headers.
     * 
     * @see #undeploy(String, java.util.Map)
     */
    default void undeploy(String deploymentId) throws ProcessDefinitionException {
        undeploy(deploymentId, java.util.Collections.emptyMap());
    }

}
