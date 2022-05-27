package org.bardframework.commons.web;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.commons.web.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WildcardReloadableMessageSource extends ReloadableResourceBundleMessageSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WildcardReloadableMessageSource.class);

    public void addWildcardBaseNames(String... baseNames) {
        List<Resource> resources = new ArrayList<>();
        /*
            we use the Set to auto remove duplicates
         */
        Set<String> finalBaseNames = new HashSet<>();
        try {
            for (String basename : baseNames) {
                basename = StringUtils.trimToEmpty(basename);
                resources.addAll(List.of(ResourceUtils.getResources(basename)));
            }
            LOGGER.info("[{}] resource found from [{}] wildcard base names:\n\t{}", resources.size(), baseNames, resources.stream().map(Object::toString).collect(Collectors.joining("\n\t")));
            for (Resource resource : resources) {
                String url = resource.getURL().toString();
                String basename = null;
                if (resource instanceof FileSystemResource) {
                    String path = StringUtils.substringBetween(url, "file:/", ".properties");
                    if (StringUtils.isNotBlank(path)) {
                        basename = "file:/" + path;
                    }
                } else if (resource instanceof ClassPathResource) {
                    String path = StringUtils.substringBefore(url, ".properties");
                    if (StringUtils.isNotBlank(path)) {
                        basename = path;
                    }
                } else if (resource instanceof UrlResource) {
                    String path = this.getBaseName(resource);
                    if (StringUtils.isNotBlank(path)) {
                        basename = "classpath:" + path;
                    }
                } else {
                    basename = url;
                }
                if (StringUtils.isNotBlank(basename)) {
                    finalBaseNames.add(this.processBasename(basename));
                } else {
                    LOGGER.error("can't detect base name from i18n file [{}]", resource);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        LOGGER.info("final base names from wildcard are:\n\t{}", String.join("\n\t", finalBaseNames));
        super.addBasenames(finalBaseNames.toArray(String[]::new));
    }

    public Properties getAllProperties(Locale locale) {
        this.clearCacheIncludingAncestors();
        PropertiesHolder propertiesHolder = this.getMergedProperties(locale);
        return propertiesHolder.getProperties();
    }

    private String processBasename(String baseName) {
        String prefix = StringUtils.substringBeforeLast(baseName, "/");
        String name = StringUtils.substringAfterLast(baseName, "/");
        do {
            name = StringUtils.substringBeforeLast(name, "_");
        } while (name.contains("_"));
        return prefix + "/" + name;
    }

    private String getBaseName(Resource resource) throws IOException {
        String baseName = StringUtils.substringAfterLast(resource.getURL().toString(), ".jar!/");
        if (baseName.contains("classes!/")) {
            baseName = StringUtils.substringAfterLast(baseName, "classes!/");
        }
        if (baseName.contains("classes/")) {
            baseName = StringUtils.substringAfterLast(baseName, "classes/");
        }
        return StringUtils.substringBefore(baseName, ".properties");
    }
}