package project.fiverupizzeria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;


public class CurrentOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order_layout);
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
}
