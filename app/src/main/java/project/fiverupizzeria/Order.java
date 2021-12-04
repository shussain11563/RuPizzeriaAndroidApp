package project.fiverupizzeria;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Order is the class that holds a singular order with possibly multiple Pizzas.
 * Contains constructors and methods for setting, getting, and manipulating
 * pizzas in the order.
 * @author Sharia Hussain, David Lam
 */
public class Order implements Serializable {
    private String phoneNumber;
    private ArrayList<Pizza> pizzas;
    private double totalPrice;

    /**
     * Constructs and initializes an Order for use.
     * Used for StoreOrders and CurrenOrders for information.
     * @param phoneNumber the phoneNumber for the order.
     */
    public Order(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        pizzas = new ArrayList<Pizza>();
        totalPrice = 0;
    }


    /**
     * Sets the totalPrice for the order
     * @param totalPrice the totalPrice for the order.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Retrieves the total price of the order.
     * @return the total price of the order.
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Retrieves the list of pizzas in the order.
     * @return the list of pizzas in the order.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Retrieves the phoneNumber for the order.
     * @return the phoneNumber for the order.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Adds the pizza to the order.
     * @param pizza the pizza to be added.
     */
    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    /**
     * Removes the pizza to the order.
     * @param pizza the pizza to be removed.
     */
    public void removePizza(Pizza pizza) {
        this.pizzas.remove(pizza);
    }




}
