package io.jcloud.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import io.jcloud.core.JCloudExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(JCloudExtension.class)
@Inherited
public @interface Scenario {
    /**
     * Set the scenario target environment where to run the tests. Fallback property `ts.scenario.target`.
     */
    String target() default "local";
}
