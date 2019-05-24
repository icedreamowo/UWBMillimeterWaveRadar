/*�������Ӵ�����*/
/*ʹ��UDP����*/
package ServerPackage;
import java.net.*;
import java.util.*;

public class JRServer {
	private oneServer server= null;
	private JDBC db = null;
	private String serverName = "JRServer";
	private int dataSize = 2048;										//��Ҫ���ý������ݴ�С
	
	JRServer(int port){
		server = new oneServer(port);
		db = new JDBC(Method.dbAdress,Method.dbName,Method.dbPassword);
	}
	public void start(){
		try{
			System.out.println(serverStatus() + "JRServer online(using port:" + server.port + ")");
			server.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close(){
		try{
			server.close();
			System.out.println(serverStatus() + "JRServer offline");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private String serverStatus(){
		String result = "[" + Method.getTime() + " " + serverName + "]";
		return result;
	}
	
	class oneServer extends Thread{
		private int port = 8001;
		private DatagramSocket server = null;
		private DatagramPacket packet = null;
		private byte[] data = null;
		
		oneServer(int port){
			try{
				this.port = port;
				server = new DatagramSocket(port);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void run(){
			try{
				while(true){											//δ���ùرշ���������(�����ر�)
					data = new byte[dataSize];
					packet = new DatagramPacket(data, data.length);
				
					server.receive(packet);
					
					JDBCMovement dbm = new JDBCMovement(data);						//ʹ���߳�ִ��JDBC����,�ӿ�socket��ȡ
					dbm.start();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void close() throws Exception{
			server.close();
		}
	}
	
	class JDBCMovement extends Thread{
		private byte[] data = null;
		private int distance = 0;
		private int amplitude = 0;
		private Distance distanceData = new Distance();
		private Distance d2 = new Distance();
		
		JDBCMovement(){}
		JDBCMovement(byte[] data){
			this.data = data;
		}
		public void run(){
			/*����Ϊ�������ݺ�JDBC����*/
			String hexStr = bytes2HexStr(data);
			//distance = Integer.parseInt(hexStr.substring(8, 13));
			//411f1 ��ʼ��
			//amplitude = Integer.parseInt(hexStr.substring(13, 18));
			hexStr = hexStr.substring(0, 30);
			System.out.println(serverStatus() + "FullDataString: " +hexStr);
			//System.out.println(serverStatus() + "distance:" + distance + " amplitude:" + amplitude);
			//distanceData.set(000001, 0, 0, 0, distance+"", amplitude+"", System.currentTimeMillis(), 0, 0);
			
			//db.insert(distanceData.toInsertString("distance"));
			//Method.RetToDistanceData(d2,db.selectExistReturnRet("distance_n", "sequence_number", "1"));
			//db.update(distanceData.toUpdateString("distance_n") + d2.getTime_stamp() + "';");
			
//			System.out.println(serverStatus() + hexStr + "@" + hexStr.substring(8, 13) + "@" + hexStr.substring(13, 18));
			/*����Ϊ�������ݺ�JDBC����*/
		}
		
		private String bytes2HexStr(byte[] byteArr) {
	        if (null == byteArr || byteArr.length < 1) return "";
	        StringBuilder sb = new StringBuilder();
	        for (byte t : byteArr) {
	            if ((t & 0xF0) == 0) sb.append("0");
	            sb.append(Integer.toHexString(t & 0xFF));  //t & 0xFF ������Ϊȥ��Integer��λ����ķ���λ��java�������ò����ʾ��???
	        }
	        return sb.toString();
	    }
	}
}
