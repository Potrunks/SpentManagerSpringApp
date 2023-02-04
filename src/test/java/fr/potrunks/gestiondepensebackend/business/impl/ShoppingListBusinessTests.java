package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.IShoppingListBusiness;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.ShoppingItem;
import fr.potrunks.gestiondepensebackend.model.ShoppingItemReport;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import fr.potrunks.gestiondepensebackend.resources.ShoppingItemLogAction;
import fr.potrunks.gestiondepensebackend.resources.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShoppingListBusinessTests {

    private ShoppingItem _shoppingItem;
    private UserEntity _user;

    @Autowired
    private IShoppingListBusiness _shoppingListBusiness;

    @Autowired
    private UserIRepository _userRepository;

    @BeforeEach
    public void init(){
        _user = new UserEntity();
        _user.setFirstNameUser("John");
        _user.setLastNameUser("Wick");
        _user = _userRepository.save(_user);

        _shoppingItem = new ShoppingItem();
        _shoppingItem.setName("Xbox Series X");
        _shoppingItem.setQuantity(42);
        _shoppingItem.setUserIdConcerned(_user.getIdUser());
    }

    @Test
    public void shouldReturnStatusOk_whenShoppingItemAddSuccessfully(){
        ShoppingItemReport result = _shoppingListBusiness.addNewShoppingItem(_shoppingItem);

        assertEquals(Status.OK, result.getStatus());
        assertNull(result.getMessage());
        assertEquals(1, result.getShoppingItemProcessed().size());
        ShoppingItem shoppingItemReturn = result.getShoppingItemProcessed().get(0);
        assertEquals(_shoppingItem.getName(), shoppingItemReturn.getName());
        assertEquals(_shoppingItem.getQuantity(), shoppingItemReturn.getQuantity());
        assertEquals(_user.getIdUser(), shoppingItemReturn.getUserIdConcerned());
        assertEquals(LocalDate.now(), shoppingItemReturn.getDate());
        assertEquals(ShoppingItemLogAction.ADDED.toString(), shoppingItemReturn.getAction());
    }

    @Test
    public void shouldReturnStatusError_whenShoppingItemNameIsTooBig(){
        _shoppingItem.setName("Xbox Series XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                "XX");

        ShoppingItemReport result = _shoppingListBusiness.addNewShoppingItem(_shoppingItem);

        assertEquals(Status.ERROR, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals(0, result.getShoppingItemProcessed().size());
    }
}
