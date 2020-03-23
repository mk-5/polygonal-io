package pl.mk5.polygonal.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.verifytask.Conditions;
import pl.mk5.polygonal.verifytask.VerifyTaskAction;

@Mojo(name = "verifyPolygons", defaultPhase = LifecyclePhase.VERIFY)
public class PolygonalArchitecturePlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(required = true)
    String basePackage;

    @Parameter
    File sourcesDir;

    @Parameter
    File polygonTemplate;

    @Parameter
    boolean strictMode = true;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        assert project != null : Message.PACKAGE_REQUIRED;
        if (sourcesDir == null) {
            sourcesDir = new File(project.getBasedir(), "src/main/java");
        }
        if (polygonTemplate == null) {
            polygonTemplate = new File(project.getBasedir(), "src/main/resources/polygon.yml");
        }
        getLog().info(Message.USING_TEMPLATE.withArgs(polygonTemplate.getPath()));
        Conditions.check(polygonTemplate.canRead(), Message.YML_TEMPLATE_NOT_FOUND.msg());
        PolygonalArchitecture polygonalArchitecture = new PolygonalArchitecture();
        polygonalArchitecture.setBasePackage(basePackage);
        polygonalArchitecture.setSourcesDir(sourcesDir);
        polygonalArchitecture.setPolygonTemplate(polygonTemplate);
        polygonalArchitecture.setStrictMode(strictMode);
        getLog().info(Message.CHECKING_POLYGONS.withArgs(project.getArtifactId()));
        new VerifyTaskAction().accept(polygonalArchitecture, project);
    }
}
