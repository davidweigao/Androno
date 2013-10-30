package platform.android.btdemo;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

public class AndronoApp extends Application{

	//public static BluetoothAdapter mBluetoothAdapter;
	public static BluetoothControlService mBluetoothControlService = new BluetoothControlService();
	
}
