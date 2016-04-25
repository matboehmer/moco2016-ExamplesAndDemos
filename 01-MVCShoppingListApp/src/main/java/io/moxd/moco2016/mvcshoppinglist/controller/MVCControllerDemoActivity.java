package io.moxd.moco2016.mvcshoppinglist.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.moxd.moco2016.mvcshoppinglist.R;
import io.moxd.moco2016.mvcshoppinglist.model.ShoppingListModel;


/**
 * This Activity implements the controller of our little MVC demo application, the view is implemented in file
 * activity_main.xml, and the model in Class ShoppingListModel.
 */
public class MVCControllerDemoActivity extends Activity implements ShoppingListModel.OnModelChangeListener {

    private Button buttonAddItem;
    private EditText editTextNewItem;
    private TextView textShoppingList;
    private Button buttonRemoveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to views
        buttonAddItem = (Button) findViewById(R.id.buttonAddItem);
        buttonRemoveItem = (Button) findViewById(R.id.buttonRemoveItem);
        editTextNewItem = (EditText) findViewById(R.id.editTextNewItem);
        textShoppingList = (TextView) findViewById(R.id.textShoppingList);

        // add handler methods
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get data from view
                String newItem = editTextNewItem.getText().toString();

                // add data to model
                ShoppingListModel model = ShoppingListModel.getInstance();
                model.addItem(newItem);

            }
        });

        buttonRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from view
                String newItem = editTextNewItem.getText().toString();

                // remove item from model
                ShoppingListModel model = ShoppingListModel.getInstance();
                model.removeItem(newItem);
            }
        });

        // register controller for model updates
        ShoppingListModel.getInstance().setOnModelChangeListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        doServerSimulation();
    }

    @Override
    public void onModelChanged(final ArrayList<String> allCurrentItems) {
        final StringBuffer sb = new StringBuffer("On our shopping list: ");

        for (String item : allCurrentItems) {
            sb.append("\n" + item);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textShoppingList.setText(sb.toString());
            }
        });
    }

    /**
     * This is just a simulation to show that the model can also be changed by somebody else than the user himself; for
     * instance a remote service running on a server or some events which are happening.
     */
    private void doServerSimulation() {
        Timer serverSimulation = new Timer();
        serverSimulation.schedule(new TimerTask() {
            @Override
            public void run() {
                ShoppingListModel.getInstance().addItem("random" + new Random().nextInt(100));
            }
        }, 15000, 10000);
    }
}
