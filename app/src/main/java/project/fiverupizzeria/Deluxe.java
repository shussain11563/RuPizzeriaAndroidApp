package project.fiverupizzeria;

/**
 * Deluxe is the subclass for Pizza and defines pizzas with type Deluxe.
 * Contains constructors and methods for setting a deluxe pizza.
 * @author Sharia Hussain, David Lam
 */

public class Deluxe extends Pizza {

    /** Constants used for the minimum cost of the Deluxe Pizza */
    private static final double MIN_COST = 12.99;

    /** Constants used for the minimum toppings for the Deluxe Pizza */
    private static final int MIN_TOPPING = 5;

    /**
     * Constructs and initializes a Deluxe pizza object for use.
     * Sets all the initial toppings.
     */
    public Deluxe() {
        this.toppings.add(Topping.Sausage);
        this.toppings.add(Topping.Onion);
        this.toppings.add(Topping.GreenPepper);
        this.toppings.add(Topping.BlackOlives);
        this.toppings.add(Topping.DicedTomatoes);
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
