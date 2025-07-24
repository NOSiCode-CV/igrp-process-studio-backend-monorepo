package cv.igrp.framework.process.management.integration.activiti.adapter;

import cv.igrp.framework.process.management.integration.activiti.ActivitiProcessDefinitionApplication;
import cv.igrp.framework.process.management.integration.core.model.IgrpProcessDefinitionRepresentation;
import cv.igrp.framework.process.management.integration.core.model.ProcessDefinitionRepresentation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = ActivitiProcessDefinitionApplication.class)
class ActivitiProcessDefinitionAdapterIntegrationTest {

    @Autowired
    private ActivitiProcessDefinitionAdapter processDefinitionAdapter;

    @Test
    void testDeploy() throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("samples/dynamicProcess.bpmn20.xml").toURI());
        String bpmnXmlContent = Files.readString(path);
        ProcessDefinitionRepresentation pdr = IgrpProcessDefinitionRepresentation.builder()
                .key("dynamicProcess")
                .name("DynamicProcess")
                .bpmnXml(bpmnXmlContent)
                .resourceName("dynamicProcess.bpmn20.xml")
                .build();
        pdr = processDefinitionAdapter.deploy(pdr);

        assertEquals("Process_14icc9a", pdr.getKey());
        assertNotNull(pdr.getId());
        assertNotNull(pdr.getDeploymentId());
        assertNotNull(pdr.getVersion());
        assertNotNull(pdr.getBpmnXml());
        assertNotNull(pdr.getBpmnSourceType());
    }

    @Test
    void testUnDeploy() {
        final String deploymentId = "8ea2d1e8-67fc-11f0-8b4d-00090faa0001";
        processDefinitionAdapter.undeploy(deploymentId);
    }

}