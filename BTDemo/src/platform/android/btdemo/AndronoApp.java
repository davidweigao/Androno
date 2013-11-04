package platform.android.btdemo;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;

public class AndronoApp extends Application{

	//public static BluetoothAdapter mBluetoothAdapter;
	public static BluetoothControlService mBluetoothControlService = new BluetoothControlService();
	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    public static final String TOAST = "toast";
    public static final String DEVICE_NAME = "device_name";
}
