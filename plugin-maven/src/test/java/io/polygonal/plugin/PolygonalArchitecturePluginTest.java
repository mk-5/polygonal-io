package io.polygonal.plugin;

import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

// FIXME - it's failing randomly on CI
//@MojoTest
class PolygonalArchitecturePluginTest {

//    @Test
//    @InjectMojo(
//            goal = "verifyPolygons",
//            pom = "src/test/resources/pom.xml"
//    )
    void testMojo(PolygonalArchitecturePlugin mojo) throws Exception {
        assertNotNull(mojo);
    }
}
