package com.example.testParseMVCListViewSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.testParseMVCListViewSubActivity.Model.Ingrediente;
import com.example.testParseMVCListViewSubActivity.Model.InterestPoint;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainListActivity extends AppCompatActivity {

    private static final int SHOW_SUBACTIVITY = 1;
    private static final int SHOW_ADDACTIVITY = 2;
    private static final int SHOW_ADDINGACTIVITY = 3;
    private ListView listView;
    private ArrayAdapter<InterestPoint> todoItemsAdapter;
    TravelPointsApplication tpa;
    private InterestPoint aInterestPoint;

    //Objetos necesarios para los ingredientes
    private ArrayAdapter<Ingrediente> todoItemsAdapter2;
    IngredientApplication ia;
    private Ingrediente aIngrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tpa, AddPointActivity.class);
                startActivityForResult(intent, SHOW_ADDACTIVITY);
            }
        });

        //Boton añadir ingredientes
        FloatingActionButton botonIngredientes = findViewById(R.id.ingredientes);
        botonIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ia, AddIngredientActivity.class);
                startActivityForResult(intent, SHOW_ADDINGACTIVITY);
            }
        });

        listView = (ListView) findViewById(R.id.list);
        tpa = (TravelPointsApplication)getApplicationContext();
        ia = (IngredientApplication)getApplicationContext();
        getServerList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.action_get: {
                getServerList();
                break;
            }

            case R.id.action_new: {
                Intent intent = new Intent(tpa, AddPointActivity.class);
                startActivityForResult(intent, SHOW_ADDACTIVITY);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SHOW_SUBACTIVITY)
            {
                Bundle bundle = data.getExtras();
                String name= bundle.getString("name");
                int position= bundle.getInt("position");
                InterestPoint item = (InterestPoint) listView.getItemAtPosition(position);
                item.setNombre(name);
                item.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //    Toast.makeText(
                            //            getBaseContext(),
                            //            "newParseObject(): object saved in server: " + aInterestPoint.getObjectId(),
                            //            Toast.LENGTH_SHORT).show();
                            todoItemsAdapter.notifyDataSetChanged();
                            Log.d("object udpate server:", "update()");
                        } else {
                            Log.d("update failed, reason: "+ e.getMessage(), "update()");
                            Toast.makeText(
                                    getBaseContext(),
                                    "update(): Object udpate failed, reason: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }

            else if (requestCode == SHOW_ADDACTIVITY)
            {
                Bundle bundle = data.getExtras();
                Double latitud = bundle.getDouble("latitud");
                Double longitud = bundle.getDouble("longitud");
                newParseObject("",latitud,longitud);

            }

            else if(requestCode == SHOW_ADDINGACTIVITY)
            {
                Bundle bundle = data.getExtras();
                String nombre = bundle.getString("nombre");

            }

        }
    }

    public void newParseObject(String name, Double latitud, Double longitud) {

        aInterestPoint = new InterestPoint();
        aInterestPoint.setNombre(name);
        aInterestPoint.setLatitud(latitud);
        aInterestPoint.setLongitud(longitud);

        aInterestPoint.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //    Toast.makeText(
                    //            getBaseContext(),
                    //            "newParseObject(): object saved in server: " + aInterestPoint.getObjectId(),
                    //            Toast.LENGTH_SHORT).show();
                    tpa.pointList.add(aInterestPoint);
                    todoItemsAdapter.notifyDataSetChanged();
                    Log.d("object saved in server:", "newParseObject()");
                } else {
                    Log.d("save failed, reason: "+ e.getMessage(), "newParseObject()");
                    Toast.makeText(
                            getBaseContext(),
                            "newParseObject(): Object save failed  to server, reason: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void getServerList() {

        ParseQuery<InterestPoint> query = ParseQuery.getQuery("InterestPoint");
        query.findInBackground(new FindCallback<InterestPoint>() {
            public void done(List<InterestPoint> objects, ParseException e) {
                if (e == null) {
                    tpa.pointList = objects;
                    if (todoItemsAdapter==null) {
                        todoItemsAdapter = new ArrayAdapter<InterestPoint>(getApplicationContext(), R.layout.row_layout, R.id.listText, tpa.pointList);
                        listView.setAdapter(todoItemsAdapter);

                        Log.d("object query server:", "todoItemsAdapter= null");

                    }
                    else
                    {
                        todoItemsAdapter.notifyDataSetChanged();
                        Log.d("object query server:", "todoItemsAdapter= notifyDataSetChanged");

                    }
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            InterestPoint item = (InterestPoint) listView.getItemAtPosition(position);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", position);
                            bundle.putDouble("latitud", item.getLatitud());
                            bundle.putDouble("longitud", item.getLongitud());
                            Intent intent = new Intent(tpa, DisplayActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, SHOW_SUBACTIVITY);


                        }
                    });
                    Log.d("query OK ", "getServerList()");
                } else {
                    Log.d("error query, reason: " + e.getMessage(), "getServerList()");
                    Toast.makeText(
                            getBaseContext(),
                            "getServerList(): error  query, reason: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getServerList2(){

        ParseQuery<Ingrediente> query = ParseQuery.getQuery("Ingrediente");
        query.findInBackground(new FindCallback<Ingrediente>() {
            public void done(List<Ingrediente> objects, ParseException e) {
                if (e == null) {
                    ia.ingredientList = objects;
                    if (todoItemsAdapter2==null) {
                        todoItemsAdapter2 = new ArrayAdapter<Ingrediente>(getApplicationContext(), R.layout.row_layout, R.id.listText, ia.ingredientList);
                        listView.setAdapter(todoItemsAdapter2);

                        Log.d("object query server:", "todoItemsAdapter2= null");

                    }
                    else
                    {
                        todoItemsAdapter2.notifyDataSetChanged();
                        Log.d("object query server:", "todoItemsAdapter2= notifyDataSetChanged");

                    }
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Ingrediente item2 = (Ingrediente) listView.getItemAtPosition(position);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", position);
                            bundle.putString("nombre",item2.getNombre());

                            //FALTA APAÑAR ESTO!!!!
                            Intent intent = new Intent(tpa, DisplayActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, SHOW_SUBACTIVITY);
                            //EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

                        }
                    });
                    Log.d("query OK ", "getServerList()");
                } else {
                    Log.d("error query, reason: " + e.getMessage(), "getServerList()");
                    Toast.makeText(
                            getBaseContext(),
                            "getServerList(): error  query, reason: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
