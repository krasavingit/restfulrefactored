package ru.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage() {
		return "/login.html";
	}

}
