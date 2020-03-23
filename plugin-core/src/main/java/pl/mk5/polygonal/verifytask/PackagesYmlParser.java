package pl.mk5.polygonal.verifytask;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.plugin.PackageDef;
import pl.mk5.polygonal.plugin.Polygon;

class PackagesYmlParser {

    private static final String POLYGON_EXTENSION = "polygon";
    private static final String PACKAGES = "packages";
    private static final String YML_EXTENSION = ".yml";

    static final Map<String, String> keywordsMap;

    static {
        keywordsMap = new LinkedHashMap<>(7);
        keywordsMap.put("public", "publicScope");
        keywordsMap.put("packagePrivate", "packagePrivateScope");
        keywordsMap.put("protected", "protectedScope");
        keywordsMap.put("internal", "internalScope");
        keywordsMap.put("types", "types");
        keywordsMap.put("required", "required");
        keywordsMap.put("name", "name");
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public Polygon parseYml(File file) {
        if (file == null || !file.exists() || !Files.isReadable(file.toPath())) {
            throw new IllegalArgumentException(Message.YML_TEMPLATE_NOT_FOUND.toString());
        } else if (!file.getName().endsWith(YML_EXTENSION)) {
            throw new IllegalArgumentException(Message.TEMPLATE_IS_NOT_YML_FILE.withArgs(file.getName()));
        }

        List<PackageDef> extensions = new ArrayList<>();
        MapToPackageDefConverter mapConverter = new MapToPackageDefConverter();
        Yaml yaml = new Yaml();
        Map<String, Object> ymlMap = (Map<String, Object>) yaml.loadAs(new String(Files.readAllBytes(file.toPath())), Map.class);
        Conditions.check(ymlMap.containsKey(POLYGON_EXTENSION), Message.TEMPLATE_CANNOT_FIND_POLYGON.withArgs(POLYGON_EXTENSION, file.getName()));
        Map<String, Object> polygonMap = (Map<String, Object>) ymlMap.get(POLYGON_EXTENSION);
        PackageDef rootPackageDef = new PackageDef("");
        polygonMap.forEach((key, value) -> {
            if (keywordsMap.containsKey(key)) {
                ObjectHelper.set(rootPackageDef, keywordsMap.get(key), value);
            }
        });
        TypesValidator.validate(rootPackageDef.getTypes());
        extensions.add(rootPackageDef);
        if (polygonMap.containsKey(PACKAGES)) {
            Conditions.check(polygonMap.get(PACKAGES) instanceof Map, Message.TEMPLATE_PACKAGES_SHOULD_CONTAIN_PACKAGES_DEFINITIONS.toString());
            List<PackageDef> packagesDefinitions = mapConverter.convert((Map) polygonMap.get(PACKAGES), keywordsMap);
            extensions.addAll(packagesDefinitions);
        }
        return new Polygon(extensions);
    }
}
