package platform.android.btdemo;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

public class BTDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

int resource;
	
	public BTDeviceAdapter(Context context, int resource,List<BluetoothDevice> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GridLayout listViewItem;
		
		BluetoothDevice device = getItem(position);
		
		String nameString = device.getName();
		String addressString = device.getAddress();
		
		if (convertView == null) {
			listViewItem = new GridLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater)getContext().getSystemService(inflater);
			li.inflate(resource, listViewItem, true);
		} else {
			listViewItem = (GridLayout) convertView;
		}
		
		TextView txtName = (TextView)listViewItem.findViewById(R.id.txtName);
		TextView txtAdd = (TextView)listViewItem.findViewById(R.id.txtAddress);
		
		txtName.setText(nameString);
		txtAdd.setText(addressString);
		
		return listViewItem;
	}
	
}
