package fr.potrunks.gestiondepensebackend.factory.impl;

import fr.potrunks.gestiondepensebackend.business.AccountIBusiness;
import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.factory.IMainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MainFactory implements IMainFactory {

    @Autowired
    private AccountIBusiness accountBusiness;
    @Autowired
    private SpentCategoryIBusiness spentCategoryBusiness;

    @Override
    public void AdministratorAccountFabricator(String firstName, String lastName, String mail, String password) {
        log.warn("Start process administrator account creation...");
        if (!accountBusiness.verifyAdministratorAccountExist()) {
            Boolean success = false;
            success = accountBusiness.createAdminAccount(firstName, lastName, mail, password);
            if (success == true) {
                log.warn("Administrator account creation process succeeded");
            } else {
                log.error("Administrator account creation process failed");
            }
        }
        log.warn("End process administrator account creation !!!");
    }

    @Override
    public void SpentCategoryFabricator(List<String> spentCategoryNameList) {
        log.warn("Start process spent category creation...");
        spentCategoryBusiness.createNewSpentCategories(spentCategoryNameList);
        log.warn("End process spent category creation !!!");
    }
}
