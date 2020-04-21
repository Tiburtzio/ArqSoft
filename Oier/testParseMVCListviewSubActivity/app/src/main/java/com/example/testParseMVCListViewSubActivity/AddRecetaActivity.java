package com.example.testParseMVCListViewSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddRecetaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreceta);

    }

    public void saveReceta(View view) {
        Bundle bundle = new Bundle();
        EditText editNombre = (EditText) findViewById(R.id.nombreReceta);
        EditText editdescripcion = (EditText) findViewById(R.id.descripcion);
        String nombreIng = editNombre.getText().toString();
        String nombreDes = editdescripcion.getText().toString();
        bundle.putString("nombre",nombreIng);
        bundle.putString("descripcion",nombreDes);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
