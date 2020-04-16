package com.example.testParseMVCListViewSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DisplayActivity extends Activity {

    Double lon,lat;
    int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lon= bundle.getDouble("longitud");
		lat= bundle.getDouble("latitud");
        position= bundle.getInt("position");
        TextView textView =((TextView) findViewById(R.id.text_longitud));
		textView.setTextSize(40);
		textView.setText(lon.toString());
		TextView textView2 =((TextView) findViewById(R.id.text_latitud));
		textView2.setTextSize(40);
		textView2.setText(lat.toString());

	}

	public void saveLocationName(View view) {
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
        EditText editText = (EditText) findViewById(R.id.edit_point_name);
        String point_name = editText.getText().toString();
        bundle.putString("name", point_name);
        Intent intent = new Intent();
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}
}
