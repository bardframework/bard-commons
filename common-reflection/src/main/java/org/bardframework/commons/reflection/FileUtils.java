package org.bardframework.commons.reflection;

class FileUtils {

    private FileUtils() {
    }

    /**
     * Gets the file name without the extension
     *
     * @param fileName name of the file including extension
     * @return
     */
    public static String removeExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String modifiedName = fileName;
        int index = fileName.lastIndexOf(".");
        if (index > -1) {
            modifiedName = fileName.substring(0, index);
        }
        return modifiedName;
    }
}
