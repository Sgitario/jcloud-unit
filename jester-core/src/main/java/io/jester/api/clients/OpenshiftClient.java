package io.jester.api.clients;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.openshift.api.model.Route;
import io.fabric8.openshift.client.OpenShiftClient;
import io.jester.api.Service;
import io.jester.utils.Command;

public final class OpenshiftClient extends BaseKubernetesClient<OpenShiftClient> {

    private static final String OC = "oc";

    @Override
    public String command() {
        return OC;
    }

    @Override
    public OpenShiftClient initializeClient(Config config) {
        return new KubernetesClientBuilder().withConfig(config).build().adapt(OpenShiftClient.class);
    }

    @Override
    public String host(Service service) {
        Route route = underlyingClient().routes().withName(service.getName()).get();
        if (route == null || route.getSpec() == null) {
            return super.host(service);
        }

        return route.getSpec().getHost();
    }

    @Override
    public int port(Service service, int port) {
        if (PORT_FORWARD_HOST.equalsIgnoreCase(host(service))) {
            return super.port(service, port);
        }

        Route route = underlyingClient().routes().withName(service.getName()).get();
        if (route == null || route.getSpec() == null || route.getSpec().getPort() == null) {
            return super.port(service, port);
        }

        return route.getSpec().getPort().getTargetPort().getIntVal();
    }

    public void exposeRoute(Service service, int[] ports) {
        List portList = IntStream.of(ports).mapToObj(Integer::toString).collect(Collectors.toList());
        portList.forEach(port -> {
            try {
                new Command(OC, "expose", "svc", service.getName(), "--port=" + port, "--name=" + service.getName(),
                        "-n", namespace()).runAndWait();
            } catch (Exception e) {
                throw new RuntimeException("Route failed to be exposed.", e);
            }
        });
    }
}