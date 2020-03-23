package pl.mk5.polygonal;

import org.apache.maven.model.Plugin;
import org.apache.maven.project.MavenProject;

public class LanguageRecognizer {

    static final String JAVA = "java";
    static final String KOTLIN = "kotlin";

    private static final String KOTLIN_PLUGIN_GROUP_ID = "org.jetbrains.kotlin";
    private static final String KOTLIN_PLUGIN_ARTIFACT_ID = "kotlin-maven-plugin";

    @SuppressWarnings("unchecked")
    public static String recognize(MavenProject project) {
        boolean isKotlin = project.getBuildPlugins()
                .stream()
                .anyMatch(p -> ((Plugin) p).getArtifactId().equals(KOTLIN_PLUGIN_ARTIFACT_ID)
                        && ((Plugin) p).getGroupId().equals(KOTLIN_PLUGIN_GROUP_ID));
        if (isKotlin) {
            return KOTLIN;
        } else {
            return JAVA;
        }
    }
}
