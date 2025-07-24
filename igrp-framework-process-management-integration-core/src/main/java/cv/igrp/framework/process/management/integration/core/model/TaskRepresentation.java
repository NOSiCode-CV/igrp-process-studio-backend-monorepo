package cv.igrp.framework.process.management.integration.core.model;

import java.util.List;

public interface TaskRepresentation {

    String getId();
    String getKey();
    String getName();
    List<VariableRepresentation> getVariables();

}
