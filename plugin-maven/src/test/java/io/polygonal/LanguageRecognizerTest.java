package io.polygonal;

import org.apache.maven.model.Plugin;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LanguageRecognizerTest {

    @Test
    void shouldRecognizeKotlinLanguage() {
        // Given
        MavenProject project = mock(MavenProject.class);
        List<Plugin> plugins = Collections.singletonList(mock(Plugin.class));
        when(plugins.get(0).getArtifactId()).thenReturn("kotlin-maven-plugin");
        when(plugins.get(0).getGroupId()).thenReturn("org.jetbrains.kotlin");
        when(project.getBuildPlugins()).thenReturn(plugins);

        // When
        String language = LanguageRecognizer.recognize(project);

        // Then
        assert language.equals(LanguageRecognizer.KOTLIN);
    }

    @Test
    void shouldRecognizeOtherLanguageAsJava() {
        // Given
        MavenProject project = mock(MavenProject.class);

        // When
        String language = LanguageRecognizer.recognize(project);

        // Then
        assert language.equals(LanguageRecognizer.JAVA);
    }
}
