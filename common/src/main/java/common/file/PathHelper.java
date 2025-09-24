package common.file;

public class PathHelper {
    public static final String ROOT_FOLDER = "travelnew";
    public static final String PIPELINE_FOLDER = "_travelnew_pipeline";
    public static final String LIB_FOLDER = ROOT_FOLDER + "/lib/";
    public static final String TEST_DEFINITION_DATA_FOLDER = ROOT_FOLDER + "/testdefinitions/src/test/data/";

    private PathHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String getTestDataFolder(String absolutePath) {
        return genericFolder(absolutePath, TEST_DEFINITION_DATA_FOLDER);
    }

    public static String getLibFolder(String absolutePath) {
        return genericFolder(absolutePath, LIB_FOLDER);
    }

    private static String genericFolder(String absolutePath, String folder) {
        String[] split = absolutePath.split(ROOT_FOLDER);
        if (absolutePath.contains(PIPELINE_FOLDER)) {
            return split[0] + ROOT_FOLDER + "/" + folder;
        } else {
            return split[0] + folder;
        }
    }

}
