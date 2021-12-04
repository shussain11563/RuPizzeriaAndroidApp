package project.fiverupizzeria;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class PizzaCustomizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_pizza_layout);
        System.out.println("Hello world");
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("ORDER");
        String pizzaName = intent.getStringExtra("PIZZA_TYPE");
        intent.getIntExtra("PIZZA_IMAGE", 0); //MAGIC NUMBER, MAKE NOT FOUND AND CHANE TO -1

        System.out.println(pizzaName);

        //set the name
        //set the image

    }

    /**
     * WE WANT TO DO ONDESTROY AFTER THIS ACTIVITY ENDS
     */
}

