package srmbs.models;

public class ResourceManager extends User {
    public ResourceManager(String username, String password) {
        super(username, password, "ResourceManager");
    }

    @Override
    public void showMenu() {
        System.out.println("\n--- Resource Manager Menu ---");
        System.out.println("1. Add Resource");
        System.out.println("2. View Resources");
        System.out.println("3. Delete Resource");
    }
}
