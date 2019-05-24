/*用于Java客户端*/
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
		this.name = name;						//不能更换顺序,先设置终端名后才能链接,否则发送null名字
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
	public String getName() {								//取消setName()方法,使用ready(String ServerAdress,int port,String name)
		return name;										//或构造函数设置name
	}
	public boolean isRefused(){
		return client.socket == null;
	}
	
	public String searchDevicesList(){						//查询传感器编号列表
		try{
			String str = null,result = null;
			client.println(SearchDevicesList);				//发送查询传感器编号指令
			result = client.read();
			if(result.equals(MsgSuccess)){
				str = client.read();
				return str;
			}else{
				
				return null;								//查询失败情况
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String searchDeviceData(String DeviceName){		//查询指定传感器数据
		try{
			String str = null,result = null;
			client.println(SearchDeviceData);				//发送查询传感器数据指令
			client.println(DeviceName);
			result = client.read();
			if(result.equals(MsgSuccess)){
				str = client.read();
				return str;
			}else{
				
				return null;								//查询失败情况
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public boolean addDeviceName(String DeviceName){						//添加传感器编号(暂缓)
		try{
			String result = null;
			client.println(AddDeviceName);
			if(checkDeviceName(DeviceName)){
				client.println(DeviceName);
				result = client.read();
				if(result.equals(MsgSuccess)){
					return true;
				}else{
					
					return false;											//添加失败情况
				}
			}
			return true;
		}catch(Exception e){
			return false;	
		}
	}
	
	private boolean checkDeviceName(String DeviceName){		//检测传感器编号是否符合规范
		return true;										//(暂不检测)
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
				println(getName());										//连接成功后发送终端名字 (name)
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
