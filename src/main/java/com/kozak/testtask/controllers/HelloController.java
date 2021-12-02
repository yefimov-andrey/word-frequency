package com.kozak.testtask.controllers;

import com.kozak.testtask.services.MainService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {

	private static final MainService mainService = new MainService();

	@RequestMapping(value = "/analyze", method = RequestMethod.GET)
	public void perform() throws IOException {
		mainService.analyze();
	}

}
