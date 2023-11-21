package org.lessons.java.springlamiapizzeriacrud.service;

import org.lessons.java.springlamiapizzeriacrud.exceptions.OfferNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.java.springlamiapizzeriacrud.model.Offer;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.OfferRepository;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OfferService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;

    // Prendo come paramentro in ingresso l'id della pizza passato dal Controller,
    // preso a sua volta da url richiesta (?pizzaId=X)
    public Offer createNewOffer(Integer pizzaId) throws PizzaNotFoundException {
        // Prova a recuperare pizza con ID
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new PizzaNotFoundException("Pizza with ID " + pizzaId + ": Not Found"));
        // Creo una nuova offerta
        Offer offer = new Offer();
        // Setto data inizio a oggi
        // e data di fine fra un mese a oggi
        offer.setStartDate(LocalDate.now());
        offer.setEndDate(LocalDate.now().plusMonths(1));
        // Collego la relazione offerta/pizza passando la pizza recuperata tramite id
        offer.setPizza(pizza);
        return offer;
    }

    public Offer saveOffer(Offer offer) {
        // Salvo l'offerta
        return offerRepository.save(offer);
    }

    public Offer getOffer(Integer id) throws OfferNotFoundException {
        // Provo a recuperare offerta da ID
        // Se non ci riesco lancio eccezione
        return offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException("Offer with ID " + id + ": Not Found"));
    }
}
