package project.fiverupizzeria;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class PizzaCustomizationActivity extends AppCompatActivity implements OnItemSelectedListener
{
    private Order currentOrder;
    private StoreOrders storeOrders;
    private Pizza pizza;
    private String pizzaFlavor;
    private ImageButton imageButton;
    private TextView chosenPizzaTextView;
    private ArrayAdapter<Topping> selectedToppingsAdapter;
    private ArrayAdapter<Topping> additionalToppingsAdapter;
    private Spinner spinner;
    private ListView availableToppingsView;
    private ListView selectedToppingsView;


    //remove edittext and change to textview
    private EditText priceTextArea;



    private ArrayAdapter<Size> spinnerArrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_pizza_layout);

        Intent intent = getIntent();
        this.currentOrder = (Order) intent.getSerializableExtra("ORDER");
        this.storeOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        int pizzaName = intent.getIntExtra("PIZZA_NAME", 0);
        this.pizzaFlavor = intent.getStringExtra("PIZZA_TYPE");
        int pizzaPictureRid = intent.getIntExtra("PIZZA_IMAGE", 0); //MAGIC NUMBER, MAKE NOT FOUND AND CHANE TO -1

        //change from simple list item to somehhing
       ;
        /*
        IF RID == NOT FOUND, ERROR MESSAGE?!?!?!
         */

        chosenPizzaTextView = findViewById(R.id.chosenPizzaTextView);
        chosenPizzaTextView.setText(pizzaName);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setImageResource(pizzaPictureRid);

        //
        this.spinner = findViewById(R.id.spinner);
        spinnerArrayAdapter = new ArrayAdapter<Size>(this,
                android.R.layout.simple_spinner_dropdown_item, Arrays.asList(Size.values()));
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        //onclicklistener for listview and spinner

        Pizza pizza = PizzaMaker.createPizza(pizzaFlavor); //null

        this.pizza = pizza;


        this.priceTextArea = findViewById(R.id.priceTextArea);

        setPrice();


        selectedToppingsView = findViewById(R.id.selectedToppingsView);
        availableToppingsView = findViewById(R.id.additionalToppingsView);


        //change order
        updateListView();



        availableToppingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Topping topping = (Topping) availableToppingsView.getItemAtPosition(position);
                addToppings(topping);
                System.out.println(topping);

            }
        });

        selectedToppingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Topping topping = (Topping) selectedToppingsView.getItemAtPosition(position);
                System.out.println(topping);
                removeToppings(topping);
                //removeSelectedPizza((Pizza) orderListView.getItemAtPosition(position));
            }
        });
    }





    public void removeToppings(Topping topping)
    {
        if(topping != null)
        {
            if(selectedToppingsView.getAdapter().getCount() > 0) //orderListView.getAdapter().getCount() > 0
            {
                if(pizzaFlavor.equals("Deluxe Pizza")) {
                    if(checkDeluxeToppings(topping)) {
                        showConfirmationRemoveEssentialToppings(topping);
                    }else {
                        callRemoveToppings(topping);
                    }
                } else if(pizzaFlavor.equals("Hawaiian Pizza")) {
                    if(checkHawaiianToppings(topping)) {
                        showConfirmationRemoveEssentialToppings(topping);
                    }else {
                        callRemoveToppings(topping);
                    }

                } else if(pizzaFlavor.equals("Pepperoni Pizza")) {
                    if(checkPepperoniToppings(topping)) {
                        showConfirmationRemoveEssentialToppings(topping);
                    }else {
                        callRemoveToppings(topping);
                    }
                }
            }
            else if(selectedToppingsView.getAdapter().getCount() <= 0) {
                showConfirmationNoToppingsOnPizza();
            }
        }
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }





    /*
    public void spinnerControl()
    {
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Object item = parent.getItemAtPosition(pos);
                        System.out.println("Hello");
                        System.out.println(item.toString());     //prints the text in spinner item.

                    }
                    public void onNothingSelected(AdapterView<?> parent)
                    {
                    }
                });
    }

     */


    /**
     * Sets the price of the pizza to the text area.
     */
    private void setPrice()
    {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        priceTextArea.setText(df.format((pizza.price())));
        disableEditText(this.priceTextArea);
    }

    //ondestory/onstop






    private void updateListView()
    {

        ArrayList<Topping> selectedToppings = this.pizza.getToppings();
        ArrayList<Topping> allToppings = new ArrayList<Topping>(Arrays.asList(Topping.values()));
        allToppings.removeAll(selectedToppings);
        ArrayList<Topping> additionalToppings = allToppings;

        selectedToppingsAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, selectedToppings);
        additionalToppingsAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, additionalToppings);

        this.selectedToppingsView.setAdapter(selectedToppingsAdapter);
        this.availableToppingsView.setAdapter(additionalToppingsAdapter);

        //might have to update adapter
    }



    /**
     * Method that alerts users that their pizza is added to their order.
     */
    public void addToOrderAlertBox() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Added to Order");
        alert.setMessage("Order has been added.");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void callRemoveToppings(Topping toppingObj) {
        Topping topping = toppingObj;

        additionalToppingsAdapter.add(topping);
        selectedToppingsAdapter.remove(topping);
        additionalToppingsAdapter.notifyDataSetChanged();
        selectedToppingsAdapter.notifyDataSetChanged();

        this.pizza.removeTopping(topping);
        setPrice();
    }



    /**
     * Shows alert box regarding removing an essential topping.
     */
    public void showConfirmationRemoveEssentialToppings(Topping topping) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning with Removing Toppings");
        alert.setMessage("You are removing essential toppings");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callRemoveToppings(topping);
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Shows alert box when there is no toppings on pizza.
     */
    public void showConfirmationNoToppingsOnPizza() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning with Removing Toppings");
        alert.setMessage("No Toppings on Pizza");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    void addToppings(Topping topping)
    {
        if(topping != null)
        {
            if(selectedToppingsView.getAdapter().getCount() < Pizza.MAX_TOPPINGS)
            {

                additionalToppingsAdapter.remove(topping);
                selectedToppingsAdapter.add(topping);
                additionalToppingsAdapter.notifyDataSetChanged();
                selectedToppingsAdapter.notifyDataSetChanged();
                this.pizza.addTopping(topping);
                setPrice();

                /**
                 *         additionalToppingsAdapter.add(topping);
                 *         selectedToppingsAdapter.remove(topping);
                 *         additionalToppingsAdapter.notifyDataSetChanged();
                 *         selectedToppingsAdapter.notifyDataSetChanged();
                 */

            }
            else
            {
                showMaxToppingsOnPizza();

            }

        }
    }

    /**
     * Shows alert box when more than 7 toppings is trying to be added to the pizza
     */
    public void showMaxToppingsOnPizza() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning with Adding Toppings");
        alert.setMessage("You can only have at most 7 toppings");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
    /**
     * Shows toast box when order has been added
     */
    public void showAddedToOrderToast() {
        Context context = getApplicationContext();
        CharSequence text = "Added Pizza to Order";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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

    public void addOrder(View view)
    {
        this.currentOrder.addPizza(this.pizza);

        showAddedToOrderToast();
        Intent intent = new Intent();
        intent.putExtra("ORDER", currentOrder);
        intent.putExtra("STORE_ORDERS", this.storeOrders);
        setResult(RESULT_OK, intent);

        Pizza pizza = PizzaMaker.createPizza(this.pizzaFlavor);
        this.pizza = pizza;
        updateListView();
        setPrice();
        spinner.setSelection(0); //remove magic number
        addToOrderAlertBox();

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
        Size selectedSize = (Size) parent.getItemAtPosition(position);
        pizza.setSize(selectedSize);
        setPrice();
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

