package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.model.SpentCategory;

import java.util.List;

public interface SpentCategoryIBusiness {

    /**
     * Find a spent category with the ID
     * @param idSpentCategorySelected ID of the spent category wanted
     * @return Return a Spent Category Entity wanted
     */
    SpentCategoryEntity findById(Long idSpentCategorySelected);

    /**
     * Find all the spent category from the database
     * @return Return a List of Spent Category model
     */
    List<SpentCategory> getAll();

    /**
     * Check if spent category already exist in DB
     * @param spentCategoryName Spent category wanted
     * @return If existed, return true else false
     */
    Boolean verifySpentCategoryExist(String spentCategoryName);

    /**
     * Create new spent categories
     * @param spentCategoryNameList List of spent categories names
     * @return If creation is a success, return true else false;
     */
    Boolean createNewSpentCategories(List<String> spentCategoryNameList);
}
