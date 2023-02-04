package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.IShoppingListBusiness;
import fr.potrunks.gestiondepensebackend.model.ShoppingItem;
import fr.potrunks.gestiondepensebackend.model.ShoppingItemReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000", "https://spent-manager-react-app.herokuapp.com/"})
@RestController
@RequestMapping("/spentmanager/shoppinglist/")
@Slf4j
public class ShoppingListController {

    @Autowired
    private IShoppingListBusiness _shoppingListBusiness;

    @PostMapping("/shoppingitem/new")
    public ResponseEntity<ShoppingItemReport> newShoppingItem(@RequestBody ShoppingItem newShoppingItem){
        ShoppingItemReport report = _shoppingListBusiness.addNewShoppingItem(newShoppingItem);
        return ResponseEntity.ok(report);
    }
}
