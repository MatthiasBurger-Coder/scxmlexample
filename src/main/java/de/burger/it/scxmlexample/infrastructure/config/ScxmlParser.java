package de.burger.it.scxmlexample.infrastructure.config;

import java.io.IOException;

import org.apache.commons.scxml2.io.SCXMLReader;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.SCXML;
import org.springframework.core.io.ClassPathResource;

public class ScxmlParser {

    public SCXML parseScxml(String classpathLocation) {
        ClassPathResource resource = new ClassPathResource(classpathLocation);
        try {
            return SCXMLReader.read(resource.getURL());
        } catch (IOException | ModelException exception) {
            throw new IllegalStateException("Unable to parse SCXML definition", exception);
        }
    }
}
