package com.example.testParseMVCListViewSubActivity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class AddIngredientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingredient);

    }

    public void saveIngredient(View view) {
        Bundle bundle = new Bundle();
        EditText editNombre = (EditText) findViewById(R.id.nombreIngrediente);
        String nombreIng = editNombre.getText().toString();
        bundle.putString("nombre",nombreIng);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
