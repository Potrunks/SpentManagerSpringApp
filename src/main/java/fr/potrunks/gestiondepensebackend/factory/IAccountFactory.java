package fr.potrunks.gestiondepensebackend.factory;

public interface IAccountFactory {

    /**
     * Create an administrator account
     */
    void AdministratorAccountFabricator(String firstName, String lastName, String mail, String password);
}
