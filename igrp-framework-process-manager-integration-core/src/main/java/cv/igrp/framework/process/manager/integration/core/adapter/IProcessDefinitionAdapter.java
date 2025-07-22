package cv.igrp.framework.process.manager.integration.core.adapter;

import cv.igrp.framework.process.manager.integration.core.model.ProcessDefinitionRepresentation;
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
     * Saves a new process definition.
     * Typically used for storing metadata in the application layer.
     *
     * @param processDefinition the process definition to save
     * @return the saved process definition with any assigned ID or metadata
     */
    ProcessDefinitionRepresentation save(ProcessDefinitionRepresentation processDefinition);

    /**
     * Updates an existing process definition by its ID.
     *
     * @param processDefinitionId the ID of the process definition to update
     * @param processDefinition the new data to update
     * @return the updated process definition
     */
    ProcessDefinitionRepresentation update(String processDefinitionId, ProcessDefinitionRepresentation processDefinition);

    /**
     * Deletes a process definition from the system (metadata or engine-level).
     *
     * @param processDefinitionId the ID of the process definition to delete
     */
    void delete(String processDefinitionId);

    /**
     * Deploys a BPMN XML content into the underlying workflow engine.
     *
     * @param processDefinitionId the ID associated with this deployment
     * @param bpmnXmlContent the BPMN 2.0 XML content to deploy
     */
    void deploy(String processDefinitionId, String bpmnXmlContent);

    /**
     * Removes a deployed process definition from the engine.
     *
     * @param processDefinitionId the ID of the process definition to undeploy
     */
    void undeploy(String processDefinitionId);

    /**
     * Finds a process definition by its ID.
     *
     * @param processDefinitionId the ID to search for
     * @return the corresponding process definition, or null if not found
     */
    ProcessDefinitionRepresentation findById(String processDefinitionId);

    /**
     * Retrieves all available process definitions.
     *
     * @return a list of all process definitions
     */
    List<ProcessDefinitionRepresentation> findAll();

}
