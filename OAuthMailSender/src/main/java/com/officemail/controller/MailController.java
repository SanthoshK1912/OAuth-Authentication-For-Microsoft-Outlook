package com.officemail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.officemail.service.MailService;

@RestController
@RequestMapping("outlook")
public class MailController {
	
	@Autowired	
	MailService mailService;
	
	@GetMapping("/send")
	public ResponseEntity<String> sendMail(){
		try {
		mailService.sendMail();
		return new ResponseEntity<String>("Mail Sended Succesfully ",HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
		return new ResponseEntity<String>("Failed to Send " + e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/read")
	public ResponseEntity<String> readMail(){
		try {
		mailService.readMail();
		return new ResponseEntity<String>("Mail Readed Succesfully ",HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
		return new ResponseEntity<String>("Failed to Read " + e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	

}
