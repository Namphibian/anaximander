package com.namphibian.anaximander.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ChartEntry {
    public String name;
    public Integer size;
    private List<ChartEntry> children;
    public ChartEntry(){

    }

    public List<ChartEntry> getChildren() {

        return children;



    }

    public void addChildEntry(ChartEntry childEntry){
        if (this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(childEntry);
    }
}
