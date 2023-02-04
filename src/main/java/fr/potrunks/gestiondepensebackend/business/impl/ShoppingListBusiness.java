package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.IShoppingListBusiness;
import fr.potrunks.gestiondepensebackend.entity.ErrorLogEntity;
import fr.potrunks.gestiondepensebackend.entity.ShoppingItemEntity;
import fr.potrunks.gestiondepensebackend.entity.ShoppingItemLogEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.ShoppingItem;
import fr.potrunks.gestiondepensebackend.model.ShoppingItemReport;
import fr.potrunks.gestiondepensebackend.repository.ErrorLogIRepository;
import fr.potrunks.gestiondepensebackend.repository.ShoppingItemIRepository;
import fr.potrunks.gestiondepensebackend.repository.ShoppingItemLogIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import fr.potrunks.gestiondepensebackend.resources.ShoppingItemLogAction;
import fr.potrunks.gestiondepensebackend.resources.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class ShoppingListBusiness implements IShoppingListBusiness {

    @Autowired
    private ShoppingItemIRepository _shoppingItemRepository;
    @Autowired
    private UserIRepository _userRepository;
    @Autowired
    private ShoppingItemLogIRepository _shoppingItemLogRepository;
    @Autowired
    private ErrorLogIRepository _errorLogRepository;

    @Override
    public ShoppingItemReport addNewShoppingItem(ShoppingItem shoppingItemToAdd) {
        ShoppingItemReport report = new ShoppingItemReport();
        UserEntity user;

        try {
            user = _userRepository.getById(shoppingItemToAdd.getUserIdConcerned());

            ShoppingItemEntity shoppingItemAdded = new ShoppingItemEntity();
            shoppingItemAdded.setName(shoppingItemToAdd.getName());
            shoppingItemAdded.setQuantity(shoppingItemToAdd.getQuantity());
            shoppingItemAdded.setIsBought(false);
            shoppingItemAdded = _shoppingItemRepository.save(shoppingItemAdded);

            ShoppingItemLogEntity shoppingItemLog = new ShoppingItemLogEntity();
            shoppingItemLog.setShoppingItem(shoppingItemAdded);
            shoppingItemLog.setAction(ShoppingItemLogAction.ADDED);
            shoppingItemLog.setUserLogged(user);
            shoppingItemLog.setDate(LocalDate.now());
            shoppingItemLog = _shoppingItemLogRepository.save(shoppingItemLog);

            ShoppingItem shoppingItemToReturn = new ShoppingItem
                    (
                            shoppingItemAdded.getName(),
                            shoppingItemAdded.getQuantity(),
                            user.getIdUser(),
                            shoppingItemLog.getDate(),
                            shoppingItemLog.getAction().toString()
                    );
            report.getShoppingItemProcessed().add(shoppingItemToReturn);
        }
        catch (Exception exception){
            user = _userRepository.getById(shoppingItemToAdd.getUserIdConcerned());
            String errorMessage = exception.getCause().getCause().getMessage();

            log.error("Error during add new shopping item in the database");
            log.info("Shopping item name : " + shoppingItemToAdd.getName());
            log.info("Shopping item quantity : " + shoppingItemToAdd.getQuantity());
            log.info("Shopping item user : " + user.getFirstNameUser() + " " + user.getLastNameUser());
            log.error("Error message : " + errorMessage);

            report.setStatus(Status.ERROR);
            report.setMessage(errorMessage);

            ErrorLogEntity errorLogEntity = new ErrorLogEntity();
            errorLogEntity.setUserLogged(user);
            errorLogEntity.setMethodConcerned("addNewShoppingItem");
            errorLogEntity.setMessage(errorMessage);
            errorLogEntity.setDate(LocalDate.now());
            _errorLogRepository.save(errorLogEntity);
        }

        return report;
    }
}
