/*�������Ӵ�����*/
/*ʹ��UDP����*/package pers.idoo.jrserver;
import java.net.*;

import pers.idoo.dao.JDBC;
import pers.idoo.pojo.Distance;

public class JRServer {
	private static final String moduleName = "JRServer";				//�˹���ģ�������
	private String moudleStatus(){
		String result = "[" + Method.getTime() + " " + moduleName + "]";
		return result;
	}
	
	private oneServer server= null;
	private JDBC db = null;
	private int dataSize = 2048;										//��Ҫ���ý������ݴ�С
	
	public JRServer(int port){
		server = new oneServer(port);
		db = new JDBC(Config.getDbAdress(),Config.getDbName(),Config.getDbPassword());
	}
	public void start(){
		try{
			System.out.println(moudleStatus() + "JRServer online(using port:" + server.port + ")");
			server.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void close(){
		try{
			server.close();
			System.out.println(moudleStatus() + "JRServer offline");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//阻塞型 自检    确认UDP服务器是否启动
	public void checkSelf(String selfIP,int port) {
		DatagramSocket socket = null;
		DatagramPacket request = null;
		InetAddress host = null;
		String str = null;
		try{
			socket = new DatagramSocket(0);
			socket.setSoTimeout(10000);
			host = InetAddress.getByName(selfIP);
			str = "Self Check From checkSelf()...";
			request = new DatagramPacket(str.getBytes(),str.getBytes().length, host, port);
			socket.send(request);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(socket != null) socket.close();
				socket = null;
				request = null;
				host = null;
				str = null;
		}
	}
	
	class oneServer extends Thread{
		private int port = 8001;
		private DatagramSocket server = null;
		private DatagramPacket packet = null;
		private byte[] data = null;
		private JDBCMovement dbm =null;
		
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
					
					byte[] newData = new byte[packet.getLength()];
					System.arraycopy(data,0,newData,0,packet.getLength());
					
					dbm = new JDBCMovement(newData);
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
		private String sequenceNumber = null;
		private int distanceNumber = 0;
		private int amplitudeNumber = 0;
		private Distance distanceData = null;
		
		JDBCMovement(){}
		JDBCMovement(byte[] data){
			this.data = data;
		}
		public void run(){
			String hexStr = bytes2HexStr(data);
			
			distanceData = extractData(hexStr);
			if(!distanceData.isEmpty()) {
				dataBaseMovement(distanceData);
			}
			if(Config.isPrintLogOn()){
				System.out.println(moudleStatus() + "FullDataString: " + hexStr);
				System.out.println(moudleStatus() + "distance:" + distanceData.getDistances() + " amplitude:" + distanceData.getAmplitudes());
			}
		}
		
		private String bytes2HexStr(byte[] byteArr) {
	        if (null == byteArr || byteArr.length < 1) return "";
	        StringBuilder sb = new StringBuilder();
	        for (byte t : byteArr) {
	            if ((t & 0xF0) == 0) sb.append("0");
	            sb.append(Integer.toHexString(t & 0xFF));
	        }
	        return sb.toString();
	    }
		
		private Distance extractData(String rawData) {
			//data:0000100019901960
			boolean isLegal = true;
			Distance newDistance = new Distance();
			newDistance.set(0);
			if(rawData.length() != 16) isLegal = false;
			if(isLegal) {
				try {
					sequenceNumber = rawData.substring(0, 5);
					distanceNumber = Integer.parseInt(rawData.substring(6, 10));
					amplitudeNumber = Integer.parseInt(rawData.substring(11, 15));
					
					newDistance.set(sequenceNumber,
									0,0,0,
									distanceNumber+"",
									amplitudeNumber+"",
									System.currentTimeMillis(),
									0,0
									);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return newDistance;
		}
		
		private void dataBaseMovement(Distance distanceData) {
			db.insert(distanceData.toInsertString("distance"));
			//Method.RetToDistanceData(d2,db.selectExistReturnRet("distance_n", "sequence_number", "1"));
			db.update(distanceData.toUpdateString("distance_n"));
			System.out.println(distanceData.toUpdateString("distance_n"));
			//SET SQL_SAFE_UPDATES = 0;
			//update distance_n set sequence_number =00001,reflection_count =0,actual_start=0.0,actual_end=0.0,distances=200,amplitudes=1400,time_stamp=1557490081412,marks=0,type=0 where sequence_number =00001;
		}
	}
}
