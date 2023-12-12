package org.java.spring.controller;

import java.util.List;

import org.java.spring.db.pojo.Ingredient;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.pojo.SpecialOffer;
import org.java.spring.db.serv.IngredientService;
import org.java.spring.db.serv.PizzaService;
import org.java.spring.db.serv.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


@Controller
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private SpecialOfferService specialOfferService;
	
	
	@GetMapping("/")
	public String getPizzas(Model model,
			@RequestParam(required = false) String search) {
		
		List<Pizza> pizza = search == null  
				? pizzaService.findAll()
				: pizzaService.findByName(search);
		
			model.addAttribute("pizza", pizza);
			model.addAttribute("search", search == null ? "" : search);

		return "index";
	}
	

	@GetMapping("/pizzas/{id}")
	public String getBook(Model model,
			@PathVariable int id) {

		Pizza pizza = pizzaService.findById(id);
		model.addAttribute("pizza", pizza);
		
		return "single";
	}
	
	@GetMapping("/pizzas/create")
	public String create(Model model)  {
		
		List<Ingredient> ings = ingredientService.findAll();
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredients", ings);
		
		return "create";
	}
	
	@PostMapping("/pizzas/create")
	public String store(
			@Valid @ModelAttribute Pizza formPizza,
			BindingResult bindingResult,
			Model model){
		
		int savedPizza;
		if(bindingResult.hasErrors()) {
		     model.addAttribute("pizza", formPizza);
		     System.out.println(formPizza);
			return "create";
			}
		
		try {
			pizzaService.save(formPizza);
			savedPizza = formPizza.getId();
		}catch (Exception e) {
			bindingResult.addError(new FieldError("book", "isbn", formPizza.getName(), false, null, null, "Name must be unique"));
			model.addAttribute("pizza", formPizza);
			return "create";
		}


		 return "redirect:/pizzas/" + savedPizza;
	}
	
	@GetMapping("/pizzas/edit/{id}")
	public String update(Model model, 
			@PathVariable int id) {
		
		List<Ingredient> ings = ingredientService.findAll();
		model.addAttribute("ingredients", ings);
		Pizza pizza = pizzaService.findById(id);
		model.addAttribute("pizza", pizza);
		
		return "create";
	}
	
	@PostMapping("/pizzas/edit/{id}")
	public String updatePizza(Model model,
			@Valid @ModelAttribute Pizza pizza,
			BindingResult bindingResult) {
		
		return store(pizza, bindingResult, model);
	}
	
	@PostMapping("/pizzas/delete/{id}")
	public String delete(@PathVariable int id) {
		
		Pizza pizza = pizzaService.findById(id);
		 if (pizza != null) {
			 pizza.clearIngredients();
			
			 pizzaService.save(pizza);
			 
			List<SpecialOffer> specialOffers = pizza.getSpecialOffers();
			specialOffers.forEach(specialOfferService::delete);
			 
		     pizzaService.delete(pizza);
		    }
		 
		return "redirect:/";
	}
}
