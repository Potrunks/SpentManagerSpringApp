package fr.potrunks.gestiondepensebackend.factory.impl;

import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.factory.ISpentCategoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SpentCategoryFactory implements ISpentCategoryFactory {

    @Autowired
    private SpentCategoryIBusiness spentCategoryBusiness;

    @Override
    public void SpentCategoryFabricator(List<String> spentCategoryNameList) {
        log.warn("Start process spent category creation...");
        spentCategoryBusiness.createNewSpentCategories(spentCategoryNameList);
        log.warn("End process spent category creation !!!");
    }
}
