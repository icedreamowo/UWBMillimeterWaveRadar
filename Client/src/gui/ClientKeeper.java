package gui;

public class ClientKeeper extends Thread{
	private ClientFrame mainFrame = null;
	ClientKeeper(ClientFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	public void run(){
		if(mainFrame.client.isRefused()){
			mainFrame.showError("client is null");
		//���socket����״̬...
		}
	}
}
