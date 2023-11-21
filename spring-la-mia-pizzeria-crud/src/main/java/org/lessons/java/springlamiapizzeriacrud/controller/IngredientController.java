package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.exceptions.IngredientNameUniqueException;
import org.lessons.java.springlamiapizzeriacrud.exceptions.IngredientNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    // Rotta "/ingredients" (GET - AL CARICAMENTO DELLA PAGINA DA NAVBAR)
    @GetMapping
    public String index(Model model) {
        // Passo al model la lista degl ingredienti per popolare pagina
        model.addAttribute("ingredientList", ingredientService.getAll());
        // Passo al model anche un nuovo ingrediente per la create
        model.addAttribute("ingredientObj", new Ingredient());
        return "ingredients/list";
    }

    // Rotta "/ingredients" (POST - QUANDO SI CREA INGREDIENTE)
    @PostMapping
    public String create(@Valid @ModelAttribute("ingredientObj") Ingredient formIngredient, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Se ci sono errori
        if (bindingResult.hasErrors()) {
            // Ricarico la pagina
            // Passo anche la lista sempre per ripopolare pagina
            model.addAttribute("ingredientList", ingredientService.getAll());
        }
        try {
            // Provo a salvare ingredienti con dati presi da form
            ingredientService.save(formIngredient);
            redirectAttributes.addFlashAttribute("message", "Ingrediente '" + formIngredient.getName() + "' creato correttamente!");
            return "redirect:/ingredients";
        } catch (IngredientNameUniqueException e) {
            // Se non ci riesco (perchè già esiste) ricarico la pagina e ritorno un messaggio
            redirectAttributes.addFlashAttribute("messageFailed", "L'ingrediente " + formIngredient.getName() + "' esiste già");
            return "redirect:/ingredients";
        }
    }

    // Rotta "/ingredients/delete/id <---(dinamico)" (POST)
//    @PostMapping("/delete/{id}")
//    // Parametri in ingresso:
//    // @PathVariable Integer id -> per gestire quale elemento eliminare
//    // RedirectAttributes redirectAttributes -> attributi che ci sono solo nel redirect
//    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
//        try {
//            // Provo a prendere ingrediente in base a id
//            Ingredient ingredientToDelete = ingredientService.getIngredientById(id);
//            // Elimino ingrediente per id
//            ingredientService.deleteIngredient(id);
//            // Passo il messaggio durante il redirect
//            redirectAttributes.addFlashAttribute("message", "Ingrediente '" + ingredientToDelete.getName() + "' eliminato correttamente!");
//            return "redirect:/ingredients";
//        } catch (IngredientNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }

    // Rotta "/ingredients/delete/id <---(dinamico)" (POST)
    @PostMapping("/delete/{id}")
    // Parametri in ingresso:
    // @PathVariable Integer id -> per gestire quale elemento eliminare
    // RedirectAttributes redirectAttributes -> attributi che ci sono solo nel redirect
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Provo a prendere ingrediente in base a id
            Ingredient ingredientToDelete = ingredientService.getIngredientById(id);
            // Elimino prima relazione e poi ingrediente
            ingredientService.deleteIngredientWithAssociations(id);
            // Passo il messaggio durante il redirect
            redirectAttributes.addFlashAttribute("message", "Ingrediente '" + ingredientToDelete.getName() + "' eliminato correttamente!");
            return "redirect:/ingredients";
        } catch (IngredientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
