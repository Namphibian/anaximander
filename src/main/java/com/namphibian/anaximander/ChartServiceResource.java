package com.namphibian.anaximander;

import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/serviceMap")
public class ChartServiceResource {
    @Inject
    KubernetesClient client;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ServiceList chartServices() {

        return client.services().list();

    }
}
