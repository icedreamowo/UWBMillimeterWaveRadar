package pers.idoo.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value="/error",method = RequestMethod.GET)
public class ErrorController {
	public String returnErrorPage() {
		return "This is 404 Page!";
	}
}
