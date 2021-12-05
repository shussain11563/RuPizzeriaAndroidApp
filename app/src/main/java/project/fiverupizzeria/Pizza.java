package project.fiverupizzeria;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The Pizza class acts as a blueprint for a Pizza object
 * and its subclasses such as Deluxe, Hawaiian, and Pepperoni
 * which contains attributes such as toppings, size, price.
 * This class also contains methods to allow pizza objects with
 * manipulation of toppings and calculation of pizzas.
 * @author Sharia Hussain, David Lam
 */
public abstract class Pizza implements Serializable {

    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /** Constants used for the sales tax for all Pizzas */
    static final double SALES_TAX_RATE = 6.625;

    /** Constants used for the maximum toppings for all Pizzas */
    static final int MAX_TOPPINGS = 7;

    /** Constants used for the increase in price when size changes */
    static final double SIZE_INCREASE_COST = 2;

    /** Constants used to calculate additional toppings and their cost */
    static final double ADDITIONAL_TOPPINGS_COST = 1.49;

    /**
     * Returns the price of the pizza
     * @return price, the price of the pizza
     */
    public abstract double price();

    /**
     * Adds a topping to the pizza.
     * @return true if the toppings was added or not.
     */
    public boolean addTopping(Topping topping) {
        if(this.toppings.contains(topping)) {
            return false;
        }

        this.toppings.add(topping);
        return true;
    }

    /**
     * Removing a topping on the pizza.
     * @return true if the toppings was removed or not.
     */
    public boolean removeTopping(Topping topping) {
        if(this.toppings.contains(topping)) {
            this.toppings.remove(topping);
            return true;
        }

        return false;
    }

    /**
     * Sets the size of the Pizza
     * @param size the size of the pizza to be changed to.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Retrieves the toppings list.
     * @return the arraylist of the toppings.
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Calculates the price of the pizza based on the size.
     * @return the price cost of the size
     */
    public double calculateSizeOfPizza() {
        double sizeCost = 0;

        switch (this.size) {
            case Small:
                sizeCost = 0;
                break;
            case Medium:
                sizeCost += SIZE_INCREASE_COST;
                break;
            case Large:
                sizeCost += (SIZE_INCREASE_COST + SIZE_INCREASE_COST);
                break;
        }

        return sizeCost;
    }

    /**
     * Overrides toString method to represent Pizza objects.
     * @return a textual representation of the Pizza's information.
     */
    @Override
    public String toString() {
        String pizzaType = this.getClass().getSimpleName() + " Pizza";
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String toppings = "";

        for(int i = 0; i < this.toppings.size(); i++) {
            toppings += this.toppings.get(i) + ", ";
        }

        return String.format("%s %s, %s$%s", this.size ,pizzaType, toppings, df.format(this.price()));
    }






}