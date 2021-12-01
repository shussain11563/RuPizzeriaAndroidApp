package project.fiverupizzeria;

/**
 * PizzaMaker is a class that helps create a Pizza
 * based on the type of flavor it is.
 * @author Sharia Hussain, David Lam
 */
public class PizzaMaker {
    /**
     * Creates the pizza based on the flavor.
     * @param flavor the string flavor for the pizza.
     * @return the pizza that is created.
     */
    public static Pizza createPizza(String flavor) {
        Pizza pizza = null;

        if(flavor.toLowerCase().equals("pepperoni pizza")) {
            pizza = new Pepperoni();
        }
        else if(flavor.toLowerCase().equals("deluxe pizza")) {
            pizza = new Deluxe();
        }
        else if(flavor.toLowerCase().equals("hawaiian pizza")) {
            pizza = new Hawaiian();
        }

        return pizza;
    }
}