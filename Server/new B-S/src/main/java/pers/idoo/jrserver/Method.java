package pers.idoo.jrserver;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import pers.idoo.pojo.*;

public class Method {
	public static void RetToDistanceData(Distance data,ResultSet ret,int num){			//ResultSet���ϵ�Distance�� 
		try{
			ret.first();
			for(int i = 1;i < num;i++)
				ret.next();
			data.set(ret.getString(1), ret.getInt(2), ret.getFloat(3), ret.getFloat(4), ret.getString(5), ret.getString(6), ret.getLong(7), ret.getInt(8), ret.getInt(9));
			ret.first();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void RetToDistanceData(Distance data,ResultSet ret){
		RetToDistanceData(data,ret,1);
	}
	
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
		return getCTMToDateTime(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
	}
	public static String getDate() {
		return getCTMToDateTime(System.currentTimeMillis(),"yyyy-MM-dd");
	}
	public static String getCTMToDateTime(long currentTime,String formatString){
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		Date date = new Date(currentTime);
		return formatter.format(date);
	}
}
