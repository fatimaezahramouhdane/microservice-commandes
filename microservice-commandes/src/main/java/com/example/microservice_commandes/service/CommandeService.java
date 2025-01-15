package com.example.microservice_commandes.service;

import com.example.microservice_commandes.entity.Commande;
import com.example.microservice_commandes.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    // Récupérer toutes les commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // Récupérer une commande par ID
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    // Créer une nouvelle commande
    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    // Mettre à jour une commande existante
    public Commande updateCommande(Long id, Commande updatedCommande) {
        return commandeRepository.findById(id).map(existingCommande -> {
            existingCommande.setDescription(updatedCommande.getDescription());
            existingCommande.setQuantite(updatedCommande.getQuantite());
            existingCommande.setDate(updatedCommande.getDate());
            existingCommande.setMontant(updatedCommande.getMontant());
            return commandeRepository.save(existingCommande);
        }).orElseThrow(() -> new RuntimeException("Commande not found with id " + id));
    }

    // Supprimer une commande
    public void deleteCommande(Long id) {
        if (commandeRepository.existsById(id)) {
            commandeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Commande not found with id " + id);
        }
    }
}
