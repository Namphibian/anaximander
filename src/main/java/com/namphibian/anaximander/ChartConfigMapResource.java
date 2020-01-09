package com.namphibian.anaximander;

import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/chartConfigMaps")
public class ChartConfigMapResource {
    @Inject
    KubernetesClient client;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ConfigMapList chartPods() {

        return client.configMaps().list();

    }
}
