package io.polygonal.plugin;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.jupiter.api.Test;

import java.io.File;

class PolygonalArchitecturePluginTest extends AbstractMojoTestCase {

    @Test
    void testMojo() throws Exception {
        File testPom = new File(getBasedir(), "src/test/pom.xml");
        PolygonalArchitecturePlugin mojo = (PolygonalArchitecturePlugin) lookupMojo("PolygonalArchitecturePlugin", testPom);
        assertNotNull(mojo);
    }
}
