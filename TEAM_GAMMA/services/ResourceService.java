package services;

import entity.Resource;
import java.util.List;
import java.util.stream.Collectors;
import repository.ResourceRepository;

public class ResourceService {
    private ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void addResource(Resource resource) {
        resourceRepository.addResource(resource);  // No ID conflict check needed since IDs are auto-incremented
    }

    // Modify this method to return a List<Resource> instead of void
    public List<Resource> viewResources() {
        return resourceRepository.getAllResources(); // Return the list of resources
    }

    // Modify this method to return a List<Resource> instead of void
    public List<Resource> viewResourcesByUser(String managerId) {
        return resourceRepository.getAllResources().stream()
                .filter(r -> r.getManagerId().equals(managerId)) // Filter by managerId
                .collect(Collectors.toList()); // Return filtered list
    }
}
