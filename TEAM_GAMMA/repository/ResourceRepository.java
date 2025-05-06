package repository;

import entity.Resource;
import java.util.ArrayList;
import java.util.List;

public class ResourceRepository {
    private final List<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) {
        // Check for duplicate ID
        for (Resource r : resources) {
            if (r.getId().equals(resource.getId())) {
                throw new IllegalArgumentException("Resource with this ID already exists.");
            }
        }
        resources.add(resource);
    }

    public List<Resource> getAllResources() {
        return resources;
    }
}
