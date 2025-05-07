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
    public boolean deleteResourceById(String id) {
        return resources.removeIf(r -> r.getId().equals(id));
    }
    
    public boolean updateResource(String id, String newName, double newCost) {
        for (Resource r : resources) {
            if (r.getId().equals(id)) {
                r.setName(newName);
                r.setCostPerHour(newCost);
                return true;
            }
        }
        return false;
    }
    
}
