package com.namphibian.anaximander;

import io.fabric8.kubernetes.client.AutoAdaptableKubernetesClient;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class KubernetesClientProducer {

    @Produces
    public KubernetesClient kubernetesClient() {
        // here you would create a custom client
        DefaultKubernetesClient defaultKubernetesClient = new DefaultKubernetesClient();

        return defaultKubernetesClient;
    }

}