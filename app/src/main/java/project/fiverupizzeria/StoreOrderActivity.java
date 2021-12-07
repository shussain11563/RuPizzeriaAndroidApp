package project.fiverupizzeria;

import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * RuPizzaStoreOrderController is a class that handles all the events driven by the I/O in the application
 * involving Store Orders.
 * @author Sharia Hussain, David Lam
 */

public class StoreOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private StoreOrders storeOrders;
    private Order currentOrderTemp;
    private Order currentOrder;
    private Spinner spinnerPhoneNumber;
    private ListView storeOrderListView;
    private ArrayAdapter<String> spinnerArrayAdapterPhoneNumber;
    private ArrayAdapter<Pizza> pizzaArrayAdapter;
    EditText priceStoreActivity;

    /**
     * When an Activty is first created, it calls onCreate which sets all default activities
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     * onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_store_orders_layout);

        priceStoreActivity = findViewById(R.id.priceStoreActivity);
        storeOrderListView = findViewById(R.id.storeOrderListView);

        Intent intent = getIntent();
        this.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        this.currentOrderTemp = (Order) intent.getSerializableExtra("ORDER");
        spinnerPhoneNumber = findViewById(R.id.spinnerPhoneNumber);
        spinnerArrayAdapterPhoneNumber = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, phoneNumbers());
        spinnerPhoneNumber.setAdapter(spinnerArrayAdapterPhoneNumber);
        spinnerPhoneNumber.setOnItemSelectedListener(this);

        String phoneNumber = "";
        if(spinnerPhoneNumber.getSelectedItem() != null) {
            phoneNumber = spinnerPhoneNumber.getSelectedItem().toString();
        }

        Order orderCopy = copy(this.storeOrders.find(phoneNumber));
        if(orderCopy != null)
        {
            pizzaArrayAdapter = new ArrayAdapter<Pizza>(this, android.R.layout.simple_list_item_1, orderCopy.getPizzas());

        }

        storeOrderListView.setAdapter(pizzaArrayAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Method that runs when the activity stops
     */
    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent();
        intent.putExtra("ORDER", currentOrder);
        intent.putExtra("STORE_ORDERS", this.storeOrders);
        setResult(RESULT_OK, intent);
    }

    /**
     * Populates the spinners with Phone Numbers
     */
    private void populatePhoneNumber() {
        ArrayList<String> phoneNumbers = phoneNumbers();

        this.spinnerArrayAdapterPhoneNumber.clear();
        this.spinnerArrayAdapterPhoneNumber.addAll(phoneNumbers);
        if(spinnerPhoneNumber.getAdapter().getCount() >=0)
        {
            spinnerPhoneNumber.setSelection(0);
        }

    }

    /**
     * Method that returns the phone numbers in the store orders
     */
    private ArrayList<String> phoneNumbers() {
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        ArrayList<Order> orders = this.storeOrders.getOrders();
        for(int i = 0; i < orders.size(); i++) {
            phoneNumbers.add(orders.get(i).getPhoneNumber());
        }

        return phoneNumbers;
    }

    /**
     * Method that cancels the order
     * @param view the view of the android activity
     */
    public void cancelOrder(View view) {
        String phoneNumber = (String) this.spinnerPhoneNumber.getSelectedItem();

        Order order = copy(storeOrders.find(phoneNumber));
        if(order!=null) {
            storeOrders.removeOrder(storeOrders.find(phoneNumber));
            Intent intent = new Intent();
            intent.putExtra("ORDER", currentOrder);
            intent.putExtra("STORE_ORDERS", this.storeOrders);
            setResult(RESULT_OK, intent);
        }
        else {
            errorCannotCancelOrder();
        }

        this.spinnerArrayAdapterPhoneNumber.remove(phoneNumber); //removes from spinner

        populatePhoneNumber();
        if(pizzaArrayAdapter == null)
        {
            showCannotCancelToast();
            return;
        }
        this.pizzaArrayAdapter.clear();
        this.pizzaArrayAdapter.notifyDataSetChanged();
        if(this.spinnerPhoneNumber.getSelectedItem() != null)
        {
            order = copy(this.storeOrders.find((String) this.spinnerPhoneNumber.getSelectedItem()));
        }


        if(order != null) {
            this.pizzaArrayAdapter.addAll(order.getPizzas());
            this.pizzaArrayAdapter.notifyDataSetChanged();
        }
        else
        {
            this.pizzaArrayAdapter.clear();
            this.pizzaArrayAdapter.notifyDataSetChanged();
        }


        this.priceStoreActivity.getText().clear();
        disableEditText(this.priceStoreActivity);
        showOrderIsCancelledToast();
    }

    /**
     * Disables the text to be edited
     * @param editText text to be edited
     */
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
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

    /**
     * Shows toast box when order has been cancelled
     */
    public void showCannotCancelToast() {
        Context context = getApplicationContext();
        CharSequence text = "Cannot Cancel Order";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Method that copies an order
     * @param copyThis order to be copied
     */
    private Order copy(Order copyThis) {
        if(copyThis == null)
        {
            return null;
        }
        Order order = new Order(copyThis.getPhoneNumber());
        order.setTotalPrice(copyThis.getTotalPrice());

        for(int i = 0; i < copyThis.getPizzas().size(); i++)
        {
            order.addPizza(copyThis.getPizzas().get(i));
        }

        return order;
    }

    /**
     * Sets the phone number
     * @param phoneNum string of the phone number
     */
    private void setPhoneNumber(String phoneNum) {
        String phoneNumber = phoneNum;

        if(phoneNumber != null)
        {
            //Order order = this.storeOrders.find(phoneNumber);
            Order order = copy(this.storeOrders.find(phoneNumber));
            this.pizzaArrayAdapter.clear();
            this.pizzaArrayAdapter.notifyDataSetChanged();


            //this.pizzaArrayAdapter
            this.pizzaArrayAdapter.addAll(order.getPizzas());
            this.pizzaArrayAdapter.notifyDataSetChanged();


            DecimalFormat df = new DecimalFormat("#,##0.00");
            priceStoreActivity.setText(df.format(order.getTotalPrice()));

        }
    }

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String phoneNumber = (String) parent.getItemAtPosition(position);
        setPhoneNumber(phoneNumber);
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
