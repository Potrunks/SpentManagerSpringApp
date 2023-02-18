package fr.potrunks.gestiondepensebackend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingItemReport extends Report {

    public ShoppingItemReport() {
        this.shoppingItemProcessed = new ArrayList<>();
    }

    private List<ShoppingItem> shoppingItemProcessed;

    public List<ShoppingItem> getShoppingItemProcessed() {
        return shoppingItemProcessed;
    }

    public void setShoppingItemProcessed(List<ShoppingItem> shoppingItemProcessed) {
        this.shoppingItemProcessed = shoppingItemProcessed;
    }
}
