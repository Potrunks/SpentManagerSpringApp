package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessUtils {

    public String checkUserPermission(Long idUserChecked, UserIRepository userRepository) {
        if (idUserChecked == null) {
            log.warn("User not connected");
            return "Utilisateur non connecté";
        }
        if (!userRepository.existsById(idUserChecked)) {
            log.warn("User id {} not found in database", idUserChecked);
            return "Utilisateur non présent en base de données";
        }
        return null;
    }
}
