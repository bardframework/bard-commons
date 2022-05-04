package org.bardframework.commons.utils.reflection;

import java.io.File;

/**
 * Convert a File object to a Class
 */
class FileToClassConverter {

    private String classPathRoot;

    public FileToClassConverter(String classPathRoot) {
        setClassPathRoot(classPathRoot);
    }

    public void setClassPathRoot(String classPathRoot) {
        if (classPathRoot == null) {
            throw new RuntimeException("Class path root must not be null");
        }
        this.classPathRoot = classPathRoot;
    }

    public Class convertToClass(File classFile) {
        Class classInstance = null;
        if (classFile.getAbsolutePath().startsWith(classPathRoot) && classFile.getAbsolutePath().endsWith(".class")) {
            classInstance = getClassFromName(classFile.getAbsolutePath());
        }
        return classInstance;
    }

    private Class getClassFromName(String fileName) {
        try {
            String className = removeClassPathBase(fileName);
            className = this.removeExtension(className);
            return Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String removeClassPathBase(String fileName) {
        String classPart = fileName.substring(classPathRoot.length() + 1);
        return classPart.replace(File.separatorChar, '.');
    }

    private String removeExtension(String fileName) {
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
