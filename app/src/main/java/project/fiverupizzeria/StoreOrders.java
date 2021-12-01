package project.fiverupizzeria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * StoreOrders is the class that all the orders for the company.
 * Contains constructors and methods for setting, getting, and manipulating
 * orders in the StoreOrder.
 * @author Sharia Hussain, David Lam
 */
public class StoreOrders {
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
     * Exports the information of the orders to a file
     * @param file the files to be exported.
     */
    public void export(File file) throws FileNotFoundException
    {
        if(file == null)
        {
            throw new FileNotFoundException();
        }
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println("----------- STORE ORDERS -----------\n");

        for(int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            printWriter.println(String.format("ORDER NUMBER: %s", order.getPhoneNumber()));
            ArrayList<Pizza> pizzasInOrder = order.getPizzas();
            for(int j = 0; j < pizzasInOrder.size(); j++) {
                Pizza pizza = pizzasInOrder.get(j);
                printWriter.println(String.format("- %s", pizza.toString()));
            }
            printWriter.print("\n");
        }
        printWriter.close();
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
