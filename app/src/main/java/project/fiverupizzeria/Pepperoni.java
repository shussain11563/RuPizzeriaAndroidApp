package project.fiverupizzeria;

/**
 * Pepperoni is the subclass for Pizza and defines pizzas with type Pepperoni.
 * Contains constructors and methods for setting a Pepperoni pizza.
 * @author Sharia Hussain, David Lam
 */

public class Pepperoni extends Pizza {

    /** Constants used for the minimum cost of the Pepperoni Pizza */
    private static final double MIN_COST = 8.99;

    /** Constants used for the minimum toppings for the Pepperoni Pizza */
    private static final int MIN_TOPPING = 1;

    /**
     * Constructs and initializes a Pepperoni pizza object for use.
     * Sets all the initial toppings.
     */
    public Pepperoni() {
        toppings.add(Topping.Pepperoni);
        this.size = Size.Small;
    }

    /**
     * Calculates the price for the pizza with toppings and size.
     * @return the price of the pizza.
     */
    @Override
    public double price() {
        double runningCost = 0;
        double sizeCost = calculateSizeOfPizza();

        runningCost += MIN_COST;
        runningCost += sizeCost;

        for(int i = this.MIN_TOPPING; i < toppings.size(); i++) {
            runningCost += Pizza.ADDITIONAL_TOPPINGS_COST;
        }

        return runningCost;
    }
}
