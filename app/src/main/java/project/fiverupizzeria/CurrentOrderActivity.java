package project.fiverupizzeria;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * CurrentOrderAcitivty is a class that handles all the events driven by the I/O in the application
 * involving Store Orders.
 * @author Sharia Hussain, David Lam
 */

public class CurrentOrderActivity extends AppCompatActivity
{

    private Order currentOrder;
    private StoreOrders storeOrders;
    private ListView orderListView;
    private ArrayAdapter<Pizza> pizzaArrayAdapter;
    private double salesTax;
    private double orderTotal;
    private double subtotal;
    EditText subtotalText, salesTaxText, orderTotalText;
    EditText phoneNumberOrderActivity;

    /**
     * When an Activty is first created, it calls onCreate which sets all default activities
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     * onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_layout);

        Intent intent = getIntent();
        this.currentOrder = (Order) intent.getSerializableExtra("ORDER");
        this.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");

        orderListView = findViewById(R.id.orderListView);
        subtotalText = findViewById(R.id.subtotalText);
        salesTaxText = findViewById(R.id.salesTaxText);
        orderTotalText = findViewById(R.id.orderTotalText);

        phoneNumberOrderActivity = findViewById(R.id.phoneNumberOrderActivity);
        //phoneNumberOrderActivity.setText(this.currentOrder.getPhoneNumber());

        pizzaArrayAdapter = new ArrayAdapter<Pizza>(this, android.R.layout.simple_list_item_1, this.currentOrder.getPizzas());
        orderListView.setAdapter(pizzaArrayAdapter);
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                removeSelectedPizza((Pizza) orderListView.getItemAtPosition(position));
            }
        });

        processCost();
        updatePrices();
    }

    /**
     * Clears all necessary Data and Text for the user
     */
    private void clear()
    {
        this.salesTaxText.getText().clear();
        this.subtotalText.getText().clear();
        this.orderTotalText.getText().clear();
        this.phoneNumberOrderActivity.getText().clear();
        disableEditText(salesTaxText);
        disableEditText(subtotalText);
        disableEditText(orderTotalText);
        disableEditText(phoneNumberOrderActivity);

        //this.currentOrder.getPizzas().clear();
        this.pizzaArrayAdapter.clear();
        this.pizzaArrayAdapter.notifyDataSetChanged();

        this.currentOrder = null; //???

        Intent intent = new Intent();
        intent.putExtra("ORDER", this.currentOrder);
        intent.putExtra("STORE_ORDERS", this.storeOrders);
        setResult(RESULT_OK, intent);
    }

    /**
     * Calls Remove Pizza in the Order Class
     */
    public void callRemovePizza(Pizza pizzaObj) {
        Pizza pizza = pizzaObj;
        pizzaArrayAdapter.remove(pizza);
        pizzaArrayAdapter.notifyDataSetChanged();
        this.currentOrder.removePizza(pizza);
    }

    /**
     * Method that calls all the cost related methods
     */
    private void processCost() {
        calculateSubtotal();
        calculateSalesTax();
        calculateOrderTotal();
    }

    /**
     * Calculates the sales tax of the order.
     */
    public void calculateSalesTax() {
        this.salesTax = (Pizza.SALES_TAX_RATE/100) * subtotal;
    }

    /**
     * Converts a double value to a string Price
     * @param value double value to be convert to string in decimal formace
     */
    public String priceToString(double value) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return String.format("$%s", df.format(value));
    }

    /**
     * Updates the prices in the TextAreas
     */
    private void updatePrices() {
        setPhoneNumberTextArea(this.currentOrder.getPhoneNumber());
        setSubtotalTextArea(priceToString(subtotal));
        setSalesTaxTextArea(priceToString(salesTax));
        setOrderTotalTextArea(priceToString(orderTotal));
    }

    /**
     * Sets the Phone Number Text Area
     */
    public void setPhoneNumberTextArea(String phoneNumber) {
        phoneNumberOrderActivity.setText(phoneNumber);
        disableEditText(phoneNumberOrderActivity);
    }

    /**
     * Sets the SubTotal TextArea
     */
    public void setSubtotalTextArea(String subtotal) {
        subtotalText.setText(subtotal);
        disableEditText(subtotalText);
    }

    /**
     * Prints the sales tax total into the text area.
     * @param salesTax the string representation of the sales tax of the order.
     */
    public void setSalesTaxTextArea(String salesTax) {
        salesTaxText.setText(salesTax);
        disableEditText(salesTaxText);
        //disable text
    }

    /**
     * Prints the order total into the text area.
     * @param total the total price of the order.
     */
    public void setOrderTotalTextArea(String total) {
        orderTotalText.setText(total);
        disableEditText(orderTotalText);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Calculates the order total of all the pizzas using the
     * subtotal and the sales tax.
     */
    public void calculateOrderTotal() {
        this.orderTotal = this.subtotal + this.salesTax;
        this.currentOrder.setTotalPrice(this.orderTotal);
    }

    /**
     * Calculates the subtotal of all the pizzas in the current order.
     */
    public void calculateSubtotal() {
        double subtotal = 0;

        ArrayList<Pizza> pizzas = this.currentOrder.getPizzas();
        for(int i = 0; i < pizzas.size(); i++) {
            subtotal += pizzas.get(i).price();
        }

        this.subtotal = subtotal;
    }



    /**
     * Calls the Method to confirm the order
     * @param view the view of the android activity
     */
    public void placeOrder(View view) {
        showConfirmationForOrderToBePlaced();
    }

    /**
     * Alert box to show a confirmation when the order is to be placed
     */
    public void showConfirmationForOrderToBePlaced()
    {
        if(this.currentOrder != null && this.currentOrder.getPizzas().size() > 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Warning with Placing Order");
            alert.setMessage("You are about to place an order");

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    addToStoreOrder();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }else {
            errorNoCurrentOrderAlert();
        }
    }

    /**
     * Method to add Current Order to Store Orders, calls a toast to
     * show adding and clears the Text Areas
     */
    private void addToStoreOrder()
    {
        this.storeOrders.addOrder(this.currentOrder);
        showOrderIsPlacedToast();
        clear();
    }

    /**
     * Alert box when there is no Current Order to be Placed
     */
    private void errorNoCurrentOrderAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error with Current Order");
        alert.setMessage("No current order or there is no pizzas in the current order.");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Method that removes the selected pizza and recalculates costs and prices.
     * @param pizza the pizza object to be removed
     */
    private void removeSelectedPizza(Pizza pizza)
    {
        if(pizza != null) {
            if(orderListView.getAdapter().getCount() > 0) {
                callRemovePizza(pizza);
            }
            else {
                showNoPizzasInOrder();
            }
            processCost();
            updatePrices();
        }
        else {
            showNoPizzasSelected();
        }
    }

    /**
     * Alert box when no pizza is selected
     */
    public void showNoPizzasSelected() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning with Removing Pizzas From Order");
        alert.setMessage("No pizzas selected!");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Alert box when removing the last pizza
     */
    public void showNoPizzasInOrder() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning with Removing Pizzas From Order");
        alert.setMessage("There are no pizzas in the order!");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Shows toast box when order has been placed
     */
    public void showOrderIsPlacedToast() {
        Context context = getApplicationContext();
        CharSequence text = "Order is Placed";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
