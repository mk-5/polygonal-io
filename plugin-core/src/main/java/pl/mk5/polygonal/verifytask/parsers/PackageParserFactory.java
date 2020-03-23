package pl.mk5.polygonal.verifytask.parsers;

public class PackageParserFactory {

    private static final String JAVA = "java";
    private static final String KOTLIN = "kotlin";

    public static PackageParser forLanguage(String language) {
        switch (language) {
            case JAVA:
                return new JavaPackageParser();
            case KOTLIN:
                return new KotlinPackageParser();
            default:
                throw new IllegalArgumentException();
        }
    }
}
