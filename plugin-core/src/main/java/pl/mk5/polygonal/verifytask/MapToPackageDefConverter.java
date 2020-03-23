package pl.mk5.polygonal.verifytask;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.mk5.polygonal.Message;
import pl.mk5.polygonal.plugin.PackageDef;

class MapToPackageDefConverter {

    private static final String TYPE_FIELD = "type";

    @SuppressWarnings("unchecked")
    List<PackageDef> convert(Map<String, Object> defsMap, Map<String, String> keywordsDictionary) {
        List<PackageDef> buffer = new ArrayList<>();
        defsMap.forEach((packageName, value) -> {
            processPackageDef(packageName, (Map<String, Object>) defsMap.get(packageName), keywordsDictionary, buffer);
        });
        return buffer;
    }

    @SuppressWarnings("unchecked")
    private void processPackageDef(String name,
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
                processPackageDef(MessageFormat.format("{0}.{1}", name, key).replaceAll("^\\.", ""), (Map<String, Object>) value, keywordsDictionary, buffer);
            } else {
                ObjectHelper.set(packageDef, newKey, value);
            }
        });
        buffer.add(packageDef);
    }
}
