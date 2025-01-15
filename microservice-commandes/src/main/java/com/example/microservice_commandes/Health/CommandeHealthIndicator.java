package com.example.microservice_commandes.Health;


import com.example.microservice_commandes.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommandeHealthIndicator implements HealthIndicator {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public Health health() {
        // Vérifier si des commandes existent dans la base de données
        long count = commandeRepository.count();

        if (count > 0) {
            return Health.up().withDetail("Commandes", count).build();  // Si des commandes existent, le service est UP
        } else {
            return Health.down().withDetail("aucun commande existent", count).build(); // Si aucune commande, le service est DOWN
        }
    }
}
