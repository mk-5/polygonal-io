package pl.mk5.polygonal.verifytask.parsers

class PackageParserFactory {
    public static final JAVA = "java"

    static PackageParser forLanguage(String language) {
        // first implementation -> only Java is supported language
        switch (language) {
            case JAVA:
                return new JavaPackageParser()
            default:
                throw new IllegalArgumentException()
        }
    }
}
