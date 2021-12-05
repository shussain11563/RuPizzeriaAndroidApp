package project.fiverupizzeria;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;


public class PizzaCustomizationActivity extends AppCompatActivity
{
    private ImageButton imageButton;
    private TextView chosenPizzaTextView;
    private ArrayAdapter adapter;

    //remove edittext and change to textview
    private EditText priceTextArea;

    private Order currentOrder;

    private Pizza pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_pizza_layout);
        System.out.println("Hello world");
        Intent intent = getIntent();
        this.currentOrder = (Order) intent.getSerializableExtra("ORDER");
        int pizzaName = intent.getIntExtra("PIZZA_NAME", 0);
        String pizzaFlavor = intent.getStringExtra("PIZZA_TYPE");
        int pizzaPictureRid = intent.getIntExtra("PIZZA_IMAGE", 0); //MAGIC NUMBER, MAKE NOT FOUND AND CHANE TO -1


        /*
        IF RID == NOT FOUND, ERROR MESSAGE?!?!?!
         */

        chosenPizzaTextView = findViewById(R.id.chosenPizzaTextView);
        chosenPizzaTextView.setText(pizzaName);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setImageResource(pizzaPictureRid);


        Pizza pizza = PizzaMaker.createPizza(pizzaFlavor); //null

        this.pizza = pizza;

        updateListView();

        this.priceTextArea = findViewById(R.id.priceTextArea);

        setPrice();


        //chosenPizzaTextView.setText();


        //System.out.println(pizzaName);


        //pizzaType is used to make new pizza

        //currentOrder.addPizza(new Deluxe());
        //currentOrder.addPizza(new Hawaiian());

        //set the name
        //set the imag


    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }




    /**
     * Sets the price of the pizza to the text area.
     */
    private void setPrice()
    {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        priceTextArea.setText(df.format((pizza.price())));
        disableEditText(this.priceTextArea);
    }

    private void updateListView()
    {
        /*
        selectedToppings = this.pizza.getToppings();
        ArrayList<Topping> allToppings = new ArrayList<Topping>(Arrays.asList(Topping.values()));
        allToppings.removeAll(selectedToppings);
        additionalToppings = allToppings;

        ObservableList<Topping> selectedToppingsList = FXCollections.observableArrayList(selectedToppings);
        selectedToppingsListView.setItems(FXCollections.observableList(selectedToppingsList));
        ObservableList<Topping> additionalToppingsList = FXCollections.observableArrayList(additionalToppings);
        additionalToppingsListView.setItems(FXCollections.observableList(additionalToppingsList));

         */
    }



    /**
     * WE WANT TO DO ONDESTROY AFTER THIS ACTIVITY ENDS
     */


    public void addToOrderAlertBox() {

    }




    /**
     * Checks whether a topping is essential for a Deluxe Pizza.
     * @param selectedItem the topping that is either essential or not.
     * @return true if essential, false otherwise
     */
    public boolean checkDeluxeToppings(Topping selectedItem) {
        if(selectedItem.toString().equals("Sausage") || selectedItem.toString().equals("Onion")
                || selectedItem.toString().equals("GreenPepper") || selectedItem.toString().equals("BlackOlives")
                || selectedItem.toString().equals("DicedTomatoes")) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether a topping is essential for a Hawaiian Pizza.
     * @param selectedItem the topping that is either essential or not.
     * @return true if essential, false otherwise
     */
    public boolean checkHawaiianToppings(Topping selectedItem) {
        if(selectedItem.toString().equals("Pineapple") || selectedItem.toString().equals("Ham")){
            return true;
        }
        return false;
    }

    /**
     * Checks whether a topping is essential for a Pepperoni Pizza.
     * @param selectedItem the topping that is either essential or not.
     * @return true if essential, false otherwise
     */
    public boolean checkPepperoniToppings(Topping selectedItem) {
        if(selectedItem.toString().equals("Pepperoni")) {
            return true;
        }
        return false;
    }
}

