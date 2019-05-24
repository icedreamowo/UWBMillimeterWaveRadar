package pers.idoo.jrserver;

import pers.idoo.jrserver.Config;

//Test moudle
//����ģ��
public class Test {
	public static void main(String[] args){
		System.out.println("Test moudle!");
		Config.loadConfigFile("config.json");
	}
}