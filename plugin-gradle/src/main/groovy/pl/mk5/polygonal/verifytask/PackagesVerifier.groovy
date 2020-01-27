package pl.mk5.polygonal.verifytask

import groovy.io.FileType
import groovy.transform.PackageScope
import groovy.transform.TupleConstructor
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.polygons.PackageDefExtension
import pl.mk5.polygonal.verifytask.parsers.PackageParser

@PackageScope
@TupleConstructor(includeFields = true)
class PackagesVerifier {

    static final DefaultDataDecorator defaultDataDecorator = new DefaultDataDecorator()
    static final PackagesSplitter defSplitWalker = new PackagesSplitter()

    private final PackageParser packageParser

    void verify(File basePackageDir, List<PackageDefExtension> defExtensions) {
        if (!basePackageDir.isDirectory()) {
            throw new IllegalArgumentException(Message.BASE_PACKAGE_DOESNT_EXIST.withArgs(basePackageDir.path))
        }
        def packageVerifier = new PackageVerifier(packageParser)
        defaultDataDecorator.decorate(defExtensions)
        def definitionsMap = defSplitWalker.walkAndSplit(basePackageDir, defExtensions)
        basePackageDir.eachFileRecurse(FileType.DIRECTORIES, { dir ->
            PackageDefExtension packageDefExtension = definitionsMap.get(dir, new PackageDefExtension(DirectoryPackageConverter.convert(basePackageDir, dir)))
            packageVerifier.verify(dir, packageDefExtension)
        })
    }
}
