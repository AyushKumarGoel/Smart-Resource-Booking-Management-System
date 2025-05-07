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

    // Modify this method to handle the List<Resource> returned from viewResources()
    public void viewResources() {
        List<Resource> resources = resourceService.viewResources();
        if (resources.isEmpty()) {
            System.out.println("No resources found.");
            return;
        }
        for (Resource res : resources) {
            System.out.println("ID: " + res.getId() + ", Name: " + res.getName() +
                    ", Type: " + res.getType() + ", Cost/hr: " + res.getCostPerHour());
        }
    }

    // Modify this method to handle the List<Resource> returned from viewResourcesByUser()
    public void viewResourcesByUser(String userId) {
        List<Resource> userResources = resourceService.viewResourcesByUser(userId);
        if (userResources.isEmpty()) {
            System.out.println("No resources found.");
            return;
        }
        for (Resource res : userResources) {
            System.out.println("ID: " + res.getId() + ", Name: " + res.getName() +
                    ", Type: " + res.getType() + ", Cost/hr: " + res.getCostPerHour());
        }
    }
    public boolean deleteResource(String id) {
        return resourceService.deleteResource(id);
    }
    
    public boolean updateResource(String id, String name, double cost) {
        return resourceService.updateResource(id, name, cost);
    }
    
}
