package io.jcloud.api.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;

import io.jcloud.api.LocalSource;
import io.jcloud.core.ManagedResource;

public interface LocalSourceManagedResourceBinding {
    /**
     * @param context
     *
     * @return if the current managed resource applies for the current context.
     */
    boolean appliesFor(ExtensionContext context);

    /**
     * Init and return the managed resource for the current context.
     *
     * @param metadata
     *
     * @return
     */
    ManagedResource init(LocalSource metadata);
}