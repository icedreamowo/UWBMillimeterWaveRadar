/*��������Java�����ն�*/
/*ʹ��TCP����*/
package ServerPackage;
import java.io.*;
import java.net.*;

public class JServer {
	/*���ڿͻ�������*/
	private static final String MsgSuccess = "Success";
	private static final String MsgFail = "Fail";
	private static final String ClientOffline = "ClientOffline";
	private static final String SearchDevicesList = "SearchDevicesList";
	private static final String SearchDeviceData = "SearchDeviceData";
	private static final String AddDeviceName = "AddDeviceName";
	/*���ڿͻ���״̬*/
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
		private String sensorNumber = null;			//��¼���������
		private String clientName = null;				//��¼�ն��û���
		private String operate = null;					//��¼����
		private boolean onlineFlag = true;				//��¼��¼�Ƿ��ѽ����ն˹ر�ָ��
		
		SocketThread(Socket socket){
			this.socket = socket;
		}
		public void run(){
			try{
				connectedMarks++;			//����������ն���
				writer = new PrintWriter(socket.getOutputStream());
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				readName();					//���Ӻ��ȡ�ն�����(������һ�ν���)
				printInf(online);
				/*����Ϊ���Ӻ�������*/
				
				while(onlineFlag){			//�ڽ��յ���������ǰһֱѭ��
					operate = read();		//��ȡ����
					switch(operate){
						case SearchDevicesList:			//��ѯ����������б�
							DataPackage result = new DataPackage(db.selectAll("sensor_list"),DataPackage.DevicesList);
							if(!result.isEmpty()){
								println(MsgSuccess);
								println(result.toString());
							}else{
								println(MsgFail);
							}
							break;
						case SearchDeviceData:			//��ѯָ������������
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
						case AddDeviceName:				//��Ӵ��������(�ݻ�)
							sensorNumber = read();
							
							
							println(MsgSuccess);
						case ClientOffline:				//�ն˹ر�����
							onlineFlag = false;
							break;
					}
				}
	
				/*����Ϊ���Ӻ�������*/
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
		private void printInf(String Situation){			//���ݲ��������Ϣ
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
