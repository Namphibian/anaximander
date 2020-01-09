package com.namphibian.anaximander;

import com.namphibian.anaximander.model.NodePodMap;
import com.namphibian.anaximander.model.PodMap;
import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.NodeList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Path("/chartnodes")
public class ChartNodeResource {
    private static final Logger logger = LoggerFactory.getLogger(ChartNodeResource.class);
    @Inject
    KubernetesClient client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Node> chartNodes() {

        return client.nodes().list().getItems();
    }

    @Path("/pods")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NodePodMap> chartNodeFull() {

        logger.info("Getting a list of all the nodes.");
        NodeList nodeList = client.nodes().list();
        List<Pod> pods = client.pods().list().getItems();
        List<NodePodMap> nodes = nodeList.getItems().stream().map(node -> mapNodeAndPods(node, pods)).collect(Collectors.toList());
        return nodes;
    }

    @GET
    @Path("/{nodeName}/pods")
    @Produces(MediaType.APPLICATION_JSON)
    public NodePodMap getNode(@PathParam("nodeName") String id) {
        // search my database and get a string representation and return it
        logger.info("Getting a node with the name of: {}.", id);
        Node theNode =client.nodes().withName(id).get();
        List<Pod> pods = client.pods().list().getItems();
        return  mapNodeAndPods(theNode,pods);
    }
    private NodePodMap mapNodeAndPods(Node node, List<Pod> pods){
        //NodePodMap newNodePodMap = new NodePodMap();
        String nodeName = node.getMetadata().getName();
        AtomicReference<String> newInternalIp= new AtomicReference<>("");
        node.getStatus().getAddresses().forEach( nodeAddress -> {
            if(nodeAddress.getType().equalsIgnoreCase("internalip")){
                newInternalIp.set(nodeAddress.getAddress());
            }
        });
        List<PodMap> newPodMap = pods.stream().filter(pod ->
                pod.getStatus().getHostIP().equalsIgnoreCase(newInternalIp.get())).map(podAddress ->{
            PodMap podMap = new PodMap(podAddress.getMetadata().getName(), podAddress.getMetadata().getNamespace());
            return podMap;

        }).collect(Collectors.toList());

        return new NodePodMap(nodeName,newPodMap,newInternalIp.get());
    }
}