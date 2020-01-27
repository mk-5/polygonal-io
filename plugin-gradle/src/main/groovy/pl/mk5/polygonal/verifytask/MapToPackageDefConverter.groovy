package pl.mk5.polygonal.verifytask

import groovy.transform.PackageScope
import org.codehaus.groovy.runtime.InvokerHelper
import pl.mk5.polygonal.Message
import pl.mk5.polygonal.polygons.PackageDefExtension

import java.util.function.BiConsumer
import java.util.stream.Collectors

@PackageScope
class MapToPackageDefConverter {

    private final List<PackageDefExtension> buffer = new ArrayList<>()

    List<PackageDefExtension> convert(Map<String, Object> defsMap, Map<String, String> keywordsMap) {
        defsMap.keySet().each { packageName ->
            processPackageDef(packageName, defsMap[packageName] as Map, keywordsMap)
        }
        def result = new ArrayList<PackageDefExtension>(buffer)
        buffer.clear()
        return result
    }

    List<PackageDefExtension> convert(Map<String, Object> defsMap) {
        def packageDefFields = new PackageDefExtension().properties.keySet()
        return convert(defsMap, packageDefFields.stream().collect(Collectors.toMap({ p -> p }, { p -> p })) as Map)
    }

    private void processPackageDef(String name, Map<String, Object> extension, Map<String, String> keywordsMap) {
        def packageDefFields = new PackageDefExtension().properties.keySet()
        def packageDef = new PackageDefExtension()
        extension.put("name", name)
        extension.keySet().each { key ->
            if (keywordsMap.containsKey(key)) {
                InvokerHelper.setProperty(packageDef, keywordsMap[key], extension[key])
            }
        }
        InvokerHelper.setProperties(packageDef, extension)
        TypesValidator.validate(packageDef.types)
        extension.forEach(new BiConsumer<String, Object>() {
            @Override
            void accept(String key, Object o) {
                if (!keywordsMap.containsKey(key)) {
                    assert o instanceof Map: Message.NOT_KEYWORD_FIELD_SHOULD_BE_MAP.withArgs(key)
                    processPackageDef([name, key].join(".").replaceAll("^\\.", ""), o, keywordsMap)
                }
            }
        })
        buffer.add(packageDef)
    }
}
