package org.bardframework.commons.reflection;

import java.io.File;
import java.io.FileFilter;

class MatchAllFileFilter implements FileFilter {
    public boolean accept(File pathname) {
        return true;
    }
}
