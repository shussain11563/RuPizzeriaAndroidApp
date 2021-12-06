package project.fiverupizzeria;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


public class StoreOrderActivity extends AppCompatActivity
{
    private StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_store_orders_layout);
        Intent intent = getIntent();
        this.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        populatePhoneNumber();
    }

    private void populatePhoneNumber()
    {
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        ArrayList<Order> orders = this.storeOrders.getOrders();

        for(int i = 0; i < orders.size(); i++)
        {
            phoneNumbers.add(orders.get(i).getPhoneNumber());
        }
        ObservableList<String> phoneNumbersList = FXCollections.observableArrayList(phoneNumbers);
        this.customerPhoneNumberComboBox.setItems(phoneNumbersList);


    }




    /**
     * Alert box when there is no Current Order to be cancelled
     */
    private void errorCannotCancelOrder() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error with Cancelling Order");
        alert.setMessage("No Order Selected to Cancel");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Shows toast box when order has been cancelled
     */
    public void showOrderIsCancelledToast() {
        Context context = getApplicationContext();
        CharSequence text = "Order is Cancelled";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
