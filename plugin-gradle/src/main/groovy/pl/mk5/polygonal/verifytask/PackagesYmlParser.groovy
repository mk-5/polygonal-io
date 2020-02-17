package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import org.yaml.snakeyaml.Yaml
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.plugin.PackageDefExtension
import pl.mk5.polygonal.plugin.PolygonExtension

import java.nio.file.Files

@PackageScope
class PackagesYmlParser {

    static final String POLYGON_EXTENSION = "polygon"
    static final String PACKAGES = "packages"
    static final String YML_EXTENSION = ".yml"
    static final Map<String, String> keywordsMap = [
            "public"        : "publicScope",
            "packagePrivate": "packagePrivateScope",
            "protected"     : "protectedScope",
            "internal"      : "internalScope",
            "types"         : "types",
            "required"      : "required",
            "name"          : "name"
    ]

    PolygonExtension parseYml(File file) {
        if (file == null || !file.exists() || !Files.isReadable(file.toPath())) {
            throw new IllegalArgumentException(Message.YML_TEMPLATE_NOT_FOUND.toString())
        } else if (!file.name.endsWith(YML_EXTENSION)) {
            throw new IllegalArgumentException(Message.TEMPLATE_IS_NOT_YML_FILE.withArgs(file.name))
        }
        def extensions = new ArrayList<>()
        def mapConverter = new MapToPackageDefConverter()
        def yaml = new Yaml()
        new FileInputStream(file).withCloseable { is ->
            def ymlMap = yaml.load(is);
            Conditions.check(ymlMap.containsKey(POLYGON_EXTENSION), Message.TEMPLATE_CANNOT_FIND_POLYGON.withArgs(POLYGON_EXTENSION, file.name))
            def polygonMap = ymlMap.get(POLYGON_EXTENSION)
            def rootPackageDef = new PackageDefExtension('')
            polygonMap.entrySet().forEach { entry ->
                if (keywordsMap.containsKey(entry.key)) {
                    rootPackageDef.setProperty(keywordsMap.get(entry.key), entry.value)
                }
            }
            TypesValidator.validate(rootPackageDef.types)
            extensions.add(rootPackageDef)
            if (polygonMap.containsKey(PACKAGES)) {
                Conditions.check(polygonMap.get(PACKAGES) instanceof Map, Message.TEMPLATE_PACKAGES_SHOULD_CONTAIN_PACKAGES_DEFINITIONS.toString())
                def packagesDefinitions = mapConverter.convert((Map) polygonMap.get(PACKAGES), keywordsMap)
                extensions.addAll(packagesDefinitions)
            }
        }
        return new PolygonExtension(packagesDefs: extensions)
    }
}
