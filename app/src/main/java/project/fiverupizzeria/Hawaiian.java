package project.fiverupizzeria;

/**
 * Hawaiian is the subclass for Pizza and defines pizzas with type Hawaiian.
 * Contains constructors and methods for setting a Hawaiian pizza.
 * @author Sharia Hussain, David Lam
 */

public class Hawaiian extends Pizza {

    /** Constants used for the minimum cost of the Hawaiian Pizza */
    private static final double MIN_COST = 10.99;

    /** Constants used for the minimum toppings for the Hawaiian Pizza */
    private static final int MIN_TOPPING = 2;

    /**
     * Constructs and initializes a Hawaiian pizza object for use.
     * Sets all the initial toppings.
     */
    public Hawaiian() {
        this.toppings.add(Topping.Pineapple);
        this.toppings.add(Topping.Ham);
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
