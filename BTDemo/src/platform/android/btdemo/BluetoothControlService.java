package platform.android.btdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

public class BluetoothControlService {
	// Debugging
    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;

    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Unique UUID for this application
    private static final UUID MY_UUID =
        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    
    // Member fields
    private final BluetoothAdapter mAdapter;
    //private final Handler mHandler;
   /* private AcceptThread mSecureAcceptThread;
    private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;*/
    private int mState;
    
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    
    private BluetoothSocket mBluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    
    public BluetoothControlService() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
    }
    
    public void connectToDevice(BluetoothDevice device) {
    	UUID uuid = device.getUuids()[0].getUuid();
    	try {
			mBluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
			mBluetoothSocket.connect();
			inputStream = mBluetoothSocket.getInputStream();
			outputStream = mBluetoothSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void sendString(String message) {
    	try {
    		byte[] byteArray = (message + " ").getBytes();
    		byteArray[byteArray.length - 1] = 0;
			outputStream.write(byteArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
