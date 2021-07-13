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
        Language language = LanguageRecognizer.recognize(project);

        // Then
        assert language == Language.KOTLIN;
    }

    @Test
    void shouldRecognizeOtherLanguageAsJava() {
        // Given
        MavenProject project = mock(MavenProject.class);

        // When
        Language language = LanguageRecognizer.recognize(project);

        // Then
        assert language == Language.JAVA;
    }
}
