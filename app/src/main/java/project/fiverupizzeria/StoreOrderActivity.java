package project.fiverupizzeria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;


public class StoreOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_store_orders_layout);
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
}
