package org.java.spring.controller;

import java.util.List;

import org.java.spring.DTO.PizzasOffersDTO;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.pojo.SpecialOffer;
import org.java.spring.db.serv.PizzaService;
import org.java.spring.db.serv.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class SpecialController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private SpecialOfferService specialOfferService;
	
	@GetMapping("/pizzas/special")
	public String getSpecialForm(Model model) {
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("specialOffer", new SpecialOffer());
		return "special-form";
		
	}
	
	@GetMapping("/pizzas/special/edit/{id}")
	public String update(Model model, 
			@PathVariable int id) {
		SpecialOffer specialOffer = specialOfferService.findById(id);
		List<Pizza> pizzas = pizzaService.findAll();
		Pizza pizza = pizzaService.findById(specialOffer.getPizza().getId());
		model.addAttribute("specialOffer", specialOffer);
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("pizza", pizza);
		
		return "special-form";
	}
	
	@PostMapping("/pizzas/special/edit/{id}")
	public String updatePizza(
			@Valid @ModelAttribute SpecialOffer specialOffer,
			BindingResult bindingResult,
			PizzasOffersDTO pizzasOffersDTO,
			Model model, @PathVariable int id) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("specialOffer", specialOffer);
			return "special-form";
			}
		
		SpecialOffer spc = specialOfferService.findById(id);
		spc.setTitle(pizzasOffersDTO.getTitle());
		spc.setStartDate(pizzasOffersDTO.getStartDate());
		spc.setEndDate(pizzasOffersDTO.getEndDate());
		specialOfferService.save(spc);
		
		
		return ("redirect:/");
	}
	
	@PostMapping("/pizzas/special")
	public String storeOffers(
			@Valid @ModelAttribute SpecialOffer specialOffer,
			BindingResult bindingResult,
			PizzasOffersDTO pizzasOffersDTO,
			Model model
		) {
		
		Pizza pizza = pizzaService.findById(pizzasOffersDTO.getPizza_id());
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		
		
		if(bindingResult.hasErrors()) {
		     System.out.println(bindingResult);
			model.addAttribute("specialOffer", specialOffer);
			System.out.println("vediamo: " + specialOffer);
			return "special-form";
			}
		
		SpecialOffer special = new SpecialOffer(
				pizzasOffersDTO.getTitle(),
				pizzasOffersDTO.getStartDate(),
				pizzasOffersDTO.getEndDate(),
				pizza);
		
		specialOfferService.save(special);
		
		return "redirect:/";
	}
}
