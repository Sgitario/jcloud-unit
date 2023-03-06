package io.github.snowdrop.jester.resources.quarkus.kubernetes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.snowdrop.jester.api.Dependency;
import io.github.snowdrop.jester.core.ServiceContext;
import io.github.snowdrop.jester.resources.kubernetes.KubernetesManagedResource;
import io.github.snowdrop.jester.resources.quarkus.common.BootstrapQuarkusResource;
import io.github.snowdrop.jester.utils.DockerUtils;
import io.github.snowdrop.jester.utils.QuarkusUtils;

public class ContainerRegistryProdModeBootstrapQuarkusKubernetesManagedResource extends KubernetesManagedResource {

    private final String location;
    private final Class<?>[] classes;
    private final Dependency[] forcedDependencies;
    private final boolean forceBuild;
    private final String version;

    private BootstrapQuarkusResource resource;
    private String image;

    public ContainerRegistryProdModeBootstrapQuarkusKubernetesManagedResource(String location, Class<?>[] classes,
            Dependency[] forcedDependencies, boolean forceBuild, String version) {
        this.location = location;
        this.classes = classes;
        this.forcedDependencies = forcedDependencies;
        this.forceBuild = forceBuild;
        this.version = version;
    }

    @Override
    public String getDisplayName() {
        return resource.getDisplayName();
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    protected String getExpectedLog() {
        return resource.getExpectedLog();
    }

    @Override
    protected int[] getPorts() {
        List<Integer> ports = new ArrayList<>();
        ports.add(Optional.ofNullable(getProperty(QuarkusUtils.QUARKUS_HTTP_PORT_PROPERTY)).map(Integer::parseInt)
                .orElse(QuarkusUtils.HTTP_PORT_DEFAULT));

        Optional.ofNullable(getProperty(QuarkusUtils.QUARKUS_GRPC_SERVER_PORT)).map(Integer::parseInt)
                .ifPresent(grpcPort -> ports.add(grpcPort));

        return ports.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    protected void init(ServiceContext context) {
        super.init(context);

        resource = new BootstrapQuarkusResource(context, location, classes, forcedDependencies, forceBuild, version);
        image = createImageAndPush();
    }

    private String createImageAndPush() {
        String dockerFile = QuarkusUtils.getDockerfile(resource.getLaunchMode());
        return DockerUtils.createImageAndPush(context, dockerFile, resource.getRunner());
    }

}