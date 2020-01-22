package com.namphibian.anaximander;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/secrets")
public class SecretsConfigurationResource {


    @Inject
    KubernetesClient client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Secret> chartServices() {

        return client.secrets().list().getItems();

    }

}
