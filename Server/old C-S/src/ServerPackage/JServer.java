/*用于连接Java界面终端*/
/*使用TCP连接*/
package ServerPackage;
import java.io.*;
import java.net.*;

public class JServer {
	/*用于客户端命令*/
	private static final String MsgSuccess = "Success";
	private static final String MsgFail = "Fail";
	private static final String ClientOffline = "ClientOffline";
	private static final String SearchDevicesList = "SearchDevicesList";
	private static final String SearchDeviceData = "SearchDeviceData";
	private static final String AddDeviceName = "AddDeviceName";
	/*用于客户端状态*/
	private static final String online = "online";
	private static final String offline = "offline";
	private static final String error = "error";
	private oneServer server= null;
	private JDBC db = null;
	private int connectedMarks = 0;
	private String serverName = "JServer";
	
	JServer(int port){
		server = new oneServer(port);
		db = new JDBC(Method.dbAdress,Method.dbName,Method.dbPassword);
	}
	public void start(){
		try{
			System.out.println(serverStatus() + "JServer online(using port:" + server.port + ")");
			server.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close(){
		try{
			server.close();
			System.out.println(serverStatus() + "JServer offline");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private String serverStatus(){
		String result = "[" + Method.getTime() + " " + serverName + "]";
		return result;
	}
	
	class oneServer extends Thread{
		private int port = 8000;
		private ServerSocket server = null;
		
		oneServer(){}
		oneServer(int port){
			this.port = port;
		}
		public void setPort(int port){
			this.port = port;
		}
		public void run(){
			try{
				server = new ServerSocket(port);
				while(true){
					SocketThread socketThread = new SocketThread(server.accept());
					socketThread.start();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void close() throws Exception{
			server.close();
		}
		
	}
	
	class SocketThread extends Thread{
		private Socket socket = null;
		private PrintWriter writer = null;
		private BufferedReader reader = null;
		private String sensorNumber = null;			//记录传感器编号
		private String clientName = null;				//记录终端用户名
		private String operate = null;					//记录命令
		private boolean onlineFlag = true;				//记录记录是否已接收终端关闭指令
		
		SocketThread(Socket socket){
			this.socket = socket;
		}
		public void run(){
			try{
				connectedMarks++;			//标记已连接终端数
				writer = new PrintWriter(socket.getOutputStream());
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				readName();					//链接后读取终端名字(这里有一次接收)
				printInf(online);
				/*以下为链接后具体操作*/
				
				while(onlineFlag){			//在接收到结束命令前一直循环
					operate = read();		//读取命令
					switch(operate){
						case SearchDevicesList:			//查询传感器编号列表
							DataPackage result = new DataPackage(db.selectAll("sensor_list"),DataPackage.DevicesList);
							if(!result.isEmpty()){
								println(MsgSuccess);
								println(result.toString());
							}else{
								println(MsgFail);
							}
							break;
						case SearchDeviceData:			//查询指定传感器数据
							sensorNumber = read();
							Distance distance = new Distance();
							Method.RetToDistanceData(distance, db.selectExistReturnRet("distance_n","sequence_number",sensorNumber));
							
							if(!(distance.getSequence_number() == 0)){
								println(MsgSuccess);
								println(distance.toString());
							}else{
								println(MsgFail);
							}
							break;
						case AddDeviceName:				//添加传感器编号(暂缓)
							sensorNumber = read();
							
							
							println(MsgSuccess);
						case ClientOffline:				//终端关闭命令
							onlineFlag = false;
							break;
					}
				}
	
				/*以上为链接后具体操作*/
				connectedMarks--;
				printInf(offline);
				close();
			}catch(Exception e){
				connectedMarks--;
				e.printStackTrace();
				printInf(error);
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
		private void printInf(String Situation){			//根据参数输出信息
		String connectedInf = serverStatus() + "Now Connecting:" + connectedMarks + ",";
			switch(Situation){
				case online:
					System.out.println(connectedInf + clientName + " online");
					break;
				case offline:
					System.out.println(connectedInf + clientName + " offline");
					break;
				case error:
					System.out.println(connectedInf + "an error has occurred with " + clientName);
					break;
			}
		}
		public void readName() throws Exception{
			clientName = read();
		}
		public void close() throws Exception{
			socket.close();
			writer.close();
			reader.close();
		}
		
	}
}
