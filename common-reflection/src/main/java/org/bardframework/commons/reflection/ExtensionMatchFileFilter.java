package org.bardframework.commons.reflection;

import java.io.File;
import java.io.FileFilter;

class ExtensionMatchFileFilter implements FileFilter {

    private final String extension;

    public ExtensionMatchFileFilter(String extension) {
        this.extension = extension;
    }

    public boolean accept(File file) {
        if (this.extension == null) {
            return true;
        }
        return file.getName().endsWith("." + extension);
    }


}
