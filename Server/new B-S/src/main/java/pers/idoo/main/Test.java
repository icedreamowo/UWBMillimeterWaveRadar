package pers.idoo.main;

import org.springframework.boot.*;

import pers.idoo.dao.JDBC;
import pers.idoo.jrserver.Config;

@SpringBootConfiguration
public class Test {
	public static void main(String[] args) {
		String str = "SET SQL_SAFE_UPDATES = 0;update distance_n set sequence_number =00001,reflection_count =0,actual_start=0.0,actual_end=0.0,distances=200,amplitudes=1400,time_stamp=1557490081412,marks=0,type=0 where sequence_number =00001;";
		JDBC db = new JDBC(Config.getDbAdress(),Config.getDbName(),Config.getDbPassword());
		db.update(str);
	}
}