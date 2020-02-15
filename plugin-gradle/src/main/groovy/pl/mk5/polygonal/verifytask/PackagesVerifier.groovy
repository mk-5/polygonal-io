package pl.mk5.polygonal.verifytask

import groovy.io.FileType
import groovy.transform.PackageScope
import org.gradle.api.Project
import pl.mk5.polygonal.LanguageRecognizer
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.PackageDefExtension
import pl.mk5.polygonal.verifytask.verifiers.PackageVerifierFactory

@PackageScope
class PackagesVerifier {

    static final DefaultDataDecorator defaultDataDecorator = new DefaultDataDecorator()
    static final PackagesSplitter defSplitWalker = new PackagesSplitter()

    final String language

    PackagesVerifier(Project project) {
        language = LanguageRecognizer.recognize(project)
    }

    void verify(File basePackageDir, List<PackageDefExtension> defExtensions) {
        if (!basePackageDir.isDirectory()) {
            throw new IllegalArgumentException(Message.BASE_PACKAGE_DOESNT_EXIST.withArgs(basePackageDir.path))
        }
        def packageVerifier = PackageVerifierFactory.forLanguage(language)
        defaultDataDecorator.decorate(defExtensions)
        def definitionsMap = defSplitWalker.walkAndSplit(basePackageDir, defExtensions)
        basePackageDir.eachFileRecurse(FileType.DIRECTORIES, { dir ->
            PackageDefExtension packageDefExtension = definitionsMap.get(dir, new PackageDefExtension(DirectoryPackageConverter.convert(basePackageDir, dir)))
            packageVerifier.verify(dir, packageDefExtension)
        })
    }
}
