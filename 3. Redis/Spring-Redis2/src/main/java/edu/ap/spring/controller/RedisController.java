package edu.ap.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ap.spring.redis.RedisService;

@Controller
public class RedisController {

   @Autowired
   private RedisService service;
     
   @GetMapping("/")
   public String index() {
      return "redirect:/movies";
   }

   @PostMapping("/movies")
   public String postMessage(@RequestParam("name") String name,
   							@RequestParam("year") String year,
							@RequestParam("actors") String actors) {
	  
	  String key = "movies:" + year + ":" + name;
	  String[] parts = actors.split(",");
	  for(String actor : parts) {
		this.service.rpush(key, actor);
	  }
	  this.service.incr("moviescount");
	  return "redirect:/movies";
   }

   @GetMapping("/form")
   public String messageForm() {	   
	   return "movieForm";
   }
   
   @GetMapping("/movies")
   public String movies(Model model) {

	   ArrayList<String> movieList = new ArrayList<String>();
	   Set<String> movies = service.keys("movies:*");
	   for(String m : movies) {
		String movie = "";

		List<String> actors = service.getList(m);
		// get all parts of the key, eg : ["movies", "1998", "The Big Lebowski"]
		String[] parts = m.split(":");
		   
		movie += parts[2] + " (" + parts[1] + ") ";
		movie += "Actors : ";
		// iterate over actors
		for(String a : actors) {
			 movie += a + ", ";
		}
		// strip off last ', '
		movie = movie.substring(0, movie.length() - 2);
		movieList.add(movie);
	   }

	   model.addAttribute("moviescount", service.getKey("moviescount"));
	   model.addAttribute("movies", movieList);
	   
	   return "movies";
   }
 }
