package srmbs.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Resource> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addResource(Resource resource) {
        cartItems.add(resource);
    }

    public void showCart() {
        for (Resource r : cartItems) {
            r.display();
        }
    }
}