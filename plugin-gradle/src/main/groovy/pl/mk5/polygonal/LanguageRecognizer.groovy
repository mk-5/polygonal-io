package pl.mk5.polygonal

import org.gradle.api.Project

class LanguageRecognizer {
    static final JAVA = 'java'
    static final KOTLIN = 'kotlin'

    private static final KOTLIN_PLUGIN_NAME = 'org.jetbrains.kotlin'

    static String recognize(Project project) {
        if (project.plugins.find { plugin -> plugin.class.name.contains(KOTLIN_PLUGIN_NAME) } != null) {
            return KOTLIN
        }
        if (project.plugins.find { plugin -> plugin.class.name =~ /Java(.*)Plugin$/ } != null) {
            return JAVA
        }
        throw new IllegalStateException(Message.CANNOT_RECOGNIZE_LANGUAGE.withArgs(project.name))
    }
}
