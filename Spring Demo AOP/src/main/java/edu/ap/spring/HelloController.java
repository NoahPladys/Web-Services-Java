package edu.ap.spring;

import java.time.LocalTime;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.aop.Interceptable;
import edu.ap.spring.jpa.*;

@Controller
@Scope("session")
public class HelloController {

    private String name;
    @Autowired
    private PersonRepository repository;

    public HelloController() {
        this.name = "Stranger";
    }

    @RequestMapping("/hello")
    @Interceptable
    public ResponseEntity<String> getHello() {

        try {
            Person savedPerson = this.repository.findByName(this.name);
            this.name = savedPerson.getName();
        } 
        catch (Exception ex) {}

        return new ResponseEntity<String>("Hello " + this.name, HttpStatus.OK);
    }

    @RequestMapping("/helloTemplate")
    public String getHelloTemplate(Model model) {

        try {
            Person savedPerson = this.repository.findByName(this.name);
            this.name = savedPerson.getName();
        } 
        catch (Exception ex) {}

        model.addAttribute("name", this.name);

        return "helloTemplate";
    }

    @RequestMapping("/all")
    public ResponseEntity<String> getAll() {

        String all = "<b>All Persons</b><br/><br/>";

        try {
            Iterable<Person> itr = this.repository.findAll();
            for (Person p : itr) {
                all += p.getName() + "<br/>";
            }
        } 
        catch (Exception ex) {}

        return new ResponseEntity<String>(all, HttpStatus.OK);
    }

    @RequestMapping("/addForm")
    public String getAddFrom() {
        return "addForm";
    }

    @PostMapping("/add")
    public String add(@RequestParam("name") String name, HttpServletResponse response) {

        this.name = name.trim();
        LocalTime localTime = LocalTime.now();
        response.addCookie(new Cookie("remote", "" + localTime.getMinute()));

        try {
            Person newPerson = new Person(this.name);
            this.repository.save(newPerson);
        } 
        catch (Exception ex) {}

        return "redirect:hello";
    }

    @RequestMapping("/cookie")
    public ResponseEntity<String> getCookie(@CookieValue(value = "remote", defaultValue = "Unknown") String remote) {

        return new ResponseEntity<String>("Cookie : " + remote, HttpStatus.OK);
    }
}