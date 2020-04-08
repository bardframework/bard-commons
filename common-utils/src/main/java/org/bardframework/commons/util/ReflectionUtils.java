package org.bardframework.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class ReflectionUtils extends org.springframework.util.ReflectionUtils {

    private static final String UNACCEPTABLE_NULL_CLAZZ = "null clazz not acceptable";
    private static final String UNACCEPTABLE_FIELD_PATH = "null or empty field path not acceptable";

    private ReflectionUtils() {
    }

    public static Field getDeclaredField(Class<?> clazz, String name)
            throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            if (Object.class != clazz.getSuperclass()) {
                return getDeclaredField(clazz.getSuperclass(), name);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return value of property path of root object
     */
    public static Object getPropertyValue(final Object root, String propertyPath)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (root == null) {
            throw new IllegalArgumentException("null object not acceptable");
        }
        if (propertyPath == null || propertyPath.isEmpty()) {
            throw new IllegalArgumentException("null or empty what not acceptable");
        }
        Object current = root;
        String[] properties = propertyPath.split("\\.");
        for (String property : properties) {
            if (current == null) {
                return current;
            }
            current = getGetter(current.getClass(), property).invoke(current);
        }
        return current;
    }

    public static Field getField(Class<?> clazz, String fieldPath) {
        if (clazz == null) {
            throw new IllegalArgumentException(UNACCEPTABLE_NULL_CLAZZ);
        }
        if (StringUtils.hasNotText(fieldPath)) {
            throw new IllegalArgumentException(UNACCEPTABLE_FIELD_PATH);
        }
        Class<?> currentClazz = clazz;
        String[] fieldPaths = fieldPath.split("\\.");
        Field field = null;
        for (String fieldName : fieldPaths) {
            field = ReflectionUtils.findField(currentClazz, fieldName);
            if (null == field) {
                return null;
            }
            currentClazz = field.getType();
        }
        return field;
    }

    public static Class<?> getContainerCLassByField(Class<?> clazz, String fieldPath) {
        if (clazz == null) {
            throw new IllegalArgumentException(UNACCEPTABLE_NULL_CLAZZ);
        }
        if (StringUtils.hasNotText(fieldPath)) {
            throw new IllegalArgumentException(UNACCEPTABLE_FIELD_PATH);
        }
        Class<?> currentClazz = clazz;
        String[] fieldPaths = fieldPath.split("\\.");
        for (int i = 0; i < fieldPaths.length - 1; i++) {
            currentClazz = findField(currentClazz, fieldPaths[i]).getType();
        }
        return currentClazz;
    }

    public static Method getGetterMethod(Class<?> clazz, String fieldPath)
            throws NoSuchMethodException {
        if (clazz == null) {
            throw new IllegalArgumentException(UNACCEPTABLE_NULL_CLAZZ);
        }
        if (StringUtils.hasNotText(fieldPath)) {
            throw new IllegalArgumentException(UNACCEPTABLE_FIELD_PATH);
        }
        Class<?> currentClazz = clazz;
        String[] fieldPaths = fieldPath.split("\\.");
        Method method = null;
        for (String fieldName : fieldPaths) {
            method = getGetter(currentClazz, fieldName);
            if (null == method) {
                return null;
            }
            currentClazz = method.getReturnType();
        }
        return method;
    }

    public static Class<?> getContainerClassByGetter(Class<?> clazz, String fieldPath)
            throws NoSuchMethodException {
        if (clazz == null) {
            throw new IllegalArgumentException(UNACCEPTABLE_NULL_CLAZZ);
        }
        if (StringUtils.hasNotText(fieldPath)) {
            throw new IllegalArgumentException(UNACCEPTABLE_FIELD_PATH);
        }
        Class<?> currentClazz = clazz;
        String[] fieldPaths = fieldPath.split("\\.");
        for (int i = 0; i < fieldPaths.length - 1; i++) {
            currentClazz = getGetter(currentClazz, fieldPaths[i]).getReturnType();
        }
        return currentClazz;
    }


    public static Object setValue(final Object root, final String path, final Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        if (root == null) {
            throw new IllegalArgumentException("null root not acceptable");
        }
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("null or empty what not acceptable");
        }
        if (null == value) {
            return root;
        }
        Object current = root;
        String[] callList = path.split("\\.");
        for (int i = 0; i < callList.length - 1; i++) {
            Method getter = getGetter(current.getClass(), callList[i]);
            Object result = getter.invoke(current);
            if (result == null) {
                result = getter.getReturnType().newInstance();
                getSetter(current.getClass(), callList[i], result.getClass()).invoke(current, result);
            }
            current = result;
        }
        try {
            getSetter(current.getClass(), callList[callList.length - 1], value.getClass()).invoke(current, value);
        } catch (NoSuchMethodException e) {
            Class<?> clazz;
            if (value.getClass().equals(Byte.class)) {
                clazz = byte.class;
            } else if (value.getClass().equals(Short.class)) {
                clazz = short.class;
            } else if (value.getClass().equals(Integer.class)) {
                clazz = int.class;
            } else if (value.getClass().equals(Long.class)) {
                clazz = long.class;
            } else {
                throw e;
            }

            getSetter(current.getClass(), callList[callList.length - 1], clazz).invoke(current, value);
        }
        return current;
    }

    public static Method getSetter(Class<?> aClass, final String property, Class<?>... parameterType)
            throws NoSuchMethodException {
        if (aClass == null) {
            throw new IllegalArgumentException("null _class not accepted");
        }
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("null or empty property not accepted");
        }
        return aClass.getMethod(getSetterName(property.trim()), parameterType);
    }

    public static Method getGetter(Class<?> aClass, final String property)
            throws NoSuchMethodException {
        if (aClass == null) {
            throw new IllegalArgumentException("null _class not accepted");
        }
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("null or empty property not accepted");
        }
        try {
            return aClass.getMethod(getGetterName(property.trim(), String.class));
        } catch (NoSuchMethodException | SecurityException ex) {
            return aClass.getMethod(getGetterName(property.trim(), boolean.class));
        }
    }

    public static Method getCurrentMethod(StackTraceElement stackTraceElement)
            throws ClassNotFoundException, NoSuchMethodException {
        return Class.forName(stackTraceElement.getClassName()).getMethod(stackTraceElement.getMethodName());
    }

    /**
     * @param property, Class type
     * @return getter of the given property, if the given property has boolean
     * type add is to its first else add get
     */
    public static String getGetterName(String property, Class<?> type) {
        property = property.trim();
        return (boolean.class.equals(type) || Boolean.class.equals(type) ? "is" : "get") + property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public static String getSetterName(String property) {
        return "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public static <T extends Annotation> Map<Field, T> getFieldAnnotationMap(Class<?> clazz, Class<T> annotationClazz, boolean includeSuperClasses) {
        Map<Field, T> map = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(annotationClazz)) {
                continue;
            }
            map.put(field, field.getAnnotation(annotationClazz));
        }
        if (includeSuperClasses && !Object.class.equals(clazz.getSuperclass())) {
            map.putAll(getFieldAnnotationMap(clazz.getSuperclass(), annotationClazz, true));
        }
        return map;
    }

    public static <T extends Annotation> List<Field> getFields(Class<?> clazz, Class<T> annotationClazz, boolean includeSuperClasses) {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(annotationClazz)) {
                continue;
            }
            fields.add(field);
        }
        if (includeSuperClasses && !Object.class.equals(clazz.getSuperclass())) {
            fields.addAll(getFields(clazz.getSuperclass(), annotationClazz, true));
        }
        return fields;
    }

    public static Set<String> fetchPropertyFields(Set<String> allFields, String fieldName) {
        AssertionUtils.notNull(fieldName, "null field name not acceptable");
        Set<String> fields = new HashSet<>();
        if (null == allFields) {
            return fields;
        }
        allFields.forEach(field -> {
            if (field.startsWith(fieldName + Constants.DOT_CHAR)) {
                fields.add(field.replaceFirst(fieldName + Constants.DOT_CHAR, Constants.EMPTY_STRING));
            }
        });
        if (!fields.isEmpty()) {
            fields.add(fieldName);
        }
        return fields;
    }

    public static boolean isContainField(Set<String> fields, String fieldName) {
        AssertionUtils.notNull(fieldName, "null field name not acceptable");
        if (null == fields) {
            return false;
        }
        for (String field : fields) {
            if (field.equals(fieldName) || field.startsWith(fieldName + Constants.DOT_CHAR)) {
                return true;
            }
        }
        return false;
    }

    public static String methodToFieldName(Method method) {
        String name = method.getName();
        if (name.startsWith("get")) {
            name = name.replaceFirst("get", Constants.EMPTY_STRING);
        } else if (name.startsWith("is")) {
            name = name.replaceFirst("is", Constants.EMPTY_STRING);
        }
        return lowerCaseFirstLetter(name);
    }

    public static String lowerCaseFirstLetter(String name) {
        StringBuilder builder = new StringBuilder(name);
        builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
        return builder.toString();
    }
}
