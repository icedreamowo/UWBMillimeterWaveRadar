package data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

public class Method {
	public static final String dbAdress = "jdbc:mysql://127.0.0.1/data_t1?useSSL=true";
	public static final String dbName = "root";
	public static final String dbPassword = "";
	
	public static void RetToDistanceData(Distance data,ResultSet ret,int num){			//ResultSet���ϵ�Distance�� 
		try{
			ret.first();
			for(int i = 1;i < num;i++)
				ret.next();
			data.set(ret.getInt(1), ret.getInt(2), ret.getFloat(3), ret.getFloat(4), ret.getString(5), ret.getString(6), ret.getLong(7), ret.getInt(8), ret.getInt(9));
			ret.first();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void RetToDistanceData(Distance data,ResultSet ret){
		RetToDistanceData(data,ret,1);
	}
	
	public static void RetToEnvelopeData(Envelope data,ResultSet ret,int num){			//ResultSet���ϵ�Envelope�� 
		try{
			ret.first();
			for(int i = 1;i < num;i++)
				ret.next();
			data.set(ret.getInt(1), ret.getInt(2), ret.getFloat(3), ret.getFloat(4), ret.getString(5), ret.getLong(6), ret.getInt(7), ret.getInt(8));
			ret.first();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void RetToEnvelopeData(Envelope data,ResultSet ret){
		RetToEnvelopeData(data,ret,1);
	}
	/*
	public static void RetToExcel(ResultSet ret,int num,PoiManager poi){				//ResultSet��������Excel(ʹ��Poi)
		Envelope data = new Envelope();
		String line = null;
		try{
			ret.first();
			for(int order = 1;order <= 1818;order++){
				poi.addTitle("data" + order);
			}
			for(int n = 1;n <= num;n++){
				data.set(ret.getInt(1), ret.getInt(2), ret.getFloat(3), ret.getFloat(4), ret.getString(5), ret.getLong(6), ret.getInt(7), ret.getInt(8));
				poi.add(data.toJxlString("&"),false);
				line = data.getData().replaceAll("/"," ");
				for(int order = 0;order < 1818;order++){
					poi.add(line.substring(order*4, (order+1)*4),false);
				}
				poi.nextLine();
				ret.next();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	public static List RetToList(ResultSet ret){										//ResultSet���ϵ�List
		try{
			List list = new ArrayList();
			ResultSetMetaData md = ret.getMetaData();
			int columnCount = md.getColumnCount();
			while (ret.next()) {
				 Map rowData = new HashMap();
				 for (int i = 1; i <= columnCount; i++) {
					 rowData.put(md.getColumnName(i), ret.getObject(i));
				 }
				 list.add(rowData);
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static int ExtractInteger(String target){									//��ȡ��������
		String regex="[^0-9]";  
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(target);
		if(m.replaceAll("").trim().equals(""))
			return 0;
		return Integer.parseInt(m.replaceAll("").trim());
	}
	public static float ExtractFloat(String target){									//��ȡ����������
		String regex="[^0-9\\.]";  
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(target);
		if(m.replaceAll("").trim().equals(""))
			return 0;
		return Float.parseFloat((m.replaceAll("").trim()));
	}
	
	public static int IntegerLength(int num){											//����������ֳ���
		int len = 0,n = 1;
		for(;;n*=10,len++){
			if(num/n == 0)	return len;
		}
	}
	
	public static String getTime(){														//��ȡ��ǰʱ��
		String result = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result = format.format(new Date());
		return result;
	}
	public static String CTMToDateTime(long currentTime){							//currentTimeMillis()
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(currentTime);
		return formatter.format(date);
	}
}
