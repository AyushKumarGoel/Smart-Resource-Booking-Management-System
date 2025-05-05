package srmbs.services;

import srmbs.models.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceService {
    private List<Resource> resourceList = new ArrayList<>();
    private int resourceIdCounter = 1;

    public void addResource(String name, String type, double costPerHour) {
        Resource res = new Resource(resourceIdCounter++, name, type, costPerHour);
        resourceList.add(res);
        System.out.println("Resource added: " + name);
    }

    public List<Resource> getAllResources() {
        return resourceList;
    }
}