package cv.igrp.framework.process.management.integration.core.config;


import cv.igrp.framework.process.management.integration.core.adapter.IProcessDefinitionAdapter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ProcessDefinitionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IProcessDefinitionAdapter onMissingProcessDefinitionAdapter() {
        throw new IllegalStateException("No Process Definition implementation found. Please include a specific Process Definition library.");
    }

}
