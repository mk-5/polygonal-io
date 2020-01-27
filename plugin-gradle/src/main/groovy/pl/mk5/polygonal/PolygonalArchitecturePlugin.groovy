package pl.mk5.polygonal

import org.gradle.api.Plugin
import org.gradle.api.Project

class PolygonalArchitecturePlugin implements Plugin<Project> {
    void apply(Project project) {
        def task = new ProjectDispatcher(project).dispatch()
        def checkTask = project.getTasksByName("check", true)
        assert checkTask.size() > 0: Message.CHECK_TASK_NOT_FOUND.withArgs(project.name)
        checkTask.forEach({ ct -> ct.dependsOn(task) })
    }
}
