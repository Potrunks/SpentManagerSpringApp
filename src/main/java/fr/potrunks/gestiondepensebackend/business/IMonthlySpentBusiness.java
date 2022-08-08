package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.model.MonthlySpent;

import java.util.List;

public interface IMonthlySpentBusiness {

    /**
     * Create new monthly spent
     * @param idUserConnected
     * @param monthlySpentToCreate
     * @return Error message if there is a problem during the save in database
     */
    String createMonthlySpent(Long idUserConnected, MonthlySpent monthlySpentToCreate);

    /**
     * Update monthly spent wanted
     * @param monthlySpentToModify
     * @return Error message if there is a problem during the update in database
     */
    String updateMonthlySpent(MonthlySpent monthlySpentToModify);

    /**
     * Delete a monthly spent by his Id
     * @param idMonthlySpentToDelete
     * @return Error message if there is a problem during the update in database
     */
    String deleteMonthlySpentById(Long idMonthlySpentToDelete);

    /**
     * Get all the monthly spent of an user wanted
     * @param idUserWanted
     * @return List of monthly spent of the user wanted
     */
    List<MonthlySpent> getAllByIdUser(Long idUserWanted);

    /**
     * Get monthly spent by id
     * @param idMonthlySpentWanted
     * @return Monthly spent with id wanted
     */
    MonthlySpent getById(Long idMonthlySpentWanted);

    /**
     * Transformed a monthly spent in a spent
     * @param monthlySpentToSpentifyList
     * @param idUserCreatingSpent
     * @return Error message if there is a problem during the transformation
     */
    String becomeSpent(List<MonthlySpent> monthlySpentToSpentifyList, Long idUserCreatingSpent);
}
