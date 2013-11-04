package platform.android.btdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ControlActivity extends Activity {

	private Button btnSend;
	private EditText txtSend;
	private BluetoothControlService mBluetoothControlService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		btnSend = (Button)findViewById(R.id.btnSend);
		txtSend = (EditText)findViewById(R.id.txtSend);
		mBluetoothControlService = AndronoApp.mBluetoothControlService;
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//mBluetoothControlService.sendString(txtSend.getText().toString());
				mBluetoothControlService.write(txtSend.getText().toString().getBytes());
			}			
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

}
