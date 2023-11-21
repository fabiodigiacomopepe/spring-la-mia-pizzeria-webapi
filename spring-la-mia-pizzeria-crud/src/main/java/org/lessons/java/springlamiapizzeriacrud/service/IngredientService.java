package org.lessons.java.springlamiapizzeriacrud.service;

import jakarta.transaction.Transactional;
import org.lessons.java.springlamiapizzeriacrud.exceptions.IngredientNameUniqueException;
import org.lessons.java.springlamiapizzeriacrud.exceptions.IngredientNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.IngredientRepository;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    // Ritorno lista di tutti gli ingredienti
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    // Salvo ingrediente
    public Ingredient save(Ingredient ingredient) throws IngredientNameUniqueException {
        // Verifico che ingrediente non è duplicato
        if (ingredientRepository.existsByName(ingredient.getName())) {
            // Se lo, è lancio eccezione passando il nome dell'ingrediente
            throw new IngredientNameUniqueException(ingredient.getName());
        }
        // Trasformo il nome in lowercase
        ingredient.setName(ingredient.getName().toLowerCase());
        return ingredientRepository.save(ingredient);
    }

    public Ingredient getIngredientById(Integer id) throws IngredientNotFoundException {
        // Salvo in result in modo Optional perchè potrebbe non ritornare nulla
        Optional<Ingredient> result = ingredientRepository.findById(id);
        // Se il risultato è presente
        if (result.isPresent()) {
            // Ritorno l'ingrediente (result.get())
            return result.get();
        } else {
            // Altrimenti lancio eccezione
            throw new IngredientNotFoundException("Ingredient with ID " + id + ": Not Found");
        }
    }

//    public void deleteIngredient(Integer id) {
//        ingredientRepository.deleteById(id);
//    }

    @Transactional
    public void deleteIngredientWithAssociations(Integer id) {
        // Recupero l'ingrediente tramite ID
        Ingredient ingredientToDelete = ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException("Ingredient with ID " + id + ": Not Found"));
        // Rimuovo l'associazione tra ingrediente e pizza
        // Ciclo su ogni oggetto pizza e rimuovo l'ingrediente
        for (Pizza pizza : pizzaRepository.findAll()) {
            pizza.getIngredients().remove(ingredientToDelete);
        }
        // Elimino l'ingrediento
        ingredientRepository.delete(ingredientToDelete);
    }
}
