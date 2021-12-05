package project.fiverupizzeria;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private EditText phoneNumber;


    private static StoreOrders storeOrders; //remove this
    private static Order currentOrder;//remove this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = findViewById(R.id.phoneNumber);
        storeOrders = new StoreOrders();
        //set images maybe???


    }



    //onResume

    //onPause

    //onDestroy()



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
            this.currentOrder = new Order(phoneNumber);
        }


        if(checkPhoneNumber(phoneNumber))
        {
            //add relevent toasts/alerts/push notifcations

            Intent intent = new Intent(this, PizzaCustomizationActivity.class);

            intent.putExtra("PIZZA_NAME", stringPizzaRid);
            intent.putExtra("PIZZA_TYPE", pizzaType);
            intent.putExtra("PIZZA_IMAGE", pictureRid);
            intent.putExtra("ORDER", currentOrder);
            //intent.putExtra("ORDER", currentOrder);
            startActivity(intent);
        }
        else
        {
            errorInvalidPhoneNumberAlert();
            //
        }

        //Intent intent = new Intent(this, PizzaCustomizationActivity.class);


    }

    public void openDeluxeCustomizePizzaActivity(View view)
    {
        int name = R.string.DeluxePizza;
        String pizzaType = "Deluxe Pizza";
        int picture = R.drawable.deluxepizza;

        openPizzaCustomizationActivity(name ,pizzaType, picture);
    }

    public void openHawaiianCustomizePizzaActivity(View view)
    {
        int name = R.string.HawaiianPizza;
        String pizzaType = "Hawaiian Pizza";
        int picture = R.drawable.hawaiianpizza;
        openPizzaCustomizationActivity(name ,pizzaType, picture);
        //openPizzaCustomizationActivity(PizzaCustomizationActivity.class);

    }

    public void openPepperoniCustomizePizzaActivity(View view)
    {
        int name = R.string.PepperoniPizza;
        String pizzaType = "Pepperoni Pizza";
        int picture = R.drawable.peppizza;
        openPizzaCustomizationActivity(name ,pizzaType, picture);
        //openPizzaCustomizationActivity(PizzaCustomizationActivity.class);
    }


    public void openCurrentOrdersActivity(View view)
    {
        if(this.currentOrder != null)
        {
            //make order a variable
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            intent.putExtra("ORDER", this.currentOrder);
            intent.putExtra("STORE_ORDER", this.storeOrders);
            //data
            //safe initialize
            startActivity(intent);
        }
        else
        {
            errorNoCurrentOrderAlert();
        }
        //Intent intent = new Intent(this, CurrentOrderActivity.class);
        //startActivity(intent);
    }

    //redo titles in manifest and make them strings


    /**
     *
     *
     * void openCurrentOrdersWindow(ActionEvent event) throws IOException {
     *         if(this.currentOrder != null) {
     *     \
     *
     *             RuPizzaCurrentOrderController setController = fxmlLoader.getController();
     *             setController.setMainController(this);
     *             setController.safeInitialize();
     *
     *             Stage stage = new Stage();
     *             stage.setScene(new Scene(root, 900, 700));
     *             stage.setTitle("Customize Your Pizza");
     *             stage.show();
     *
     *     }
     */

    private void errorDuplicatePhoneNumber()
    {

    }



    private void errorNoCurrentOrderAlert()
    {
    }

    private void errorInvalidPhoneNumberAlert()
    {

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