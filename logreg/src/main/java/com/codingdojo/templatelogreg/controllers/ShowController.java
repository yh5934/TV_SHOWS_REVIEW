package com.codingdojo.templatelogreg.controllers;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codingdojo.templatelogreg.models.Show;
import com.codingdojo.templatelogreg.services.ShowService;
import com.codingdojo.templatelogreg.services.UserService;

@Controller
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;
    private final UserService userService;
    
    public ShowController(ShowService showService, UserService userService) {
        this.showService = showService;
        this.userService = userService;
    }
    
    @GetMapping("/add") 
    public String addRoute(@ModelAttribute("show") Show show) {
    	return "add";
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("show") Show show, BindingResult result, Model model, HttpSession session) {
    	
    	
    	
    	
    	Show exists = showService.findTitle(show.getTitle());
    	if(result.hasErrors())  return "add";
    	if(exists != null) {
    		model.addAttribute("showError", "This title already exists");
    		return "add";
    	} else {
    		showService.createShow(show);
    		return "redirect:/users/dashboard";
    	}
    		
    		
}
    @GetMapping("/{id}")
    public String displayShow(@PathVariable("id") Long id, @ModelAttribute("rating") Show show, Model model) {
    	Show singleShow = showService.findShow(id);
    	model.addAttribute("shows", singleShow );
		model.addAttribute("users", userService.getShows());
    	return "show";
    }
    
    
 
    
}
    