package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.model.ShoppingItem;
import fr.potrunks.gestiondepensebackend.model.ShoppingItemReport;

public interface IShoppingListBusiness {

    ShoppingItemReport addNewShoppingItem(ShoppingItem shoppingItemToAdd);
}
