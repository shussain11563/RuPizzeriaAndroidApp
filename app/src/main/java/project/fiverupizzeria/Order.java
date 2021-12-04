package project.fiverupizzeria;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Order is the class that holds a singular order with possibly multiple Pizzas.
 * Contains constructors and methods for setting, getting, and manipulating
 * pizzas in the order.
 * @author Sharia Hussain, David Lam
 */
public class Order implements Parcelable {
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

    protected Order(Parcel in) {
        phoneNumber = in.readString();
        //pizzas = in.createTypedArrayList(Pizza.CREATOR);
        totalPrice = in.readDouble();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

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


    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneNumber);
        //dest.writeTypedList(pizzas);
        dest.writeDouble(totalPrice);
    }
}
