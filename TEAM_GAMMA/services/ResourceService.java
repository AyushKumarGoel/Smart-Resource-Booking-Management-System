package services;

import entity.*;
import repository.*;
import java.util.*;

public class ResourceService {
    private ResourceRepository repo;

    public ResourceService(ResourceRepository repo) { this.repo = repo; }
    public void addResource(Resource r) { repo.addResource(r); }
    public List<Resource> getResources() { return repo.getAllResources(); }
}