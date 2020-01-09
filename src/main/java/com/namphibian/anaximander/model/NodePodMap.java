package com.namphibian.anaximander.model;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.namphibian.anaximander.model.ChartEntry;

public class NodePodMap {
    public String name;
    public String internalIp;
    public List<PodMap> pods = new ArrayList<PodMap>();
    public List<String> distinctNamespaces = new ArrayList<String>();
    public Map<String, List<String>> mapPods = new HashMap<String, List<String>>();
    private static final Logger logger = LoggerFactory.getLogger(NodePodMap.class);
    public ChartEntry chartEntry = new ChartEntry();
    public NodePodMap() {
    }



    public NodePodMap(String name, List<PodMap> pods, String internalIp) {
        this.name = name;
        this.pods = pods;
        this.internalIp = internalIp;
        this.distinctNamespaces.addAll( this.listNamespacesOnNode()) ;
        this.createNamespacePodMap();

        chartEntry.name = name;

        this.mapPods.forEach((key, value) -> {
            ChartEntry childNamespace = new ChartEntry();
            childNamespace.name = key;
            value.forEach(valuePod->{
                ChartEntry childPod = new ChartEntry();

                childPod.name = valuePod;
     

                childPod.size = 1;
                childNamespace.addChildEntry(childPod);
            });
            chartEntry.addChildEntry(childNamespace);
        });


    }
    private List<String> listNamespacesOnNode(){


        return this.pods.stream().map(podMap -> podMap.nameSpace).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

    }
    private void createNamespacePodMap(){

        this.pods.stream().forEach(pod -> {
            if(!this.mapPods.containsKey(pod.nameSpace)){
                ArrayList<String> podList =new ArrayList<>();
                podList.add(pod.name);
                this.mapPods.put(pod.nameSpace,podList);
            }
            else{
                List<String> podList =this.mapPods.get(pod.nameSpace);
                podList.add(pod.name);
                this.mapPods.put(pod.nameSpace,podList);

            }
        });
    }


}
