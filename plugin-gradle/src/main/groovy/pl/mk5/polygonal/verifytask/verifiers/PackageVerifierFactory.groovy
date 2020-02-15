package pl.mk5.polygonal.verifytask.verifiers


import pl.mk5.polygonal.verifytask.parsers.PackageParserFactory

class PackageVerifierFactory {
    public static final JAVA = "java"
    public static final KOTLIN = "kotlin"

    static PackageVerifier forLanguage(String language) {
        switch (language) {
            case JAVA:
                return new BasicPackageVerifier(PackageParserFactory.forLanguage(language))
            case KOTLIN:
                return new KotlinPackageVerifier(PackageParserFactory.forLanguage(language))
            default:
                throw new IllegalArgumentException()
        }
    }
}
