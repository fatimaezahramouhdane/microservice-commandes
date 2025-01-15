package com.example.microservice_commandes.Controller;

import com.example.microservice_commandes.Configuration.ApplicationPropertiesConfiguration;
import com.example.microservice_commandes.entity.Commande;
import com.example.microservice_commandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ApplicationPropertiesConfiguration appConfig; // Injection de la configuration

    // Endpoint : GET /commandes
    @GetMapping("/commandes")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();

        // Filtrage des commandes selon la valeur de commandesLast récupérée depuis la configuration
        LocalDate currentDate = LocalDate.now();
        List<Commande> filteredCommandes = commandes.stream()
                .filter(commande -> commande.getDate().isAfter(currentDate.minusDays(appConfig.getCommandesLast())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredCommandes);
    }

    // Endpoint : GET /commandes/{id}
    @GetMapping("/commandes/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint : POST /commandes
    @PostMapping("/commandes")
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande newCommande = commandeService.createCommande(commande);
        return ResponseEntity.ok(newCommande);
    }

    // Endpoint : PUT /commandes/{id}
    @PutMapping("/commandes/{id}")
    public ResponseEntity<Commande> updateCommande(
            @PathVariable Long id,
            @RequestBody Commande updatedCommande) {
        try {
            Commande commande = commandeService.updateCommande(id, updatedCommande);
            return ResponseEntity.ok(commande);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint : DELETE /commandes/{id}
    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        try {
            commandeService.deleteCommande(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
