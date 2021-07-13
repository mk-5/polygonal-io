package io.polygonal


import org.gradle.api.Project

class LanguageRecognizer {
    private static final KOTLIN_PLUGIN_NAME = 'org.jetbrains.kotlin'

    static Language recognize(Project project) {
        if (project.plugins.find { plugin -> plugin.class.name.contains(KOTLIN_PLUGIN_NAME) } != null) {
            return Language.KOTLIN
        }
        if (project.plugins.find { plugin -> plugin.class.name =~ /Java(.*)Plugin$/ } != null) {
            return Language.JAVA
        }
        throw new IllegalStateException(Message.CANNOT_RECOGNIZE_LANGUAGE.withArgs(project.name))
    }
}
