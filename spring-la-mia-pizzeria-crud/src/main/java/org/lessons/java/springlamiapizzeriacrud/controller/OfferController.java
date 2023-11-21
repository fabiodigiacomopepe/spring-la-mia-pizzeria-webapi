package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.exceptions.OfferNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.model.Offer;
import org.lessons.java.springlamiapizzeriacrud.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    // Rotta "/offers/create" (GET)
    @GetMapping("/create")
    // Prendo l'id della pizza passato tramite url (?pizzaId=X)
    public String createGet(@RequestParam Integer pizzaId, Model model) {
        try {
            // Provo a creare una nuova offerta passando l'id della pizza
            model.addAttribute("offer", offerService.createNewOffer(pizzaId));
            return "offers/create_update";
        } catch (PizzaNotFoundException e) {
            // Altrimenti lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/offers/create" (POST)
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        // Se ci sono errori
        if (bindingResult.hasErrors()) {
            // Ricarico la pagina
            // i dati non si perdono perchè ho usato @ModelAttribute("offer")
            return "offers/create_update";
        }
        // Salvo l'offerta passando i dati ricevuti dal form
        offerService.saveOffer(formOffer);
        return "redirect:/pizzas/show/" + formOffer.getPizza().getId();
    }

    // Rotta "/offers/edit/id <---(dinamico)" (GET)
    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable Integer id, Model model) {
        try {
            // Provo a recuperare offerta da ID
            Offer offer = offerService.getOffer(id);
            model.addAttribute("offer", offer);
            return "offers/create_update";
        } catch (OfferNotFoundException e) {
            // Se non ci riesco lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/offers/edit/id <---(dinamico)" (POST)
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id, @Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        // Se ci sono errori
        if (bindingResult.hasErrors()) {
            // Ricarico la pagina
            // i dati non si perdono perchè ho usato @ModelAttribute("offer")
            return "offers/create_update";
        }
        // Salvo l'offerta passando i dati ricevuti dal form
        offerService.saveOffer(formOffer);
        return "redirect:/pizzas/show/" + formOffer.getPizza().getId();
    }
}
