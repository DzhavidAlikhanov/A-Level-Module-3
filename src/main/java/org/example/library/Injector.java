package org.example.library;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class Injector {
    private static final Map<String, Injector> injectors = new HashMap<>();
    private final Map<Class<?>, Object> instanceOfClasses = new HashMap<>();
    private final List<Class<?>> classes = new ArrayList<>();

    private Injector(String mainPackageName) {
        try {
            classes.addAll(getClasses(mainPackageName));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't get information about all classes", e);
        }
    }

    public static Injector getInstance(String mainPackageName) {
        return injectors.computeIfAbsent(mainPackageName, Injector::new);
    }

    public Object getInstance(Class<?> certainInterface) {
        Object newInstanceOfClass = null;
        Class<?> clazz = findClassExtendingInterface(certainInterface);
        Object instanceOfCurrentClass = createInstance(clazz);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (isFieldInitialized(field, instanceOfCurrentClass)) {
                continue;
            }
            if (field.getDeclaredAnnotation(Inject.class) != null) {
                Object classToInject = getInstance(field.getType());
                newInstanceOfClass = getNewInstance(clazz);
                setValueToField(field, newInstanceOfClass, classToInject);
            } else {
                throw new RuntimeException("Class " + field.getName() + " in class "
                        + clazz.getName() + " hasn't annotation Inject");
            }
        }
        if (newInstanceOfClass == null) {
            return getNewInstance(clazz);
        }
        return newInstanceOfClass;
    }

    private Class<?> findClassExtendingInterface(Class<?> certainInterface) {
        return classes.stream()
                .filter(clazz -> Arrays.asList(clazz.getInterfaces()).contains(certainInterface))
                .filter(clazz -> clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Dao.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find class which implements "
                        + certainInterface.getName() + " interface and has valid annotation (Dao or Service)"));
    }

    private Object getNewInstance(Class<?> certainClass) {
        return instanceOfClasses.computeIfAbsent(certainClass, this::createInstance);
    }

    private boolean isFieldInitialized(Field field, Object instance) {
        field.setAccessible(true);
        try {
            return field.get(instance) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't get access to field");
        }
    }

    private Object createInstance(Class<?> clazz) {
        try {
            Constructor<?> classConstructor = clazz.getConstructor();
            return classConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't create object of the class", e);
        }
    }

    private void setValueToField(Field field, Object instanceOfClass, Object classToInject) {
        try {
            field.setAccessible(true);
            field.set(instanceOfClass, classToInject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't set value to field ", e);
        }
    }

    private static List<Class<?>> getClasses(String packageName)
            throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new RuntimeException("Class loader is null");
        }
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().contains(".")) {
                        throw new RuntimeException("File name shouldn't consist point.");
                    }
                    classes.addAll(findClasses(file, packageName + "."
                            + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }
}
