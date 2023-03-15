package edu.ap.spring.redis.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ap.spring.redis.redis.RedisService;

@Controller
public class RedisController {

   private String CHANNEL = "edu:ap:redis";
   private ArrayList<String> redisMessages = new ArrayList<String>();
   @Autowired
   private RedisService service;
     
   @GetMapping("/")
   public String index() {
      return "redirect:/messages";
   }

   @PostMapping("/messages")
   public String postMessage(@RequestParam("message") String message) {
	  this.service.sendMessage(CHANNEL, message);
	  return "redirect:/messages";
   }

   @GetMapping("/form")
   public String messageForm() {	   
	   return "messageForm";
   }
   
   @GetMapping("/messages")
   public String messages(Model model) {
	   
	   model.addAttribute("messages", redisMessages);
	   
	   return "messages";
   }
 
   // messaging
   public void onMessage(String message) {
	   this.redisMessages.add(message);
   }
 }
