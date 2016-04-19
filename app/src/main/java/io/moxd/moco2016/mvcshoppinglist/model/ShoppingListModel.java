package io.moxd.moco2016.mvcshoppinglist.model;

import java.util.ArrayList;

/**
 * This class implements a simple model for our MVC example app. It uses the singleton pattern to
 * allow getting a reference on the model instance. One problem of a singleton is, that we cannot
 * assume anything about its lifetime. It might be removed from memory by the Android runtime.
 * Ideally this will later be implemented using a database or another persistent backend.
 */
public class ShoppingListModel {

    //********
    // following lines implement the constructor and singleton pattern for the model
    //

    private static ShoppingListModel instance;
    // our model is backed by a simple collection
    private final ArrayList list;
    private OnModelChangeListener onModelChangeListener;

    //********
    //following lines implement functional aspects of the model
    //

    private ShoppingListModel() {
        list = new ArrayList();
    }

    public static ShoppingListModel getInstance() {
        if (instance == null) {
            instance = new ShoppingListModel();
        }
        return instance;
    }

    public void addItem(String item) {
        list.add(item);
        notifyListener();
    }

    public void removeItem(String item) {
        list.remove(item);
        notifyListener();
    }

    //********
    // following lines are for providing a listener interface for model changes
    //

    public ArrayList<String> getAllItems() {
        return list;
    }

    public void setOnModelChangeListener(OnModelChangeListener onModelChangeListener) {
        this.onModelChangeListener = onModelChangeListener;
    }

    private void notifyListener() {
        if (onModelChangeListener != null) {
            onModelChangeListener.onModelChanged(list);
        }
    }

    public interface OnModelChangeListener {
        void onModelChanged(final ArrayList<String> allCurrentItems);
    }
}

