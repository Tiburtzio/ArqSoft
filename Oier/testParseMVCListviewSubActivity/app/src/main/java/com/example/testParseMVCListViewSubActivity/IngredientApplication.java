package com.example.testParseMVCListViewSubActivity;

import android.app.Application;

import com.example.testParseMVCListViewSubActivity.Model.Ingrediente;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class IngredientApplication extends Application {

    public List<Ingrediente> ingredientList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Ingrediente.class);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myAppId") //si no has cambiado APP_ID, sino pon el valor de APP_ID
                .clientKey("empty")
                .server("https://miservidorpruebas.herokuapp.com/parse/")   // '/' important after 'parse'
                .build());


    }
}
