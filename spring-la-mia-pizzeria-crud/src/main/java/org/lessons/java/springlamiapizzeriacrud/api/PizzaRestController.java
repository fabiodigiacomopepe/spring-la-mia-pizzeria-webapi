package org.lessons.java.springlamiapizzeriacrud.api;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.service.IngredientService;
import org.lessons.java.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pizzas")
@CrossOrigin
public class PizzaRestController {
    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

    // Rotta "/api/v1/pizzas" (GET)
    @GetMapping
    public List<Pizza> index(@RequestParam Optional<String> search) {
        // Ritorno lista di pizze
        // Visto che search è opzionale, se c'è lo uso
        // altrimenti passo stringa vuota
        return pizzaService.getPizzaList(Optional.of(search.orElse("")));
    }

    // Rotta "/api/v1/ingredients" (GET)
    @GetMapping("/ingredients")
    public List<Ingredient> index() {
        // Ritorno lista di ingredienti
        return ingredientService.getAll();
    }

    // Rotta "/api/v1/pizzas/id" <---(dinamico) (GET)
    @GetMapping("/{id}")
    public Pizza details(@PathVariable Integer id) {
        try {
            // Provo a prendere pizza partendo da ID
            return pizzaService.getPizzaById(id);
        } catch (PizzaNotFoundException e) {
            // Se non ci riesco lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/api/v1/pizzas" (POST)
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        // Creo una nuova pizza passando in ingresso dati ricevuti
        // Valido il tutto
        // Uso @RequestBody per fare conversione da JSON -> Oggetto Java
        return pizzaService.createPizza(pizza);
    }

    // Rotta "/api/v1/pizzas/id" <---(dinamico) (PUT)
    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        // Setto per sicurezza l'ID della pizza uguale a ID in URL
        pizza.setId(id);
        try {
            // Provo a editare pizza
            return pizzaService.editPizza(pizza);
        } catch (PizzaNotFoundException e) {
            // Se non ci riesco (perchè non la trovo) lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/api/v1/pizzas/id" <---(dinamico) (DELETE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        try {
            // Provo a recuperare pizza partento da ID
            Pizza pizzaToDelete = pizzaService.getPizzaById(id);
            // Elimino pizza
            pizzaService.deletePizza(pizzaToDelete.getId());
        } catch (PizzaNotFoundException e) {
            // Se non la trovo lancio eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

