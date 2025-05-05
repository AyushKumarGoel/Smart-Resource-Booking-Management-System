package services;

import entity.Resource;
import java.util.List;
import java.util.stream.Collectors;
import repository.ResourceRepository;

public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void addResource(Resource resource) {
        resourceRepository.addResource(resource);
    }

    // ✅ Add this method
    public List<Resource> getAllResources() {
        return resourceRepository.getAllResources();
    }

    // ✅ Optional: To support filtering by user
    public List<Resource> getResourcesByUserId(String userId) {
        return resourceRepository.getAllResources().stream()
                .filter(r -> userId.equals(r.getAddedByUserId()))
                .collect(Collectors.toList());
    }
}
