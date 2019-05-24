/*����Java�ͻ���*/
package client;
import java.io.*;
import java.net.*;

public class JClient {
	private static final String MsgSuccess = "Success";
	private static final String MsgFail = "Fail";
	private static final String ClientOffline = "ClientOffline";
	private static final String SearchDevicesList = "SearchDevicesList";
	private static final String SearchDeviceData = "SearchDeviceData";
	private static final String AddDeviceName = "AddDeviceName";
	
	private oneClient client = null;
	private String name = null;
	
	public JClient(){
		client = new oneClient();
	}
	JClient(String name){
		client = new oneClient();
		this.name = name;
	}
	public void ready(String ServerAdress,int port,String name){
		this.name = name;						//���ܸ���˳��,�������ն������������,������null����
		client.ready(ServerAdress, port);
	}
	public String read(){
		try{
			return client.read();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public void print(String str){
		client.print(str);
	}
	public void println(String str){
		client.println(str);
	}
	public void close(){
		try{
			client.println(ClientOffline);
			client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getName() {								//ȡ��setName()����,ʹ��ready(String ServerAdress,int port,String name)
		return name;										//���캯������name
	}
	public boolean isRefused(){
		return client.socket == null;
	}
	
	public String searchDevicesList(){						//��ѯ����������б�
		try{
			String str = null,result = null;
			client.println(SearchDevicesList);				//���Ͳ�ѯ���������ָ��
			result = client.read();
			if(result.equals(MsgSuccess)){
				str = client.read();
				return str;
			}else{
				
				return null;								//��ѯʧ�����
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String searchDeviceData(String DeviceName){		//��ѯָ������������
		try{
			String str = null,result = null;
			client.println(SearchDeviceData);				//���Ͳ�ѯ����������ָ��
			client.println(DeviceName);
			result = client.read();
			if(result.equals(MsgSuccess)){
				str = client.read();
				return str;
			}else{
				
				return null;								//��ѯʧ�����
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public boolean addDeviceName(String DeviceName){						//��Ӵ��������(�ݻ�)
		try{
			String result = null;
			client.println(AddDeviceName);
			if(checkDeviceName(DeviceName)){
				client.println(DeviceName);
				result = client.read();
				if(result.equals(MsgSuccess)){
					return true;
				}else{
					
					return false;											//���ʧ�����
				}
			}
			return true;
		}catch(Exception e){
			return false;	
		}
	}
	
	private boolean checkDeviceName(String DeviceName){		//��⴫��������Ƿ���Ϲ淶
		return true;										//(�ݲ����)
	}

	class oneClient{
		private int port = 8000;
		private String adr = null;
		private Socket socket = null;
		private PrintWriter writer = null;
		private BufferedReader reader = null;
		
		oneClient(){}
		public boolean ready(String ServerAdress,int port){
			this.port = port;
			this.adr = ServerAdress;
			try{
				socket = new Socket(adr,port);
				writer = new PrintWriter(socket.getOutputStream());
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				println(getName());										//���ӳɹ������ն����� (name)
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		public String read() throws Exception{
			String str = null;
			str = reader.readLine();
			return str;
		}
		public void print(String str){
			writer.print(str);
			writer.flush();
		}
		public void println(String str){
			writer.println(str);
			writer.flush();
		}
		public void close() throws Exception{
			socket.close();
			reader.close();
			writer.close();
		}
	}
}
