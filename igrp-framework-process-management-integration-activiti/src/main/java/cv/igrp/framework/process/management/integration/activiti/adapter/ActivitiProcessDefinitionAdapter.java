package cv.igrp.framework.process.management.integration.activiti.adapter;


import cv.igrp.framework.process.management.integration.core.adapter.IProcessDefinitionAdapter;
import cv.igrp.framework.process.management.integration.core.exception.ProcessDefinitionException;
import cv.igrp.framework.process.management.integration.core.model.BpmnSourceType;
import cv.igrp.framework.process.management.integration.core.model.IgrpProcessDefinitionRepresentation;
import cv.igrp.framework.process.management.integration.core.model.ProcessDefinitionRepresentation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Objects;


@Service
public class ActivitiProcessDefinitionAdapter implements IProcessDefinitionAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiProcessDefinitionAdapter.class);

    private RepositoryService repositoryService;

    public ActivitiProcessDefinitionAdapter(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public ProcessDefinitionRepresentation deploy(ProcessDefinitionRepresentation processDefinitionRepresentation) throws ProcessDefinitionException {
        try{
            final String resourceName = Objects.requireNonNull(processDefinitionRepresentation.getResourceName(), "The resource name is required for deployment. Ex: dynamicProcess.bpmn20.xml");
            Deployment deployment = repositoryService.createDeployment()
                    .addString(resourceName, processDefinitionRepresentation.getBpmnXml())
                    .name(processDefinitionRepresentation.getName() != null && !processDefinitionRepresentation.getName().isBlank()
                            ? processDefinitionRepresentation.getName()
                            : processDefinitionRepresentation.getDescription())
                    .key(Objects.requireNonNull(processDefinitionRepresentation.getKey(), "The key is required for deployment."))
                    .deploy();
            ProcessDefinition processDefinition = getProcessDefinition(deployment.getId());
            final String bpmnXml = getBpmnXml(deployment.getId(), processDefinition.getResourceName());
            return IgrpProcessDefinitionRepresentation.builder()
                    .id(processDefinition.getId())
                    .key(processDefinition.getKey())
                    .name(processDefinition.getName())
                    .description(processDefinition.getDescription())
                    .version(String.valueOf(processDefinition.getVersion()))
                    .bpmnXml(bpmnXml)
                    .bpmnSourceType(BpmnSourceType.INLINE_XML)
                    .deployed(true)
                    .deploymentId(deployment.getId())
                    .deployedAt(deployment.getDeploymentTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Failed to deploy BPMN XML", ex);
            throw new ProcessDefinitionException("An error occurred when deploy a new process definition bpmn xml", ex);
        }
    }

    @Override
    public void undeploy(String deploymentId) throws ProcessDefinitionException {
        try{
            repositoryService.deleteDeployment(deploymentId, true);
        } catch (Exception ex) {
            LOGGER.error("Failed to undeploy the process definition", ex);
            throw new ProcessDefinitionException("An error occurred when undeploy the process definition", ex);
        }
    }

    private ProcessDefinition getProcessDefinition(final String deploymentId) {
        return repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
    }

    private String getBpmnXml(final String deploymentId, final String resourceName) throws Exception {

        InputStream bpmnStream = repositoryService.getResourceAsStream(deploymentId, resourceName);

        return new String(bpmnStream.readAllBytes(), StandardCharsets.UTF_8);
    }

}
