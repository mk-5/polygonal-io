package pl.mk5.polygonal.verifytask.parsers

class PackageParserFactory {
    public static final JAVA = "java"
    public static final KOTLIN = "kotlin"

    static PackageParser forLanguage(String language) {
        switch (language) {
            case JAVA:
                return new JavaPackageParser()
            case KOTLIN:
                return new KotlinPackageParser()
            default:
                throw new IllegalArgumentException()
        }
    }
}
