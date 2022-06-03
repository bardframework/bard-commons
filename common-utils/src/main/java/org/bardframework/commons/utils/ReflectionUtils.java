package org.bardframework.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Vahid Zafari on 8/12/2016.
 */
public final class ReflectionUtils {
    /**
     * Pre-built MethodFilter that matches all non-bridge non-synthetic methods
     * which are not declared on {@code java.lang.Object}.
     */
    public static final MethodFilter USER_DECLARED_METHODS = (method -> !method.isBridge() && !method.isSynthetic());
    /**
     * Pre-built FieldFilter that matches all non-static, non-final fields.
     */
    public static final FieldFilter COPYABLE_FIELDS = (field -> !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())));
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);
    private static final String UNACCEPTABLE_NULL_CLAZZ = "null clazz not acceptable";
    private static final String UNACCEPTABLE_FIELD_PATH = "null or empty field path not acceptable";
    /**
     * Naming prefix for CGLIB-renamed methods.
     */
    private static final String CGLIB_RENAMED_METHOD_PREFIX = "CGLIB$";
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    private ReflectionUtils() {
        /*
            prevent instantiation
         */
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            LOGGER.error("can't instantiate class using empty constructor {}", clazz, e);
            throw new IllegalArgumentException("can't instantiate class using empty constructor" + clazz, e);
        }
    }

    public static <T> Class<T> getGenericArgType(Class<?> clazz, int genericArgIndex) {
        Class<?> targetClazz = clazz;
        /*
            find first generic parent
         */
        ParameterizedType parameterizedType = null;
        while (null == parameterizedType && null != targetClazz.getGenericSuperclass()) {
            if (targetClazz.getGenericSuperclass() instanceof ParameterizedType) {
                if (((ParameterizedType) targetClazz.getGenericSuperclass()).getActualTypeArguments().length >= genericArgIndex + 1) {
                    parameterizedType = (ParameterizedType) targetClazz.getGenericSuperclass();
                    continue;
                }
            }
            targetClazz = targetClazz.getSuperclass();
        }

        if (null == parameterizedType) {
            throw new IllegalArgumentException("can't determine generic arg class at index: " + genericArgIndex + " for class: " + clazz);
        }

        try {
            Type type = ((ParameterizedType) targetClazz.getGenericSuperclass()).getActualTypeArguments()[genericArgIndex];
            if (type instanceof Class<?>) {
                return (Class<T>) type;
            }
            throw new IllegalArgumentException("can't determine class from generic type!");
        } catch (Exception e) {
            LOGGER.debug("can't determine class from generic type, at index [{}]", genericArgIndex, e);
            throw new IllegalArgumentException("can't determine class from generic type!", e);
        }
    }

    /**
     * Finds the first field on the given class matching the given {@link FieldFilter}.
     *
     * @param type   must not be {@literal null}.
     * @param filter must not be {@literal null}.
     * @return the field matching the filter or {@literal null} in case no field could be found.
     */
    public static Field findField(Class<?> type, FieldFilter filter) {
        return findField(type, new DescribedFieldFilter() {

            public boolean matches(Field field) {
                return filter.matches(field);
            }

            public String getDescription() {
                return String.format("FieldFilter %s", filter.toString());
            }
        }, false);
    }

    /**
     * Finds the field matching the given {@link DescribedFieldFilter}. Will make sure there's only one field matching the
     * filter.
     *
     * @param type   must not be {@literal null}.
     * @param filter must not be {@literal null}.
     * @return the field matching the given {@link DescribedFieldFilter} or {@literal null} if none found.
     * @throws IllegalStateException in case more than one matching field is found
     */
    public static Field findField(Class<?> type, DescribedFieldFilter filter) {
        return findField(type, filter, true);
    }

    /**
     * Finds the field matching the given {@link DescribedFieldFilter}. Will make sure there's only one field matching the
     * filter in case {@code enforceUniqueness} is {@literal true}.
     *
     * @param type              must not be {@literal null}.
     * @param filter            must not be {@literal null}.
     * @param enforceUniqueness whether to enforce uniqueness of the field
     * @return the field matching the given {@link DescribedFieldFilter} or {@literal null} if none found.
     * @throws IllegalStateException if enforceUniqueness is true and more than one matching field is found
     */
    public static Field findField(Class<?> type, DescribedFieldFilter filter, boolean enforceUniqueness) {

        AssertionUtils.notNull(type, "Type must not be null!");
        AssertionUtils.notNull(filter, "Filter must not be null!");

        Class<?> targetClass = type;
        Field foundField = null;

        while (targetClass != Object.class) {

            for (Field field : targetClass.getDeclaredFields()) {

                if (!filter.matches(field)) {
                    continue;
                }

                if (!enforceUniqueness) {
                    return field;
                }

                if (foundField != null) {
                    throw new IllegalStateException(filter.getDescription());
                }

                foundField = field;
            }

            targetClass = targetClass.getSuperclass();
        }

        return foundField;
    }

    /**
     * Finds the field of the given name on the given type.
     *
     * @param type must not be {@literal null}.
     * @param name must not be {@literal null} or empty.
     * @throws IllegalArgumentException in case the field can't be found.
     */
    public static Field findRequiredField(Class<?> type, String name) {

        Field result = ReflectionUtils.findField(type, name);

        if (result == null) {
            throw new IllegalArgumentException(String.format("Unable to find field %s on %s!", name, type));
        }

        return result;
    }

    /**
     * Returns the method with the given name of the given class and parameter types.
     *
     * @param type           must not be {@literal null}.
     * @param name           must not be {@literal null}.
     * @param parameterTypes must not be {@literal null}.
     * @throws IllegalArgumentException in case the method cannot be resolved.
     */
    public static Method findRequiredMethod(Class<?> type, String name, Class<?>... parameterTypes) {

        Method result = ReflectionUtils.findMethod(type, name, parameterTypes);

        if (result == null) {

            String parameterTypeNames = Arrays.stream(parameterTypes) //
                    .map(Object::toString) //
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(
                    String.format("Unable to find method %s(%s)on %s!", name, parameterTypeNames, type));
        }

        return result;
    }

    /**
     * Returns a {@link Stream} of the return and parameters types of the given {@link Method}.
     *
     * @param method must not be {@literal null}.
     */
    public static Stream<Class<?>> returnTypeAndParameters(Method method) {

        AssertionUtils.notNull(method, "Method must not be null!");

        Stream<Class<?>> returnType = Stream.of(method.getReturnType());
        Stream<Class<?>> parameterTypes = Arrays.stream(method.getParameterTypes());

        return Stream.concat(returnType, parameterTypes);
    }

    /**
     * Get default value for a primitive type.
     *
     * @param type must not be {@literal null}.
     * @return boxed primitive default value.
     */
    public static Object getPrimitiveDefault(Class<?> type) {

        if (type == Byte.TYPE || type == Byte.class) {
            return (byte) 0;
        }

        if (type == Short.TYPE || type == Short.class) {
            return (short) 0;
        }

        if (type == Integer.TYPE || type == Integer.class) {
            return 0;
        }

        if (type == Long.TYPE || type == Long.class) {
            return 0L;
        }

        if (type == Float.TYPE || type == Float.class) {
            return 0F;
        }

        if (type == Double.TYPE || type == Double.class) {
            return 0D;
        }

        if (type == Character.TYPE || type == Character.class) {
            return '\u0000';
        }

        if (type == Boolean.TYPE) {
            return Boolean.FALSE;
        }

        throw new IllegalArgumentException(String.format("Primitive type %s not supported!", type));
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
        if (StringUtils.isBlank(fieldPath)) {
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
        if (StringUtils.isBlank(fieldPath)) {
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
        if (StringUtils.isBlank(fieldPath)) {
            throw new IllegalArgumentException(UNACCEPTABLE_FIELD_PATH);
        }
        Class<?> currentClazz = clazz;
        String[] fieldPaths = fieldPath.split("\\.");
        Method method = null;
        for (String fieldName : fieldPaths) {
            method = getGetter(currentClazz, fieldName);
            currentClazz = method.getReturnType();
        }
        return method;
    }

    public static Class<?> getContainerClassByGetter(Class<?> clazz, String fieldPath)
            throws NoSuchMethodException {
        if (clazz == null) {
            throw new IllegalArgumentException(UNACCEPTABLE_NULL_CLAZZ);
        }
        if (StringUtils.isBlank(fieldPath)) {
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
                result = getter.getReturnType().getConstructor().newInstance();
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

    /**
     * @throws NoSuchMethodException getCurrentMethod(Thread.currentThread().getStackTrace()[1])
     */
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
            if (field.startsWith(fieldName + ".")) {
                fields.add(field.replaceFirst(fieldName + ".", StringUtils.EMPTY));
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
            if (field.equals(fieldName) || field.startsWith(fieldName + ".")) {
                return true;
            }
        }
        return false;
    }

    public static String methodToFieldName(Method method) {
        String name = method.getName();
        if (name.startsWith("get")) {
            name = name.replaceFirst("get", StringUtils.EMPTY);
        } else if (name.startsWith("is")) {
            name = name.replaceFirst("is", StringUtils.EMPTY);
        }
        return lowerCaseFirstLetter(name);
    }

    public static String lowerCaseFirstLetter(String name) {
        StringBuilder builder = new StringBuilder(name);
        builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
        return builder.toString();
    }

    /**
     * Handle the given reflection exception.
     * <p>Should only be called if no checked exception is expected to be thrown
     * by a target method, or if an error occurs while accessing a method or field.
     * <p>Throws the underlying RuntimeException or Error in case of an
     * InvocationTargetException with such a root cause. Throws an
     * IllegalStateException with an appropriate message or
     * UndeclaredThrowableException otherwise.
     *
     * @param ex the reflection exception to handle
     */
    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException) ex);
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Handle the given invocation target exception. Should only be called if no
     * checked exception is expected to be thrown by the target method.
     * <p>Throws the underlying RuntimeException or Error in case of such a root
     * cause. Throws an UndeclaredThrowableException otherwise.
     *
     * @param ex the invocation target exception to handle
     */
    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    /**
     * Rethrow the given {@link Throwable exception}, which is presumably the
     * <em>target exception</em> of an {@link InvocationTargetException}.
     * Should only be called if no checked exception is expected to be thrown
     * by the target method.
     * <p>Rethrows the underlying exception cast to a {@link RuntimeException} or
     * {@link Error} if appropriate; otherwise, throws an
     * {@link UndeclaredThrowableException}.
     *
     * @param ex the exception to rethrow
     * @throws RuntimeException the rethrown exception
     */
    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Rethrow the given {@link Throwable exception}, which is presumably the
     * <em>target exception</em> of an {@link InvocationTargetException}.
     * Should only be called if no checked exception is expected to be thrown
     * by the target method.
     * <p>Rethrows the underlying exception cast to an {@link Exception} or
     * {@link Error} if appropriate; otherwise, throws an
     * {@link UndeclaredThrowableException}.
     *
     * @param ex the exception to rethrow
     * @throws Exception the rethrown exception (in case of a checked exception)
     */
    public static void rethrowException(Throwable ex) throws Exception {
        if (ex instanceof Exception) {
            throw (Exception) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Obtain an accessible constructor for the given class and parameters.
     *
     * @param clazz          the clazz to check
     * @param parameterTypes the parameter types of the desired constructor
     * @return the constructor reference
     * @throws NoSuchMethodException if no such constructor exists
     */
    public static <T> Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes)
            throws NoSuchMethodException {

        Constructor<T> ctor = clazz.getDeclaredConstructor(parameterTypes);
        makeAccessible(ctor);
        return ctor;
    }


    // Constructor handling

    /**
     * Make the given constructor accessible, explicitly setting it accessible
     * if necessary. The {@code setAccessible(true)} method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     *
     * @param ctor the constructor to make accessible
     */
    @SuppressWarnings("deprecation")  // on JDK 9
    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) ||
                !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied name
     * and no parameters. Searches all superclasses up to {@code Object}.
     * <p>Returns {@code null} if no {@link Method} can be found.
     *
     * @param clazz the class to introspect
     * @param name  the name of the method
     * @return the Method object, or {@code null} if none found
     */
    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, EMPTY_CLASS_ARRAY);
    }


    // Method handling

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied name
     * and parameter types. Searches all superclasses up to {@code Object}.
     * <p>Returns {@code null} if no {@link Method} can be found.
     *
     * @param clazz      the class to introspect
     * @param name       the name of the method
     * @param paramTypes the parameter types of the method
     *                   (may be {@code null} to indicate any signature)
     * @return the Method object, or {@code null} if none found
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        AssertionUtils.notNull(clazz, "Class must not be null");
        AssertionUtils.notNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() :
                    getDeclaredMethods(searchType, false));
            for (Method method : methods) {
                if (name.equals(method.getName()) && (paramTypes == null || hasSameParams(method, paramTypes))) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    private static boolean hasSameParams(Method method, Class<?>[] paramTypes) {
        return (paramTypes.length == method.getParameterCount() &&
                Arrays.equals(paramTypes, method.getParameterTypes()));
    }

    /**
     * Invoke the specified {@link Method} against the supplied target object with no arguments.
     * The target object can be {@code null} when invoking a static {@link Method}.
     * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
     *
     * @param method the method to invoke
     * @param target the target object to invoke the method on
     * @return the invocation result, if any
     */
    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, EMPTY_OBJECT_ARRAY);
    }

    /**
     * Invoke the specified {@link Method} against the supplied target object with the
     * supplied arguments. The target object can be {@code null} when invoking a
     * static {@link Method}.
     * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
     *
     * @param method the method to invoke
     * @param target the target object to invoke the method on
     * @param args   the invocation arguments (may be {@code null})
     * @return the invocation result, if any
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    /**
     * Determine whether the given method explicitly declares the given
     * exception or one of its superclasses, which means that an exception
     * of that type can be propagated as-is within a reflective invocation.
     *
     * @param method        the declaring method
     * @param exceptionType the exception to throw
     * @return {@code true} if the exception can be thrown as-is;
     * {@code false} if it needs to be wrapped
     */
    public static boolean declaresException(Method method, Class<?> exceptionType) {
        AssertionUtils.notNull(method, "Method must not be null");
        Class<?>[] declaredExceptions = method.getExceptionTypes();
        for (Class<?> declaredException : declaredExceptions) {
            if (declaredException.isAssignableFrom(exceptionType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Perform the given callback operation on all matching methods of the given
     * class, as locally declared or equivalent thereof (such as default methods
     * on Java 8 based interfaces that the given class implements).
     *
     * @param clazz the class to introspect
     * @param mc    the callback to invoke for each method
     * @throws IllegalStateException if introspection fails
     */
    public static void doWithLocalMethods(Class<?> clazz, MethodCallback mc) {
        Method[] methods = getDeclaredMethods(clazz, false);
        for (Method method : methods) {
            mc.doWith(method);
        }
    }

    /**
     * Perform the given callback operation on all matching methods of the given
     * class and superclasses.
     * <p>The same named method occurring on subclass and superclass will appear
     * twice, unless excluded by a {@link MethodFilter}.
     *
     * @param clazz the class to introspect
     * @param mc    the callback to invoke for each method
     * @throws IllegalStateException if introspection fails
     */
    public static void doWithMethods(Class<?> clazz, MethodCallback mc) {
        doWithMethods(clazz, mc, null);
    }

    /**
     * Perform the given callback operation on all matching methods of the given
     * class and superclasses (or given interface and super-interfaces).
     * <p>The same named method occurring on subclass and superclass will appear
     * twice, unless excluded by the specified {@link MethodFilter}.
     *
     * @param clazz the class to introspect
     * @param mc    the callback to invoke for each method
     * @param mf    the filter that determines the methods to apply the callback to
     * @throws IllegalStateException if introspection fails
     */
    public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf) {
        // Keep backing up the inheritance hierarchy.
        Method[] methods = getDeclaredMethods(clazz, false);
        for (Method method : methods) {
            if (mf != null && !mf.matches(method)) {
                continue;
            }
            mc.doWith(method);
        }
        if (clazz.getSuperclass() != null && (mf != USER_DECLARED_METHODS || clazz.getSuperclass() != Object.class)) {
            doWithMethods(clazz.getSuperclass(), mc, mf);
        } else if (clazz.isInterface()) {
            for (Class<?> superIfc : clazz.getInterfaces()) {
                doWithMethods(superIfc, mc, mf);
            }
        }
    }

    /**
     * Get all declared methods on the leaf class and all superclasses.
     * Leaf class methods are included first.
     *
     * @param leafClass the class to introspect
     * @throws IllegalStateException if introspection fails
     */
    public static Method[] getAllDeclaredMethods(Class<?> leafClass) {
        final List<Method> methods = new ArrayList<>(32);
        doWithMethods(leafClass, methods::add);
        return methods.toArray(EMPTY_METHOD_ARRAY);
    }

    /**
     * Get the unique set of declared methods on the leaf class and all superclasses.
     * Leaf class methods are included first and while traversing the superclass hierarchy
     * any methods found with signatures matching a method already included are filtered out.
     *
     * @param leafClass the class to introspect
     * @throws IllegalStateException if introspection fails
     */
    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) {
        return getUniqueDeclaredMethods(leafClass, null);
    }

    /**
     * Get the unique set of declared methods on the leaf class and all superclasses.
     * Leaf class methods are included first and while traversing the superclass hierarchy
     * any methods found with signatures matching a method already included are filtered out.
     *
     * @param leafClass the class to introspect
     * @param mf        the filter that determines the methods to take into account
     * @throws IllegalStateException if introspection fails
     */
    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass, MethodFilter mf) {
        final List<Method> methods = new ArrayList<>(32);
        doWithMethods(leafClass, method -> {
            boolean knownSignature = false;
            Method methodBeingOverriddenWithCovariantReturnType = null;
            for (Method existingMethod : methods) {
                if (method.getName().equals(existingMethod.getName()) &&
                        method.getParameterCount() == existingMethod.getParameterCount() &&
                        Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
                    // Is this a covariant return type situation?
                    if (existingMethod.getReturnType() != method.getReturnType() &&
                            existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                        methodBeingOverriddenWithCovariantReturnType = existingMethod;
                    } else {
                        knownSignature = true;
                    }
                    break;
                }
            }
            if (methodBeingOverriddenWithCovariantReturnType != null) {
                methods.remove(methodBeingOverriddenWithCovariantReturnType);
            }
            if (!knownSignature && !isCglibRenamedMethod(method)) {
                methods.add(method);
            }
        }, mf);
        return methods.toArray(EMPTY_METHOD_ARRAY);
    }

    /**
     * Variant of {@link Class#getDeclaredMethods()} that uses a local cache in
     * order to avoid the JVM's SecurityManager check and new Method instances.
     * In addition, it also includes Java 8 default methods from locally
     * implemented interfaces, since those are effectively to be treated just
     * like declared methods.
     *
     * @param clazz the class to introspect
     * @return the cached array of methods
     * @throws IllegalStateException if introspection fails
     */
    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return getDeclaredMethods(clazz, true);
    }

    private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
        AssertionUtils.notNull(clazz, "Class must not be null");
        Method[] result;
        try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
            if (defaultMethods != null) {
                result = new Method[declaredMethods.length + defaultMethods.size()];
                System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                int index = declaredMethods.length;
                for (Method defaultMethod : defaultMethods) {
                    result[index] = defaultMethod;
                    index++;
                }
            } else {
                result = declaredMethods;
            }
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                    "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }
        return (result.length == 0 || !defensive) ? result : result.clone();
    }

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

    /**
     * Determine whether the given method is an "equals" method.
     */
    public static boolean isEqualsMethod(Method method) {
        if (method == null || !method.getName().equals("equals")) {
            return false;
        }
        if (method.getParameterCount() != 1) {
            return false;
        }
        return method.getParameterTypes()[0] == Object.class;
    }

    /**
     * Determine whether the given method is a "hashCode" method.
     */
    public static boolean isHashCodeMethod(Method method) {
        return (method != null && method.getName().equals("hashCode") && method.getParameterCount() == 0);
    }

    /**
     * Determine whether the given method is a "toString" method.
     */
    public static boolean isToStringMethod(Method method) {
        return (method != null && method.getName().equals("toString") && method.getParameterCount() == 0);
    }

    /**
     * Determine whether the given method is originally declared by {@link Object}.
     */
    public static boolean isObjectMethod(Method method) {
        return (method != null && (method.getDeclaringClass() == Object.class ||
                isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method)));
    }

    /**
     * Determine whether the given method is a CGLIB 'renamed' method,
     * following the pattern "CGLIB$methodName$0".
     *
     * @param renamedMethod the method to check
     */
    public static boolean isCglibRenamedMethod(Method renamedMethod) {
        String name = renamedMethod.getName();
        if (name.startsWith(CGLIB_RENAMED_METHOD_PREFIX)) {
            int i = name.length() - 1;
            while (i >= 0 && Character.isDigit(name.charAt(i))) {
                i--;
            }
            return (i > CGLIB_RENAMED_METHOD_PREFIX.length() && (i < name.length() - 1) && name.charAt(i) == '$');
        }
        return false;
    }

    /**
     * Make the given method accessible, explicitly setting it accessible if
     * necessary. The {@code setAccessible(true)} method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     *
     * @param method the method to make accessible
     */
    @SuppressWarnings("deprecation")  // on JDK 9
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied {@code name}. Searches all superclasses up to {@link Object}.
     *
     * @param clazz the class to introspect
     * @param name  the name of the field
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }


    // Field handling

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied {@code name} and/or {@link Class type}. Searches all superclasses
     * up to {@link Object}.
     *
     * @param clazz the class to introspect
     * @param name  the name of the field (may be {@code null} if type is specified)
     * @param type  the type of the field (may be {@code null} if name is specified)
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        AssertionUtils.notNull(clazz, "Class must not be null");
        AssertionUtils.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fields = getDeclaredFields(searchType);
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) &&
                        (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Set the field represented by the supplied {@linkplain Field field object} on
     * the specified {@linkplain Object target object} to the specified {@code value}.
     * <p>In accordance with {@link Field#set(Object, Object)} semantics, the new value
     * is automatically unwrapped if the underlying field has a primitive type.
     * <p>This method does not support setting {@code static final} fields.
     * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
     *
     * @param field  the field to set
     * @param target the target object on which to set the field
     * @param value  the value to set (may be {@code null})
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
        }
    }

    /**
     * Get the field represented by the supplied {@link Field field object} on the
     * specified {@link Object target object}. In accordance with {@link Field#get(Object)}
     * semantics, the returned value is automatically wrapped if the underlying field
     * has a primitive type.
     * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
     *
     * @param field  the field to get
     * @param target the target object from which to get the field
     * @return the field's current value
     */
    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    /**
     * Invoke the given callback on all locally declared fields in the given class.
     *
     * @param clazz the target class to analyze
     * @param fc    the callback to invoke for each field
     * @throws IllegalStateException if introspection fails
     */
    public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
        for (Field field : getDeclaredFields(clazz)) {
            try {
                fc.doWith(field);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
            }
        }
    }

    /**
     * Invoke the given callback on all fields in the target class, going up the
     * class hierarchy to get all declared fields.
     *
     * @param clazz the target class to analyze
     * @param fc    the callback to invoke for each field
     * @throws IllegalStateException if introspection fails
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc) {
        doWithFields(clazz, fc, null);
    }

    /**
     * Invoke the given callback on all fields in the target class, going up the
     * class hierarchy to get all declared fields.
     *
     * @param clazz the target class to analyze
     * @param fc    the callback to invoke for each field
     * @param ff    the filter that determines the fields to apply the callback to
     * @throws IllegalStateException if introspection fails
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) {
        // Keep backing up the inheritance hierarchy.
        Class<?> targetClass = clazz;
        do {
            Field[] fields = getDeclaredFields(targetClass);
            for (Field field : fields) {
                if (ff != null && !ff.matches(field)) {
                    continue;
                }
                try {
                    fc.doWith(field);
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);
    }

    /**
     * This variant retrieves {@link Class#getDeclaredFields()} from a local cache
     * in order to avoid the JVM's SecurityManager check and defensive array copying.
     *
     * @param clazz the class to introspect
     * @return the cached array of fields
     * @throws IllegalStateException if introspection fails
     */
    private static Field[] getDeclaredFields(Class<?> clazz) {
        AssertionUtils.notNull(clazz, "Class must not be null");
        Field[] result;
        try {
            result = clazz.getDeclaredFields();
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }
        return result;
    }

    /**
     * Given the source object and the destination, which must be the same class
     * or a subclass, copy all fields, including inherited fields. Designed to
     * work on objects with public no-arg constructors.
     *
     * @throws IllegalStateException if introspection fails
     */
    public static void shallowCopyFieldState(final Object src, final Object dest) {
        AssertionUtils.notNull(src, "Source for field copy cannot be null");
        AssertionUtils.notNull(dest, "Destination for field copy cannot be null");
        if (!src.getClass().isAssignableFrom(dest.getClass())) {
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() +
                    "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        }
        doWithFields(src.getClass(), field -> {
            makeAccessible(field);
            Object srcValue = field.get(src);
            field.set(dest, srcValue);
        }, COPYABLE_FIELDS);
    }

    /**
     * Determine whether the given field is a "public static final" constant.
     *
     * @param field the field to check
     */
    public static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
    }

    /**
     * Make the given field accessible, explicitly setting it accessible if
     * necessary. The {@code setAccessible(true)} method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     *
     * @param field the field to make accessible
     */
    @SuppressWarnings("deprecation")  // on JDK 9
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static <T> Set<Class<? extends T>> getSubTypeOf(String packagePrefix, Class<T> clazz) {
        return new Reflections(packagePrefix, Scanners.SubTypes).getSubTypesOf(clazz);
    }

    public static List<Class<?>> getSupersOf(Class<?> clazz, boolean includeInterface, boolean includeAbstractClasses, boolean includeObjectClass) {
        List<Class<?>> supers = new ArrayList<>();
        if (null == clazz || clazz.equals(Object.class)) {
            return supers;
        }
        if (includeObjectClass) {
            supers.add(Object.class);
        }
        Class<?> parent = clazz.getSuperclass();
        if (null != parent && !Object.class.equals(parent)) {
            if (Modifier.isAbstract(parent.getModifiers())) {
                if (includeAbstractClasses) {
                    supers.add(parent);
                }
            } else {
                supers.add(parent);
            }
            supers.addAll(ReflectionUtils.getSupersOf(parent, includeInterface, includeAbstractClasses, false));
        }
        if (includeInterface) {
            for (Class<?> anInterface : clazz.getInterfaces()) {
                supers.add(anInterface);
                supers.addAll(ReflectionUtils.getSupersOf(anInterface, includeInterface, includeAbstractClasses, false));
            }
        }
        return supers;
    }

    /**
     * A {@link FieldFilter} that has a description.
     *
     * @author Oliver Gierke
     */
    public interface DescribedFieldFilter extends FieldFilter {

        /**
         * Returns the description of the field filter. Used in exceptions being thrown in case uniqueness shall be enforced
         * on the field filter.
         */
        String getDescription();
    }


    /**
     * Action to take on each method.
     */
    @FunctionalInterface
    public interface MethodCallback {

        /**
         * Perform an operation using the given method.
         *
         * @param method the method to operate on
         */
        void doWith(Method method) throws IllegalArgumentException;
    }


    /**
     * Callback optionally used to filter methods to be operated on by a method callback.
     */
    @FunctionalInterface
    public interface MethodFilter {

        /**
         * Determine whether the given method matches.
         *
         * @param method the method to check
         */
        boolean matches(Method method);
    }


    /**
     * Callback interface invoked on each field in the hierarchy.
     */
    @FunctionalInterface
    public interface FieldCallback {

        /**
         * Perform an operation using the given field.
         *
         * @param field the field to operate on
         */
        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    /**
     * Callback optionally used to filter fields to be operated on by a field callback.
     */
    @FunctionalInterface
    public interface FieldFilter {

        /**
         * Determine whether the given field matches.
         *
         * @param field the field to check
         */
        boolean matches(Field field);
    }
}
