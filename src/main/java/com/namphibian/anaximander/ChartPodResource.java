package com.namphibian.anaximander;

import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/podMap")
public class ChartPodResource {
    @Inject
    KubernetesClient client;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PodList chartPods() {

        return client.pods().list();

    }
}
