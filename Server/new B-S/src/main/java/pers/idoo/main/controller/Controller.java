package pers.idoo.main.controller;

import org.springframework.web.bind.annotation.*;
import pers.idoo.dao.JDBC;
import pers.idoo.jrserver.*;
import pers.idoo.pojo.*;

@RestController
@RequestMapping(value="/uwbmwr")
public class Controller  {
	@RequestMapping(value="/isAlive",method = RequestMethod.GET)
	public String isAlive() {
		return "serverStatus:ServerIsOnline";
	}
}
