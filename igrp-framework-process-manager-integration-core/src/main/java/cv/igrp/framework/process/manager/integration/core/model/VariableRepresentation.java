package cv.igrp.framework.process.manager.integration.core.model;

public interface VariableRepresentation {

    String getName();
    String getType();
    Object getValue();
    boolean isRequired();

}
