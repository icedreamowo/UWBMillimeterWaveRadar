package pers.idoo.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceList {
	private List<String> deviceList = new ArrayList<>();
	private List<String> deviceListMsg = new ArrayList<>();
	public DeviceList(List list){
		setData(list.toString());
	}
	public void setData(String data){
		String regex = "[=]([^,\\}]*)[,\\}]";
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(data);
		int i = 0;
		while(m.find()){
			if(i == 0){
				deviceList.add(m.group(1));
				i++;
			}else{
				deviceListMsg.add(m.group(1));
				i--;
			}
		}
		
	}
	public List get(int index){
		if(index <= deviceList.size()){
			List list = new ArrayList();
			list.add(deviceList.get(index));
			list.add(deviceListMsg.get(index));
			return list;
		}else{
			return null;
		}
	}
	public List<String> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<String> deviceList) {
		this.deviceList = deviceList;
	}
	public List<String> getDeviceListMsg() {
		return deviceListMsg;
	}
	public void setDeviceListMsg(List<String> deviceListMsg) {
		this.deviceListMsg = deviceListMsg;
	}
	

}
