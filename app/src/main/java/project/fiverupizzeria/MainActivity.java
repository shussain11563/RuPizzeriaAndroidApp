package project.fiverupizzeria;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private EditText phoneNumber;

    private StoreOrders storeOrders; //remove this
    private Order currentOrder;//remove this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = findViewById(R.id.phoneNumber);

    }

    public void openPizzaCustomizationActivity(Class<?> cls)
    {

        String phoneNumber = this.phoneNumber.getText().toString().trim();
        if(this.storeOrders.find(phoneNumber)!=null) {
            errorDuplicatePhoneNumber();  //change this
            return;
        }
        boolean isValid = checkPhoneNumber(phoneNumber);
        boolean isSameNumber = this.currentOrder != null && (this.currentOrder.getPhoneNumber().equals(phoneNumber);

        if((isValid && this.currentOrder == null) || (isValid == true && isSameNumber == false))
        {
            this.currentOrder = new Order(phoneNumber);
        }


        if(checkPhoneNumber(phoneNumber))
        {
            //add relevent toasts/alerts/push notifcations

            Intent intent = new Intent(this, cls);


            startActivity(intent);
        }
        else
        {

        }

        //Intent intent = new Intent(this, PizzaCustomizationActivity.class);


    }

    public void openCurrentOrdersActivity(View view)
    {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
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