package project.fiverupizzeria;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


public class CurrentOrderActivity extends AppCompatActivity
{
    private Order currentOrder;
    private StoreOrders storeOrders;
    private ListView orderListView;
    private ArrayAdapter<Pizza> pizzaArrayAdapter;
    private double salesTax;
    private double orderTotal;
    private double subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_layout);

        Intent intent = getIntent();
        this.currentOrder = (Order) intent.getSerializableExtra("ORDER");
        this.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");

        orderListView = findViewById(R.id.orderListView);

        pizzaArrayAdapter = new ArrayAdapter<Pizza>(this, android.R.layout.simple_list_item_1, this.currentOrder.getPizzas());
        orderListView.setAdapter(pizzaArrayAdapter);
        processCost();
        updatePrices();


        /*
        if(this.currentOrder!=null)
        {
            System.out.println("All Pizzas");
            ArrayList<Pizza> temp = this.currentOrder.getPizzas();
            System.out.println(temp.size());
            System.out.println(this.currentOrder.getPhoneNumber());
            for(int i = 0; i < temp.size(); i++)
            {
                System.out.println(temp.get(i).toString());
            }

        }
        else
        {
            System.out.println("Hello");
        }

         */
    }

    //onresume, we reupdate the arrayadapter

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

    private void updatePrices() {
        //setPhoneNumberTextArea(this.currentOrder.getPhoneNumber());
        //setSubtotalTextArea(priceToString(subtotal));
        //setSalesTaxTextArea(priceToString(salesTax));
        //setOrderTotalTextArea(priceToString(orderTotal));
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
     * Alert box when removing the last pizza
     */

    /*
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


    public void showConfirmationForOrderToBePlaced()
    {
        if(this.currentOrder != null && this.currentOrder.getPizzas().size() > 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Warning with Placing Order");
            alert.setMessage("You are about to place an order");

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    this.storeOrders.addOrder(this.currentOrder);
                    clear();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }else {
            errorNoCurrentOrderAlert();
        }
    }


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



     */

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
