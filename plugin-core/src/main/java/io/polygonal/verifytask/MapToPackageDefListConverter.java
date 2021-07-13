package io.polygonal.verifytask;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.polygonal.Message;
import io.polygonal.plugin.Conditions;
import io.polygonal.plugin.PackageDef;

class MapToPackageDefListConverter {
    private static final String TYPE_FIELD = "type";

    private MapToPackageDefListConverter(){}

    @SuppressWarnings("unchecked")
    static List<PackageDef> convertToPackageDefinitions(Map<String, Object> defsMap, Map<String, String> keywordsDictionary) {
        List<PackageDef> buffer = new ArrayList<>();
        defsMap.forEach((packageName, value) -> {
            convertToPackageDef(packageName, (Map<String, Object>) defsMap.get(packageName), keywordsDictionary, buffer);
        });
        return buffer;
    }

    @SuppressWarnings("unchecked")
    private static void convertToPackageDef(String name,
                                     Map<String, Object> mapData,
                                     Map<String, String> keywordsDictionary,
                                     List<PackageDef> buffer) {
        PackageDef packageDef = new PackageDef();
        mapData.put("name", name);
        mapData.forEach((key, value) -> {
            String newKey = keywordsDictionary.getOrDefault(key, key);
            if (newKey.equals(TYPE_FIELD)) {
                TypesValidator.validate(value);
            }
            if (!keywordsDictionary.containsKey(key)) {
                Conditions.check(value instanceof Map, Message.NOT_KEYWORD_FIELD_SHOULD_BE_MAP.withArgs(key));
                convertToPackageDef(MessageFormat.format("{0}.{1}", name, key).replaceAll("^\\.", ""), (Map<String, Object>) value, keywordsDictionary, buffer);
            } else {
                ReflectionHelper.set(packageDef, newKey, value);
            }
        });
        buffer.add(packageDef);
    }
}
