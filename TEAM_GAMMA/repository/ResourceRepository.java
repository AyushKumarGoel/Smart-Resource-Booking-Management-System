package repository;

import entity.Resource;
import java.util.ArrayList;
import java.util.List;

public class ResourceRepository {
    private final List<Resource> resources = new ArrayList<>();
    
    public void addResource(Resource resource) {
        resources.add(resource); 
    }

    public List<Resource> getAllResources() {
        return new ArrayList<>(resources);
    }
}
