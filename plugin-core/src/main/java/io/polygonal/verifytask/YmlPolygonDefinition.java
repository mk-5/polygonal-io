package io.polygonal.verifytask;

import com.google.common.collect.ImmutableMap;
import io.polygonal.Message;
import io.polygonal.plugin.PackageDef;
import io.polygonal.plugin.Polygon;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class YmlPolygonDefinition {

    private static final String POLYGON_EXTENSION = "polygon";
    private static final String PACKAGES = "packages";
    private static final String YML_EXTENSION = ".yml";

    static final Map<String, String> KEYWORDS_MAP = ImmutableMap.<String, String>builder()
            .put("public", "publicScope")
            .put("packagePrivate", "packagePrivateScope")
            .put("protected", "protectedScope")
            .put("internal", "internalScope")
            .put("types", "types")
            .put("required", "required")
            .put("name", "name")
            .build();

    private final File file;

    YmlPolygonDefinition(File file) {
        if (file == null || !file.exists() || !Files.isReadable(file.toPath())) {
            throw new IllegalArgumentException(Message.YML_TEMPLATE_NOT_FOUND.withArgs(file != null ? file.getPath() : ""));
        } else if (!file.getName().endsWith(YML_EXTENSION)) {
            throw new IllegalArgumentException(Message.TEMPLATE_IS_NOT_YML_FILE.withArgs(file.getName()));
        }
        this.file = file;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Polygon asPolygon() {
        List<PackageDef> extensions = new ArrayList<>();
        PackageDef rootPackageDef = new PackageDef("");
        Map<String, Object> polygonMap = getPolygonMapFromYML(file, rootPackageDef);
        extensions.add(rootPackageDef);
        if (polygonMap.containsKey(PACKAGES)) {
            Conditions.check(polygonMap.get(PACKAGES) instanceof Map, Message.TEMPLATE_PACKAGES_SHOULD_CONTAIN_PACKAGES_DEFINITIONS.toString());
            List<PackageDef> packagesDefinitions = MapToPackageDefConverter.convertToPackageDefinitions((Map) polygonMap.get(PACKAGES), KEYWORDS_MAP);
            extensions.addAll(packagesDefinitions);
        }
        return new Polygon(extensions);
    }

    @SneakyThrows
    @SuppressWarnings({"unchecked"})
    private Map<String, Object> getPolygonMapFromYML(File ymlFile, PackageDef rootPackageDef) {
        Map<String, Object> ymlMap = (Map<String, Object>) new Yaml().loadAs(new String(Files.readAllBytes(ymlFile.toPath())), Map.class);
        Conditions.check(ymlMap.containsKey(POLYGON_EXTENSION), Message.TEMPLATE_CANNOT_FIND_POLYGON.withArgs(POLYGON_EXTENSION, ymlFile.getName()));
        Map<String, Object> polygonMap = (Map<String, Object>) ymlMap.get(POLYGON_EXTENSION);
        polygonMap.forEach((key, value) -> {
            if (KEYWORDS_MAP.containsKey(key)) {
                ObjectHelper.set(rootPackageDef, KEYWORDS_MAP.get(key), value);
            }
        });
        TypesValidator.validate(rootPackageDef.getTypes());
        return polygonMap;
    }
}
