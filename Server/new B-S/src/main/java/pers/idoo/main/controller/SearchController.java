package pers.idoo.main.controller;

import org.springframework.web.bind.annotation.*;

import pers.idoo.dao.JDBC;
import pers.idoo.jrserver.*;
import pers.idoo.pojo.*;

@RestController
@RequestMapping(value="/uwbmwr/search")
public class SearchController  {
	private JDBC db = null;
	public void init() {
		db = new JDBC(Config.getDbAdress(),Config.getDbName(),Config.getDbPassword());
	}
	@RequestMapping(value="/getDevicelist",method = RequestMethod.GET)
	public DeviceList getDevicelist() {
		if(db == null) {init();}		//初始化
		DeviceList result = new DeviceList(db.selectAll("sensor_list"));
		return result;
	}
	
	@RequestMapping(value="/getDevicedata",method = RequestMethod.GET)
	public Distance getDevicedata(@RequestParam("sn") String sensorNumber) {
		if(db == null) {init();}
		Distance distance = new Distance();
		Method.RetToDistanceData(distance, db.selectExistReturnRet("distance_n","sequence_number",sensorNumber));
		return distance;
	}
	
	@RequestMapping(value="/getHistorydata",method = RequestMethod.GET)
	public String getHistorydata(@RequestParam("from") String startTimeStamp
								,@RequestParam("to") String endTimeStamp) {
		if(startTimeStamp == null||endTimeStamp == null) {return null;}
		if(db == null) {init();}
		
		return null;
	}
}
