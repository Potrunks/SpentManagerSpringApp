package fr.potrunks.gestiondepensebackend.factory;

public interface IMainFactory {

    /**
     * Create an administrator account
     */
    void AdministratorAccountFabricator(String firstName, String lastName, String mail, String password);

    /**
     * Create all spent category for the app
     */
    void SpentCategoryFabricator();
}
