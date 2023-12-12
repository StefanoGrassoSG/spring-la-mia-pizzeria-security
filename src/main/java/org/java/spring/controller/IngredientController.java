package org.java.spring.controller;

import java.util.List;

import org.java.spring.db.pojo.Ingredient;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.repo.IngredientRepository;
import org.java.spring.db.serv.IngredientService;
import org.java.spring.db.serv.PizzaService;
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
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping("/ingredients")
	public String getIngredients(Model model) {
		
		List<Ingredient> ing = ingredientService.findAll();
		model.addAttribute("ingredient", ing);

		return "ingredients";
	}
	
	@GetMapping("/ingredients/create")
	public String createIngredient(Model model) {
		
		model.addAttribute("ingredient", new Ingredient());
		
		return "ingredient-form";
	}
	
	@PostMapping("/ingredients/create")
	public String store(
			@Valid @ModelAttribute Ingredient ingredient,
			BindingResult bindingResult,
			Model model){

		if(bindingResult.hasErrors()) {
		     model.addAttribute("ingredient", ingredient);
			return "ingredient-form";
			}
		
		try {
			ingredientService.save(ingredient);
		}catch (Exception e) {
			bindingResult.addError(new FieldError("ingredient", "name", ingredient.getName(), false, null, null, "Name must be unique"));
			model.addAttribute("pizza", ingredient);
			return "create";
		}


		 return "redirect:/ingredients";
	}
	
	@PostMapping("/ingredients/delete/{id}")
	public String delete(@PathVariable int id) {
		
		Ingredient ing = ingredientService.findById(id);
		ing.clearPizzas();
		System.out.println("pizze" + ing.getPizzas());
		ingredientService.save(ing);
		
		ingredientService.delete(ing);
		return "redirect:/ingredients";
	}
}
