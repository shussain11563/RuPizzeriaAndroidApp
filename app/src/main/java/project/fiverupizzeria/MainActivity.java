package project.fiverupizzeria;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * RuPizzeriaController is a class that handles all the events driven by the I/O in the application.
 * @author Sharia Hussain, David Lam
 */

public class MainActivity extends AppCompatActivity
{
    private EditText phoneNumber;
    public static StoreOrders storeOrders;
    public static Order currentOrder;

    /**
     * When an Activty is first created, it calls onCreate which sets all default activities
     * @param savedInstanceState a reference to a Bundle object that is passed into the
     * onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = findViewById(R.id.phoneNumber);
        storeOrders = new StoreOrders();
    }

    //test this
    @Override
    protected void onResume() {
        super.onResume();
        if(currentOrder != null)
        {
            phoneNumber.setText(currentOrder.getPhoneNumber());
        }
    }

    /**
     * Handles processing the customization view and handles the creation of the order.
     * @param stringPizzaRid the R id of the pizza
     * @param pizzaType the type of pizza
     * @param pictureRid the picture R id of the pizza
     */
    public void openPizzaCustomizationActivity(int stringPizzaRid, String pizzaType, int pictureRid)
    {

        String phoneNumber = this.phoneNumber.getText().toString().trim();

        if(this.storeOrders.find(phoneNumber)!=null) {
            errorDuplicatePhoneNumber();  //change this
            return;
        }
        boolean isValid = checkPhoneNumber(phoneNumber);
        boolean isSameNumber = this.currentOrder != null && (this.currentOrder.getPhoneNumber().equals(phoneNumber));

        if((isValid && this.currentOrder == null) || (isValid == true && isSameNumber == false))
        {
            this.currentOrder = new Order(phoneNumber);
        }


        if(checkPhoneNumber(phoneNumber))
        {
            Intent intent = new Intent(this, PizzaCustomizationActivity.class);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Click to Continue with Order");

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    intent.putExtra("PIZZA_NAME", stringPizzaRid);
                    intent.putExtra("PIZZA_TYPE", pizzaType);
                    intent.putExtra("PIZZA_IMAGE", pictureRid);
                    intent.putExtra("ORDER", MainActivity.currentOrder);
                    intent.putExtra("STORE_ORDERS", MainActivity.storeOrders);
                    startActivityForResult(intent, 1);
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        } else {
            errorInvalidPhoneNumberAlert();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            MainActivity.currentOrder = (Order) intent.getSerializableExtra("ORDER");
            MainActivity.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        }
    }

    /**
     * Opens the Store Order Window View.
     * @param view the view of the android activity
     *
     */
    public void openStoreOrdersActivity(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        intent.putExtra("ORDER", MainActivity.currentOrder);
        intent.putExtra("STORE_ORDERS", MainActivity.storeOrders);
        startActivityForResult(intent, 1);//magic number
    }

    /**
     * Opens the Pizza Customization View to customize pizza for a Deluxe Pizza.
     * @param view the view of the android activity
     */
    public void openDeluxeCustomizePizzaActivity(View view) {

        int name = R.string.DeluxePizza;
        String pizzaType = "Deluxe Pizza";
        int picture = R.drawable.deluxepizza;
        openPizzaCustomizationActivity(name, pizzaType, picture);
    }

    /**
     * Opens the Pizza Customization View to customize pizza for a Hawaiian Pizza.
     * @param view the view of the android activity
     */
    public void openHawaiianCustomizePizzaActivity(View view) {
        int name = R.string.HawaiianPizza;
        String pizzaType = "Hawaiian Pizza";
        int picture = R.drawable.hawaiianpizza;
        openPizzaCustomizationActivity(name, pizzaType, picture);
    }

    /**
     * Opens the Pizza Customization View to customize pizza for a Pepperoni Pizza.
     * @param view the view of the android activity
     */
    public void openPepperoniCustomizePizzaActivity(View view) {
        int name = R.string.PepperoniPizza;
        String pizzaType = "Pepperoni Pizza";
        int picture = R.drawable.peppizza;
        openPizzaCustomizationActivity(name, pizzaType, picture);
    }

    /**
     * Opens the Current Order Window View.
     * @param view the view of the android activity
     */
    public void openCurrentOrdersActivity(View view) {
        if(this.currentOrder != null)
        {
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            intent.putExtra("ORDER", MainActivity.currentOrder);
            intent.putExtra("STORE_ORDERS", MainActivity.storeOrders);
            startActivityForResult(intent, 1);
        }
        else {
            errorNoCurrentOrderAlert();
        }

    }

    /**
     * Shows alert box when there is a phone number that already ordered
     * tries to order again.
     */
    private void errorDuplicatePhoneNumber() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.errorDuplicatePhoneNumberTitle);
        alert.setMessage(R.string.errorDuplicatePhoneNumberMessage);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Shows alert box when there is a phone number that is invalid.
     */
    private void errorInvalidPhoneNumberAlert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.errorInvalidPhoneNumberAlertTitle);
        alert.setMessage(R.string.errorInvalidPhoneNumberAlertMessage);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    /**
     * Displays alert box when manipulating an order that does not exist.
     */
    private void errorNoCurrentOrderAlert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.errorNoCurrentOrderAlertTitle);
        alert.setMessage(R.string.errorNoCurrentOrderAlertMessage);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Validates whether a phoneNumber is valid or not.
     * @param text the phone number from the total text area.
     * @return true if a phone number is valid, false otherwise.
     */
    private static boolean checkPhoneNumber(String text)
    {
        int maxDigitsOfNumber = 10;
        if(text.length() != maxDigitsOfNumber)
            return false;

        //make the 10 a static final constant
        for(int i = 0; i < text.length(); i++)
        {
            if(!Character.isDigit(text.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }
}