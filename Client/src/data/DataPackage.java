package data;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class DataPackage {
	public static final String DevicesList = "DevicesList";
	//[{sensor_number=000001, sensor_oth=oth1}, {sensor_number=000002, sensor_oth=oth2}]
	public static final String DeviceData = "DeviceData";
	//[{sensor_number=000001, oth=oth}]
	
	private List<String> number = new ArrayList<>();
	private List<String> oth = new ArrayList<>();
	private String type = null;
	
	public DataPackage(String dataType){
		type = dataType;
	}
	DataPackage(List list,String dataType){
		type = dataType;
		setData(list.toString());
	}
	
	public void setData(String data){
		String regex = "[=]([^,\\}]*)[,\\}]";
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(data);
		int i = 0;
		while(m.find()){
			if(i == 0){
				number.add(m.group(1));
				i++;
			}else{
				oth.add(m.group(1));
				i--;
			}
		}
		
	}
	public String toString(){
		String str = "";
		for(int i = 0;i < number.size();i++){
			str += "{sensor_number=" + number.get(i) + ", sensor_oth=" + oth.get(i) + "}";
			if(i+1 != number.size()){
				str += ", ";
			}
		}
		return str;
	}
	public List get(int index){						//区分后oth有区别  需要更改
		if(index <= number.size()){
			List list = new ArrayList();
			list.add(number.get(index));
			list.add(oth.get(index));
			return list;
		}else{
			return null;
		}
	}
	
	public List getData(){
		return number;
	}
	public List getOth(){
		return oth;
	}
	public String getType(){
		return type;
	}
	public boolean isEmpty(){
		return number.isEmpty();
	}
	
}
