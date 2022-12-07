package org.bardframework.commons.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JarFileUtils {

    private JarFileUtils() {
        /*
            prevent instantiation
         */
    }

    public static void update(File file, Map<String, byte[]> newEntries) throws Exception {
        if (MapUtils.isEmpty(newEntries)) {
            return;
        }
        try (FileSystem zipFileSystem = FileSystems.newFileSystem(file.toPath(), ClassLoader.getPlatformClassLoader())) {
            for (String entryPath : newEntries.keySet()) {
                Path pathInJarFile = zipFileSystem.getPath(entryPath);
                /*
                    copy a file into the zip file
                 */
                Files.copy(new ByteArrayInputStream(newEntries.get(entryPath)), pathInJarFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void modify(File file, List<JarModifier> modifiers) throws Exception {
        try (JarFile jarFile = new JarFile(file)) {
            for (JarModifier modifier : modifiers) {
                modifier.doWork(jarFile);
            }
        }
    }

    public interface JarModifier {
        void doWork(JarFile jarFile) throws IOException;
    }

    public static class JarDirectoryExtractor implements JarModifier {
        protected final String zipDirectoryEntry;
        protected final Path savePath;

        public JarDirectoryExtractor(String zipDirectoryEntry, Path savePath) {
            this.zipDirectoryEntry = zipDirectoryEntry;
            this.savePath = savePath;
        }

        @Override
        public void doWork(JarFile jarFile) {
            jarFile.stream().filter(jarEntry -> !jarEntry.isDirectory() && jarEntry.toString().startsWith(this.getZipDirectoryEntry())).forEach(jarEntry -> {
                File targetFile = this.getSavePath().resolve(jarEntry.toString()).toFile();
                try {
                    FileUtils.copyInputStreamToFile(jarFile.getInputStream(jarEntry), targetFile);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });
        }

        public String getZipDirectoryEntry() {
            return zipDirectoryEntry;
        }

        public Path getSavePath() {
            return savePath;
        }
    }

    public static class JarTextFileExtractor implements JarModifier {
        protected final String zipEntry;
        protected final File savePath;
        protected final Map<String, String> replacements = new HashMap<>();

        public JarTextFileExtractor(String jarEntry, File savePath) {
            this(jarEntry, savePath, Collections.emptyMap());
        }

        public JarTextFileExtractor(String jarEntry, File savePath, Map<String, String> replacements) {
            this.zipEntry = jarEntry;
            this.savePath = savePath;
            this.replacements.putAll(replacements);
        }

        @Override
        public void doWork(JarFile jarFile) throws IOException {
            String content;
            try (InputStream inputStream = jarFile.getInputStream(jarFile.getEntry(this.getZipEntry()))) {
                content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                for (Map.Entry<String, String> entry : this.getReplacements().entrySet()) {
                    Matcher matcher = Pattern.compile(entry.getKey()).matcher(content);
                    if (!matcher.find()) {
                        throw new IllegalStateException(String.format("can't find [%s] in [%s@%s] to replace it!", entry.getKey(), this.getZipEntry(), jarFile.getName()));
                    }
                    content = matcher.replaceAll(entry.getValue());
                }
            }
            FileUtils.writeStringToFile(this.getSavePath(), content, StandardCharsets.UTF_8);
        }

        public String getZipEntry() {
            return zipEntry;
        }

        public File getSavePath() {
            return savePath;
        }

        public Map<String, String> getReplacements() {
            return replacements;
        }
    }

    public static class JarFileEntrySaver implements JarModifier {
        protected final String zipEntry;
        protected final File savePath;
        protected final Map<byte[], byte[]> replacements = new HashMap<>();

        public JarFileEntrySaver(String jarEntry, File savePath) {
            this(jarEntry, savePath, Collections.emptyMap());
        }

        public JarFileEntrySaver(String jarEntry, File savePath, Map<byte[], byte[]> replacements) {
            this.zipEntry = jarEntry;
            this.savePath = savePath;
            this.replacements.putAll(replacements);
        }

        @Override
        public void doWork(JarFile jarFile) throws IOException {
            byte[] entryBytes;
            try (InputStream inputStream = jarFile.getInputStream(jarFile.getEntry(this.getZipEntry()))) {
                entryBytes = IOUtils.toByteArray(inputStream);
                for (Map.Entry<byte[], byte[]> entry : this.getReplacements().entrySet()) {
                    if (!BytesUtils.isExist(entryBytes, entry.getKey())) {
                        throw new IllegalStateException(String.format("can't find [%s] in [%s@%s] to replace it!", Arrays.toString(entry.getKey()), this.getZipEntry(), jarFile.getName()));
                    }
                    entryBytes = BytesUtils.replace(entryBytes, entry.getKey(), entry.getValue());
                }
            }
            FileUtils.writeByteArrayToFile(this.getSavePath(), entryBytes);
        }

        public String getZipEntry() {
            return zipEntry;
        }

        public File getSavePath() {
            return savePath;
        }

        public Map<byte[], byte[]> getReplacements() {
            return replacements;
        }
    }
}
