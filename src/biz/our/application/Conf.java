package biz.our.application;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import biz.our.application.data.ConnectionSettings;

public class Conf extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		textView = (TextView) findViewById(R.id.textViewTitle);
		// textview.setText("Simple configuration");
		setContentView(R.layout.conf);

		loadAll();
	}

	private void loadAll() {


		EditText edit = (EditText) findViewById(R.id.EditTextServer);
		edit.setHint(ConnectionSettings.getServer());

		edit = (EditText) findViewById(R.id.EditTextLogin);
		edit.setHint(ConnectionSettings.getLogin());

		edit = (EditText) findViewById(R.id.EditTextPassword);
		edit.setHint(ConnectionSettings.getPassword());

		edit = (EditText) findViewById(R.id.EditTextTarget);
		edit.setHint(ConnectionSettings.getTarget());

		Button button = (Button) findViewById(R.id.ButtonSendSave);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveAll();
			}
		});
	}

	private void saveAll() {

		EditText edit = (EditText) findViewById(R.id.EditTextServer);
		if (edit.getText().toString() != null)
			ConnectionSettings.setServer(edit.getText().toString());

		edit = (EditText) findViewById(R.id.EditTextLogin);
		if (edit.getText().toString() != null)
			ConnectionSettings.setLogin(edit.getText().toString());

		edit = (EditText) findViewById(R.id.EditTextPassword);
		if (edit.getText().toString() != null)
			ConnectionSettings.setPassword(edit.getText().toString());

		edit = (EditText) findViewById(R.id.EditTextTarget);
		if (edit.getText().toString() != null)
			ConnectionSettings.setTarget(edit.getText().toString());
	};

}
