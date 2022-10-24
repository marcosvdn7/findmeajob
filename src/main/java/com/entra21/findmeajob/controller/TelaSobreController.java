package com.entra21.findmeajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class TelaSobreController {
	
	@GetMapping("/sobre")
	public String sobre() {
		return "usuario/sobre";
	}

}
