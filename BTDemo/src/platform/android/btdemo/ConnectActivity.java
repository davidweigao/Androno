package platform.android.btdemo;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ConnectActivity extends Activity {
	
	// Debugging
	private static final String TAG = "ConnectActivity";
	private static final boolean D = true;
	
	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	
	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	
	// Layout Views
	private ListView pairedDevicesListView;
	private Button btnConnect;
	
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothControlService mBluetoothControlService = null;
	private ArrayList<BluetoothDevice> pairedDevices = new ArrayList<BluetoothDevice>();
	private BTDeviceAdapter pairedDevicesListViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(D) Log.e(TAG, "+++ ON CREATE +++");
		
		// Set up the window layout
		setContentView(R.layout.activity_connect);
		mBluetoothControlService = ((AndronoApp)getApplicationContext()).mBluetoothControlService;
		// Set up UI Views
		pairedDevicesListView = (ListView)findViewById(R.id.listViewPairedDevices);
		btnConnect = (Button)findViewById(R.id.btnConnect);
		
		btnConnect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupConnection();				
			}
		});
		
		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	@Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            //if (mControlService == null) setupConnection();
        }
    }
	
	@Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        updatePairedDevices();
    }
	
    private void updatePairedDevices() {
    	Set<BluetoothDevice> pairedDevicesSet = mBluetoothAdapter.getBondedDevices();
    	pairedDevices.clear();
    	if(pairedDevicesSet.size() > 0) {
    		Log.d(TAG,"bigger than 0");
    		pairedDevices.addAll(pairedDevicesSet);
    		pairedDevicesListViewAdapter = new BTDeviceAdapter(this,R.layout.listview_pairedbtdevice,pairedDevices);
    		pairedDevicesListView.setAdapter(pairedDevicesListViewAdapter);
    		pairedDevicesListView.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Log.e(TAG, "selected" + arg2);
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
    		pairedDevicesListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					mBluetoothControlService.connectToDevice(pairedDevices.get(arg2));
					Intent k = new Intent(ConnectActivity.this,ControlActivity.class);
			        startActivity(k);
				}
			});
    	}
	}

	private void setupConnection() {
        Log.d(TAG, "setupConnection");

        // Initialize the BluetoothControlService to perform bluetooth connections
        //mControlService = new BluetoothChatService(this, mHandler);
        BluetoothDevice bDevice = (BluetoothDevice)pairedDevicesListView.getSelectedItem();
        if(bDevice != null)
        	mBluetoothControlService.connectToDevice(bDevice);
        else
        	Log.e(TAG, "Null selected");
    }
    
    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        //if (mControlService != null) mControlService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

}
