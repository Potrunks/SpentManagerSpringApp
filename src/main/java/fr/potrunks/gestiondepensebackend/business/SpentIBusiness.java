package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.Spent;

import java.util.HashMap;
import java.util.List;

public interface SpentIBusiness {

    /**
     * Get all spent during the period spent in progress
     * @return Return a List of Spent model
     */
    List<Spent> getSpentsByPeriodSpentInProgress();

    /**
     * Delete Spent by ID in the database
     * @param idSpent ID of the spent who want delete
     * @return Return a Boolean with the result of the deletion
     */
    Boolean deleteSpent(Long idSpent);

    /**
     * Get spent by ID in database
     * @param idSpent ID of the spent wanted
     * @return Return a Spent model
     */
    Spent getSpent(Long idSpent);

    /**
     * Update spent by ID in the database
     * @param idSpent ID of the spent to update
     * @param spent Spent modified
     * @return Return a spent model modified and updated
     */
    Spent updateSpent(Long idSpent, Spent spent);

    /**
     * Set a new spent and add it to the database
     * @param userConnected User who wants to add the spent
     * @param periodSpentInProgress Period spent in progress to attach the spent
     * @param newSpentList
     * @return Return a Spent Entity after added in database
     */
    List<SpentEntity> create(UserEntity userConnected, PeriodSpentEntity periodSpentInProgress, List<Spent> newSpentList);

    /**
     * Get all spents in period spent id wanted
     * @param idPeriodSpent ID of the period spent wanted
     * @return Return a list of spent model in the period spent id wanted
     */
    List<Spent> getSpentsByIdPeriodSpent(Long idPeriodSpent);
}
