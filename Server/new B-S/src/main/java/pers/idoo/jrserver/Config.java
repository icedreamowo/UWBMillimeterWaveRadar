package pers.idoo.jrserver;

import java.io.FileReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

//use for loading config.json
public class Config {
	private static final String moduleName = "ConfigFileLoader";							//�˹���ģ�������
	private static String moudleStatus(){
		String result = "[" + Method.getTime() + " " + moduleName + "]";
		return result;
	}
	private static class SpecBoolean {														//����ʵ��boolean���������ô���
		public boolean v = false;
		public String s = null;
		SpecBoolean(){}
		SpecBoolean(boolean value,String str){v = value;s = str;}
		public String toString() {
			return String.valueOf(v);
		}
	}
	
	private static String dbAdress = "";
	private static String dbName = "";
	private static String dbPassword = "";
	private static SpecBoolean printLog = new SpecBoolean(true,"printLog");
	private static SpecBoolean printLogFile = new SpecBoolean(false,"printLogFile");		//TODO finish the "printLogFile"
	private static int refreshConfigTimeInteval = 0;
	private static int hexStringLongth = 0;
	
	private static LoadConfig loadConfig = null;

	public static void loadConfigFile(String fileURL){										//���������ļ�
		loadConfig = new LoadConfig(fileURL);
		loadConfig.initConfigOption();														//Config���Գ�ʼ��ʱ��������
		loadConfig.start();
	}
	private static class LoadConfig extends Thread{											//Threadʵʱ��������ļ��ĺ����޸�
		private JsonParser parse = new JsonParser();
		private JsonObject json = null;
		private String fileURL = null;
		private FileReader file = null;
		private long t1 = 0,t2 = 0;
		LoadConfig(String fileurl){fileURL = fileurl;}
		public void initConfigOption() {
			printInitOptionMsg();
			try{
				file = new FileReader(fileURL);
				json=(JsonObject)parse.parse(file);
				dbAdress = json.get("dbAdress").getAsString();
				dbName = json.get("dbName").getAsString();
				dbPassword = json.get("dbPassword").getAsString();
				refreshConfigTimeInteval = json.get("refreshConfigTimeInteval").getAsInt();
				hexStringLongth = json.get("hexStringLongth").getAsInt();
				
				BooleanWatcher(json,printLog);
				BooleanWatcher(json,printLogFile);
				file.close();
			}catch (Exception e){
				e.printStackTrace();
			}
			printConfigMsg(json);
			
		}
		public void run(){
			t1 = System.currentTimeMillis();
			t2 = t1;
			while(true){
				if(t2 - t1 > refreshConfigTimeInteval){
					try {
						file = new FileReader(fileURL);
						json = (JsonObject)parse.parse(file);
						
						refreshConfigTimeInteval = json.get("refreshConfigTimeInteval").getAsInt();
						
						BooleanWatcher(json,printLog);
						BooleanWatcher(json,printLogFile);
						
						file.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					t1 = System.currentTimeMillis();
					t2 = t1;
				}else{
					t2 = System.currentTimeMillis();
				}
			}
		}
	}
	private static void BooleanWatcher(JsonObject json ,SpecBoolean bool) {			//���Config�ļ��е�bool���ԵĹ淶�Լ���
		String booleanOption = json.get(bool.s).getAsString();
		if(booleanOption.equals("true") || booleanOption.equals("false")){
			if(json.get(bool.s).getAsBoolean() != bool.v){
				bool.v = !bool.v;
				printConfigMsg(json);
			}
		}else{
			printFailedLoadMsg("printLog");
		}
	}
	
	public static String getDbAdress() {
		return dbAdress;
	}
	public static String getDbName() {
		return dbName;
	}
	public static String getDbPassword() {
		return dbPassword;
	}
	public static boolean isPrintLogOn() {
		return printLog.v;
	}
	public static boolean isPrintLogFileOn() {
		return printLogFile.v;
	}
	public static int getHexStringLongth() {
		return hexStringLongth;
	}
	
	private static void printInitOptionMsg(){
		System.out.println("Initialize The Option...");
	}
	private static void printConfigMsg(JsonObject json){
		System.out.println(moudleStatus() + "New Config:" +json.toString());
	}
	private static void printFailedLoadMsg(String option){										//����̨��� ����ʧ�ܵ�ѡ��
		System.out.println(moudleStatus() + "Undifined Config Option:" + "\"" + option + "\"");
	}
}
