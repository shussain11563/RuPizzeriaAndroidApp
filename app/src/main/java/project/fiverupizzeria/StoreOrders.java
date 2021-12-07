package project.fiverupizzeria;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * StoreOrders is the class that all the orders for the company.
 * Contains constructors and methods for setting, getting, and manipulating
 * orders in the StoreOrder.
 * @author Sharia Hussain, David Lam
 */
public class StoreOrders implements Serializable {
    private ArrayList<Order> orders;

    /**
     * Constructs and initializes an StoreOrder for use.
     * Used for manipulation of the StoreOrders and for information.
     */

    public StoreOrders() {
        this.orders = new ArrayList<Order>();
    }


    /**
     * Adds the order to the StoreOrders.
     * @param order the order to be added.
     */
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    /**
     * Removes the order from StoreOrders.
     * @param order the order to be removed.
     */
    public void removeOrder(Order order) {
        this.orders.remove(order);
    }

    /**
     * Retrieves the orders from storeOrders.
     * @return the arraylist of orders.
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }


    /**
     * Finds the order based on the phoneNyumber
     * @param phoneNumber the phoneNumber to find the order.
     * @return the order if found else null.
     */
    public Order find(String phoneNumber) {
        for(int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getPhoneNumber().equals(phoneNumber)) {
                return orders.get(i);
            }
        }

        return null;
    }


}
