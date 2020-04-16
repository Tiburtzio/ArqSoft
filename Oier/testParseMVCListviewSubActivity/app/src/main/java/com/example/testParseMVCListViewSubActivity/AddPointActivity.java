package com.example.testParseMVCListViewSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPointActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addpoint);

	}

	public void saveLocation(View view) {
		Bundle bundle = new Bundle();
		EditText editLatitud = (EditText) findViewById(R.id.edit_latitud);
		EditText editLongitud = (EditText) findViewById(R.id.edit_longitud);
		String longitud = editLongitud.getText().toString();
		String latitud = editLatitud.getText().toString();
		double lon =  Double.valueOf(longitud);
		double lat =  Double.valueOf(latitud);
		bundle.putDouble("longitud", lon);
		bundle.putDouble("latitud", lat);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}
}
