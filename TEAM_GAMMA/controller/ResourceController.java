package controller;

import services.*; 
import entity.*;

public class ResourceController {
    private ResourceService service;

    public ResourceController(ResourceService service) { this.service = service; }

    public void addResource(Resource resource) {
        service.addResource(resource);
        System.out.println("Resource Added: " + resource.getName());
    }

    public void viewResources() {
        for (Resource r : service.getResources()) {
            System.out.println("Resource ID: " + r.getId() + ", Name: " + r.getName());
        }
    }
}