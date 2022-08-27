package fr.potrunks.gestiondepensebackend.factory.impl;

import fr.potrunks.gestiondepensebackend.business.AccountIBusiness;
import fr.potrunks.gestiondepensebackend.factory.IAccountFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountFactory implements IAccountFactory {

    @Autowired
    private AccountIBusiness accountBusiness;

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
    public void NoAdminAccountFabricator(String firstName, String lastName, String mail, String password) {
        log.warn("Start process administrator account creation...");
        Boolean success = false;
        success = accountBusiness.createNormalAccount(firstName, lastName, mail, password);
        if (success == true) {
            log.warn("Normal account creation process succeeded");
        } else {
            log.error("Normal account creation process failed");
        }
        log.warn("End process administrator account creation !!!");
    }
}
