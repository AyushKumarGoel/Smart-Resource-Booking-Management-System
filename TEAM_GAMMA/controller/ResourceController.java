package controller;

import entity.Resource;
import java.util.List;
import services.ResourceService;

public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService; 
    }

    public void addResource(Resource resource) {
        resourceService.addResource(resource);
    }

    public void viewResources() {
        List<Resource> resources = resourceService.getAllResources();
        if (resources.isEmpty()) {
            System.out.println("No resources found.");
            return;
        }
        for (Resource res : resources) {
            System.out.println("ID: " + res.getId() + ", Name: " + res.getName() +
                    ", Type: " + res.getType() + ", Cost/hr: " + res.getCostPerHour());
        }
    }

    public void viewResourcesByUser(String userId) { 
        List<Resource> userResources = resourceService.getResourcesByUserId(userId);
        if (userResources.isEmpty()) {
            System.out.println("No resources found.");
            return;
        }
        for (Resource res : userResources) {
            System.out.println("ID: " + res.getId() + ", Name: " + res.getName() +
                    ", Type: " + res.getType() + ", Cost/hr: " + res.getCostPerHour());
        }
    }
}
