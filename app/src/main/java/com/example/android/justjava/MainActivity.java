package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 2;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView ordersummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        ordersummaryTextView.setText(message);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method returns the total price.
     * @return total price.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream==true)
            basePrice += 1;
        if (addChocolate==true)
            basePrice += 2;
        return quantity * basePrice;
    }

    /**
     * This method composes the order summary.
     * @param price: total price
     *             addWhipped cream: boolean for whipped cream
     *             addChocolate: boolean for chocolate
     *             name: Name of customer
     * @return the message to be printed as the order summary.
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name + "\n";
        priceMessage += "Add whipped cream?: " + addWhippedCream;
        priceMessage += "\nAdd chocolate?: " + addChocolate;
        priceMessage += "\nQuantity: " + quantity + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}