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
            LOGGER.debug("[{}] resource found from [{}] wildcard base names:\n\t{}", resources.size(), baseNames, resources.stream().map(Object::toString).collect(Collectors.joining("\n\t")));
            for (Resource resource : resources) {
                String uri = resource.getURI().toString();
                String basename;
                if (resource instanceof FileSystemResource) {
                    basename = "classpath:" + StringUtils.substringBetween(uri, "/classes/", ".properties");
                } else if (resource instanceof ClassPathResource) {
                    basename = StringUtils.substringBefore(uri, ".properties");
                } else if (resource instanceof UrlResource) {
                    basename = "classpath:" + this.getBaseName(resource);
                } else {
                    basename = uri;
                }
                basename = this.processBasename(basename);
                finalBaseNames.add(basename);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        LOGGER.debug("final base names from wildcard are:\n\t{}", String.join("\n\t", finalBaseNames));
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
        String baseName = StringUtils.substringAfterLast(resource.getURI().toString(), ".jar!/");
        if (baseName.contains("classes!/")) {
            baseName = StringUtils.substringAfterLast(baseName, "classes!/");
        }
        baseName = baseName.replace(".properties", "");
        return baseName;
    }
}