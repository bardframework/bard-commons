package org.bardframework.commons.utils.reflection;

import java.io.File;
import java.io.FileFilter;

class MatchAllFileFilter implements FileFilter {
    public boolean accept(File pathname) {
        return true;
    }
}
