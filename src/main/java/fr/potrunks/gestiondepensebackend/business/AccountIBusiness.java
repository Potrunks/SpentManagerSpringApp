package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;

import java.util.Map;

public interface AccountIBusiness {

    /**
     * Add a new account in database
     * @param user User who create the account
     * @param response Response to send at the UI
     * @return Map of String (Key) and Boolean (Value) who contain all information of each account creation step
     */
    Map<String, Boolean> addNewAccount(User user, Map<String, Boolean> response);

    /**
     * Authenticate the user who want to connect at the app
     * @param user User who want to connect
     * @param response Response to send at the UI
     * @return Map of String (Key) and Object (Value) who contain all information of each authentication step
     */
    Map<String, Object> authentication(User user, Map<String, Object> response);

    /**
     * Find a user by mail
     * @param user User from the UI who contain the mail wanted
     * @return A user with the mail wanted
     */
    UserEntity getUserByMailUser(User user);

    /**
     * Verify if there are already 2 accounts in the database
     * @return Return a Map of String (key) and Boolean (value) who contain the information of the step of the method
     */
    Map<String, Boolean> verifyIfThereAreAlready2Accounts(Map<String, Boolean> response);

    /**
     * Verify if an administrator account already exist in DB
     * @return If exits, return true else false
     */
    Boolean verifyAdministratorAccountExist();

    /**
     * Allow to create admin account
     * @param firstName Firstname of the user
     * @param lastName Lastname of the user
     * @param mail Mail of the user
     * @param password Password of the user
     */
    Boolean createAdminAccount(String firstName, String lastName, String mail, String password);
}
