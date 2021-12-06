package project.fiverupizzeria;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class StoreOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private StoreOrders storeOrders;
    private Spinner spinnerPhoneNumber;
    private ListView storeOrderListView;
    private ArrayAdapter<String> spinnerArrayAdapterPhoneNumber;
    private ArrayAdapter<Pizza> pizzaArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_store_orders_layout);
        Intent intent = getIntent();
        this.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        spinnerPhoneNumber = findViewById(R.id.spinnerPhoneNumber);
        //storeOrderListView
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



        spinnerPhoneNumber = findViewById(R.id.spinnerPhoneNumber);
        spinnerArrayAdapterPhoneNumber = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, phoneNumbers);
        spinnerPhoneNumber.setAdapter(spinnerArrayAdapterPhoneNumber);
        spinnerPhoneNumber.setOnItemSelectedListener(this);


    }

    public void cancelOrder(View view)
    {
        String phoneNumber = (String) this.spinnerPhoneNumber.getSelectedItem();
        System.out.println("Cancel Ordering Test " + phoneNumber);

        Order order = storeOrders.find(phoneNumber);
        if(order!=null)
        {
            storeOrders.removeOrder(storeOrders.find(phoneNumber));
        }
        else
        {
            errorCannotCancelOrder();
        }


        //this.storeOrderListView.getItems().clear();

        populatePhoneNumber();
        //this.orderTotalTextArea.clear();

        //update

        //set new thing
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
/*
    private void setPhoneNumber(String phoneNumber)
    {
        this.storeOrderListView.getItems().clear();
        String phoneNumber = this.customerPhoneNumberComboBox.getSelectionModel().getSelectedItem();


        if(phoneNumber != null)
        {
            Order order = this.storeOrders.find(phoneNumber);
            this.storeOrderListView.getItems().clear();



            ObservableList<Pizza> pizzasList = FXCollections.observableArrayList(order.getPizzas());
            this.storeOrderListView.setItems(FXCollections.observableList(pizzasList));

            DecimalFormat df = new DecimalFormat("#,##0.00");
            orderTotalTextArea.setText(df.format(order.getTotalPrice()));

        }
        //update after removing order
    }


 */

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String phoneNumber = (String) parent.getItemAtPosition(position);
        //setPhoneNumber(phoneNumber);

    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
