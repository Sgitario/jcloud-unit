package io.jester.utils;

import static io.jester.utils.ReflectionUtils.invokeMethod;
import static io.jester.utils.ReflectionUtils.loadClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

import io.jester.core.DependencyContext;

public final class InjectUtils {

    private static final Optional<Class> JAVAX_INJECT = loadClass("javax.inject.Inject");
    private static final Optional<Class> JAKARTA_INJECT = loadClass("jakarta.inject.Inject");
    private static final Optional<Class> JAVAX_NAMED = loadClass("javax.inject.Named");
    private static final Optional<Class> JAKARTA_NAMED = loadClass("jakarta.inject.Named");

    private InjectUtils() {

    }

    public static boolean isAnnotatedWithInject(Field field) {
        return (JAVAX_INJECT.isPresent() && field.isAnnotationPresent(JAVAX_INJECT.get()))
                || (JAKARTA_INJECT.isPresent() && field.isAnnotationPresent(JAKARTA_INJECT.get()));
    }

    public static String getNamedValueFromDependencyContext(DependencyContext dependencyContext) {
        Annotation named = null;
        if (JAVAX_NAMED.isPresent()) {
            Optional<Annotation> found = dependencyContext.findAnnotation(JAVAX_NAMED.get());
            if (found.isPresent()) {
                named = found.get();
            }
        }

        if (JAKARTA_NAMED.isPresent()) {
            Optional<Annotation> found = dependencyContext.findAnnotation(JAKARTA_NAMED.get());
            if (found.isPresent()) {
                named = found.get();
            }
        }

        return named != null ? (String) invokeMethod(named, "value") : null;
    }
}
