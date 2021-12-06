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

public class MainActivity extends AppCompatActivity
{
    private EditText phoneNumber;
    public static StoreOrders storeOrders;
    public static Order currentOrder;

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
    }

    public void openPizzaCustomizationActivity(int stringPizzaRid, String pizzaType, int pictureRid)
    {

        System.out.println("phone number check on line 40");
        String phoneNumber = this.phoneNumber.getText().toString().trim();
        System.out.println(phoneNumber);
        if(this.storeOrders.find(phoneNumber)!=null) {
            errorDuplicatePhoneNumber();  //change this
            return;
        }
        boolean isValid = checkPhoneNumber(phoneNumber);
        boolean isSameNumber = this.currentOrder != null && (this.currentOrder.getPhoneNumber().equals(phoneNumber));

        if((isValid && this.currentOrder == null) || (isValid == true && isSameNumber == false))
        {
            System.out.println("New Order Object");
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
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            MainActivity.currentOrder = (Order) intent.getSerializableExtra("ORDER");
            MainActivity.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        }
    }

    public void openStoreOrdersActivity(View view)
    {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        intent.putExtra("ORDER", MainActivity.currentOrder);
        intent.putExtra("STORE_ORDERS", MainActivity.storeOrders);
        startActivityForResult(intent, 1);//magic number

    }

    public void openDeluxeCustomizePizzaActivity(View view)
    {

        int name = R.string.DeluxePizza;
        String pizzaType = "Deluxe Pizza";
        int picture = R.drawable.deluxepizza;
        openPizzaCustomizationActivity(name, pizzaType, picture);
    }

    public void openHawaiianCustomizePizzaActivity(View view)
    {
        int name = R.string.HawaiianPizza;
        String pizzaType = "Hawaiian Pizza";
        int picture = R.drawable.hawaiianpizza;
        openPizzaCustomizationActivity(name ,pizzaType, picture);
    }



    public void openPepperoniCustomizePizzaActivity(View view)
    {
        int name = R.string.PepperoniPizza;
        String pizzaType = "Pepperoni Pizza";
        int picture = R.drawable.peppizza;
        openPizzaCustomizationActivity(name ,pizzaType, picture);
    }


    public void openCurrentOrdersActivity(View view)
    {
        if(this.currentOrder != null)
        {
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            intent.putExtra("ORDER", MainActivity.currentOrder);
            intent.putExtra("STORE_ORDERS", this.storeOrders);
            startActivityForResult(intent, 1);
        }
        else {
            errorNoCurrentOrderAlert();
        }

    }

    //redo titles in manifest and make them strings



    private void errorDuplicatePhoneNumber()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Error with Phone Number");
        alert.setMessage("Phone Number Has Already Ordered");

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
        alert.setTitle("Error with Phone Number");
        alert.setMessage("Phone Number Not Valid");

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
        alert.setTitle("Error with Current Order");
        alert.setMessage("There is no current order.");

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