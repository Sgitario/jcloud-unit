package io.github.snowdrop.jester.api.extensions;

import io.github.snowdrop.jester.api.Operator;
import io.github.snowdrop.jester.api.Service;
import io.github.snowdrop.jester.core.JesterContext;
import io.github.snowdrop.jester.core.ManagedResource;

public interface OperatorManagedResourceBinding {
    /**
     * @param context
     *
     * @return if the current managed resource applies for the current context.
     */
    boolean appliesFor(JesterContext context);

    /**
     * Init and return the managed resource for the current context.
     */
    ManagedResource init(JesterContext context, Service service, Operator metadata);
}
