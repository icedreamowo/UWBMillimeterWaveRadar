package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.JClient;
import data.*;

public class UpdateListener implements ActionListener{
	private JClient client = null;
	private ClientFrame mainFrame = null;
	private UpdateThread up = null;
	
	UpdateListener(ClientFrame mainFrame){
		this.mainFrame = mainFrame;
		this.client = mainFrame.client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		up = new UpdateThread();
		up.start();
	}
	
	class UpdateThread extends Thread{
		public void run() {
			mainFrame.clearFaceCombo();
			DataPackage data = new DataPackage(DataPackage.DevicesList);
			data.setData(client.searchDevicesList());
			mainFrame.setFaceCombo(data.getData());
		}
	}
}