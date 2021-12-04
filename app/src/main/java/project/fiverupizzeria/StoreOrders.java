package project.fiverupizzeria;

import android.os.Parcel;
import android.os.Parcelable;

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
public class StoreOrders implements Parcelable {
    private ArrayList<Order> orders;

    /**
     * Constructs and initializes an StoreOrder for use.
     * Used for manipulation of the StoreOrders and for information.
     */

    public StoreOrders() {
        this.orders = new ArrayList<Order>();
    }

    protected StoreOrders(Parcel in) {
        orders = in.createTypedArrayList(Order.CREATOR);
    }

    public static final Creator<StoreOrders> CREATOR = new Creator<StoreOrders>() {
        @Override
        public StoreOrders createFromParcel(Parcel in) {
            return new StoreOrders(in);
        }

        @Override
        public StoreOrders[] newArray(int size) {
            return new StoreOrders[size];
        }
    };

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
        dest.writeTypedList(orders);
    }
}
