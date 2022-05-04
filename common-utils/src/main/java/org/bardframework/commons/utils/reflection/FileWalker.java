package org.bardframework.commons.utils.reflection;

import java.io.File;
import java.io.FileFilter;

class FileWalker {

    private FileFilter matchFilter;

    private FileFindHandler handler;

    private String baseDir;

    private int matchingFiles;

    private int allFiles;

    public FileWalker() {
        this(new MatchAllFileFilter(), new FileFindHandlerAdapter());
    }

    public FileWalker(FileFilter matchFilter) {
        this(matchFilter, new FileFindHandlerAdapter());
    }

    public FileWalker(FileFindHandler handler) {
        this(new MatchAllFileFilter(), handler);
    }

    public FileWalker(FileFilter matchFilter, FileFindHandler handler) {
        this.matchFilter = matchFilter;
        this.handler = handler;
    }

    public FileFilter getMatchFilter() {
        return matchFilter;
    }

    public void setMatchFilter(FileFilter matchFilter) {
        this.matchFilter = matchFilter;
    }

    public FileFindHandler getHandler() {
        return handler;
    }

    public void setHandler(FileFindHandler handler) {
        this.handler = handler;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public void walk() {
        try {
            File rootDir = new File(baseDir);
            walk(rootDir);
            // notify handler that we are done walking
            handler.onComplete();
        } catch (Exception e) {
            // if any sort of error occurs due to bad file path, or other issues, just catch and swallow
            // because we don't expect any Exceptions in normal course of usage
            e.printStackTrace();
        }
    }

    /**
     * Preorder traversal of tree
     *
     */
    protected void walk(File currentDir) {
        File[] files = currentDir.listFiles();
        if (null == files) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                walk(file);
            } else {

                if (matchFilter.accept(file)) {
                    matchingFiles++;
                    handler.handleFile(file);
                }
                allFiles++;
            }
        }

    }

    public int getMatchingFileCount() {
        return matchingFiles;
    }

    public int getAllFilesCount() {
        return allFiles;
    }

}
