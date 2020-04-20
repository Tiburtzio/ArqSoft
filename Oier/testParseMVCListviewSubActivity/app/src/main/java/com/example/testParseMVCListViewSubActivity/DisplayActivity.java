package com.example.testParseMVCListViewSubActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DisplayActivity extends Activity {

    int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position= bundle.getInt("position");

	}

	public void saveName(View view) {
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
        EditText editText = (EditText) findViewById(R.id.edit_name);
        String point_name = editText.getText().toString();
        bundle.putString("nombre", point_name);
        Intent intent = new Intent();
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}
}
