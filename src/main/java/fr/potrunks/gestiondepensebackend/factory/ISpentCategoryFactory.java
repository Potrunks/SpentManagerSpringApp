package fr.potrunks.gestiondepensebackend.factory;

import java.util.List;

public interface ISpentCategoryFactory {

    /**
     * Create all spent category for the app
     */
    void SpentCategoryFabricator(List<String> spentCategoryNameList);
}
