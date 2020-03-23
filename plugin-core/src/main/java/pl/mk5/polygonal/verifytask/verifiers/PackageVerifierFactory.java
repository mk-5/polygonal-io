package pl.mk5.polygonal.verifytask.verifiers;

import pl.mk5.polygonal.verifytask.parsers.PackageParserFactory;

public class PackageVerifierFactory {
    public static final String JAVA = "java";
    public static final String KOTLIN = "kotlin";

    public static PackageVerifier forLanguage(String language) {
        switch (language) {
            case JAVA:
                return new BasicPackageVerifier(PackageParserFactory.forLanguage(language));
            case KOTLIN:
                return new KotlinPackageVerifier(PackageParserFactory.forLanguage(language));
            default:
                throw new IllegalArgumentException();
        }
    }
}
