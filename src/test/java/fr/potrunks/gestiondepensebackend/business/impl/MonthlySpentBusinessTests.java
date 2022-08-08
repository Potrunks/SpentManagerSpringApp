package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.MonthlySpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.MonthlySpent;
import fr.potrunks.gestiondepensebackend.model.User;
import fr.potrunks.gestiondepensebackend.repository.MonthlySpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MonthlySpentBusinessTests {

    private User userConnected;
    private MonthlySpent monthlySpentToCreate;

    @Autowired
    private MonthlySpentBusiness monthlySpentBusiness;
    @Autowired
    private PeriodSpentIBusiness periodSpentBusiness;

    @Autowired
    private UserIRepository userRepository;
    @Autowired
    private SpentCategoryIRepository spentCategoryRepository;
    @Autowired
    private MonthlySpentIRepository monthlySpentRepository;

    @BeforeEach()
    void setup() {
        UserEntity newUserEntityForTest = new UserEntity();
        newUserEntityForTest = userRepository.save(newUserEntityForTest);

        SpentCategoryEntity newSpentCategoryForTest = new SpentCategoryEntity();
        newSpentCategoryForTest.setNameSpentCategory("Entretien");
        newSpentCategoryForTest = spentCategoryRepository.save(newSpentCategoryForTest);

        userConnected = new User();
        userConnected.setIdUser(newUserEntityForTest.getIdUser());

        monthlySpentToCreate = new MonthlySpent(
                null,
                1000000f,
                "Entretien Enclos TRex",
                "J'ai dépensé sans compter",
                newSpentCategoryForTest.getIdSpentCategory(),
                true,
                userConnected.getIdUser()
        );
    }

    @Test
    void shouldReturnErrorMessageNull_WhenCreatedMonthlySpentWithoutError(){
        String result = monthlySpentBusiness.createMonthlySpent(userConnected.getIdUser(), monthlySpentToCreate);
        assertNull(result);
    }

    @Test
    void shouldReturnErrorMessageNull_WhenModifyMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentOrigin = createMonthlySpentOriginal();
        MonthlySpent monthlySpentModified = modifyMonthlySpent(monthlySpentOrigin.getIdMonthlySpent(), 10f, "Dommage et interet accident parc", "Les raptors ont bouffé tout le monde", monthlySpentOrigin.getSpentCategoryEntity().getIdSpentCategory(), monthlySpentOrigin.getIsActive());
        String result = monthlySpentBusiness.updateMonthlySpent(monthlySpentModified);
        assertNull(result);
    }

    @Test
    void shouldReturnDifferentData_WhenModifyMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentOrigin = createMonthlySpentOriginal();
        Integer monthlySpentBeforeModification = monthlySpentOrigin.hashCode();
        MonthlySpent monthlySpentModified = modifyMonthlySpent(monthlySpentOrigin.getIdMonthlySpent(), 10f, "Dommage et interet accident parc", "Les raptors ont bouffé tout le monde", monthlySpentOrigin.getSpentCategoryEntity().getIdSpentCategory(), monthlySpentOrigin.getIsActive());
        monthlySpentBusiness.updateMonthlySpent(monthlySpentModified);
        assertNotEquals(monthlySpentBeforeModification, monthlySpentRepository.getById(monthlySpentModified.getIdMonthlySpent()).hashCode());
    }

    @Test
    void shouldReturnErrorMessageNull_WhenDeleteMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentToDelete = createMonthlySpentOriginal();
        String result = monthlySpentBusiness.deleteMonthlySpentById(monthlySpentToDelete.getIdMonthlySpent());
        assertNull(result);
    }

    @Test
    void shouldReturnNotFoundMessageFromDataBase_WhenDeleteMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentToDelete = createMonthlySpentOriginal();
        Long idMonthlySpentToDelete = monthlySpentToDelete.getIdMonthlySpent();
        monthlySpentBusiness.deleteMonthlySpentById(monthlySpentToDelete.getIdMonthlySpent());
        String result = "Found";
        try {
            monthlySpentRepository.getById(idMonthlySpentToDelete);
        } catch (JpaObjectRetrievalFailureException e) {
            result = "Not found";
        }
        assertEquals("Not found", result);
    }

    @Test
    void shouldReturnMoreThanZeroCount_WhenGetAllMonthlySpentOfUserWithMonthlySpent() {
        createMonthlySpentOriginal();
        List<MonthlySpent> result = monthlySpentBusiness.getAllByIdUser(userConnected.getIdUser());
        assertTrue(result.size() > 0);
    }

    @Test
    void shouldReturnZeroCount_WhenGetAllMonthlySpentOfUserWithoutMonthlySpent() {
        List<MonthlySpent> result = monthlySpentBusiness.getAllByIdUser(userConnected.getIdUser());
        assertEquals(0, result.size());
    }

    @Test
    void shouldReturnNull_WhenGetAllMonthlySpentOfUserNull() {
        createMonthlySpentOriginal();
        List<MonthlySpent> result = monthlySpentBusiness.getAllByIdUser(null);
        assertNull(result);
    }

    @Test
    void shouldReturnNull_WhenGetMonthlySpentByIdMonthlySpentNull() {
        createMonthlySpentOriginal();
        MonthlySpent result = monthlySpentBusiness.getById(null);
        assertNull(result);
    }

    @Test
    void shouldReturnNull_WhenGetMonthlySpentByIdMonthlySpentNotExistInDataBase() {
        createMonthlySpentOriginal();
        List<MonthlySpentEntity> monthlySpentEntityList = monthlySpentRepository.findAll();
        List<Long> idMonthlySpentEntityList = new ArrayList<>();
        for (MonthlySpentEntity monthlySpentEntity : monthlySpentEntityList) {
            idMonthlySpentEntityList.add(monthlySpentEntity.getIdMonthlySpent());
        }
        Boolean idMonthlySpentFound = false;
        Long firstIdMonthlySpentNotInDataBase = 1L;
        while (idMonthlySpentFound == false) {
            if (idMonthlySpentEntityList.contains(firstIdMonthlySpentNotInDataBase)) {
                firstIdMonthlySpentNotInDataBase++;
            } else {
                idMonthlySpentFound = true;
            }
        }
        MonthlySpent result = monthlySpentBusiness.getById(firstIdMonthlySpentNotInDataBase);
        assertNull(result);
    }

    @Test
    void shouldReturnMonthlySpent_WhenGetMonthlySpentByIdMonthlySpentExistInDataBase() {
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentOriginal();
        MonthlySpent monthlySpent = entityToModel(monthlySpentEntity);
        MonthlySpent result = monthlySpentBusiness.getById(monthlySpentEntity.getIdMonthlySpent());
        assertEquals(monthlySpent, result);
    }

    @Test
    void shouldReturnNoErrorMessage_WhenMonthlySpentTransformingInSpentSuccessfully() {
        periodSpentBusiness.addNewPeriodSpent(userConnected.getIdUser(), new HashMap<String, Object>());
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentOriginal();
        MonthlySpent monthlySpent = entityToModel(monthlySpentEntity);
        List<MonthlySpent> monthlySpentList = new ArrayList<>();
        monthlySpentList.add(monthlySpent);
        String result = monthlySpentBusiness.becomeSpent(monthlySpentList, userConnected.getIdUser());
        assertNull(result);
    }

    @Test
    void shouldReturnErrorMessage_WhenIdUserTransformingMonthlySpentToSpentNotExist() {
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentOriginal();
        MonthlySpent monthlySpent = entityToModel(monthlySpentEntity);
        List<UserEntity> userEntityList = userRepository.findAll();
        List<Long> idUserEntityList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            idUserEntityList.add(userEntity.getIdUser());
        }
        Boolean idUserEntityFound = false;
        Long firstIdUserEntityNotInDataBase = 1L;
        while (idUserEntityFound == false) {
            if (idUserEntityList.contains(firstIdUserEntityNotInDataBase)) {
                firstIdUserEntityNotInDataBase++;
            } else {
                idUserEntityFound = true;
            }
        }
        List<MonthlySpent> monthlySpentList = new ArrayList<>();
        monthlySpentList.add(monthlySpent);
        String result = monthlySpentBusiness.becomeSpent(monthlySpentList, firstIdUserEntityNotInDataBase);
        assertEquals("Utilisateur non présent en base de données", result);
    }

    @Test
    void shouldReturnErrorMessage_WhenMonthlySpentToTransformedToSpentNotExist() {
        createMonthlySpentOriginal();
        List<MonthlySpentEntity> monthlySpentEntityList = monthlySpentRepository.findAll();
        List<Long> idMonthlySpentEntityList = new ArrayList<>();
        for (MonthlySpentEntity mse : monthlySpentEntityList) {
            idMonthlySpentEntityList.add(mse.getIdMonthlySpent());
        }
        Boolean idMonthlySpentFound = false;
        Long firstIdMonthlySpentNotInDataBase = 1L;
        while (idMonthlySpentFound == false) {
            if (idMonthlySpentEntityList.contains(firstIdMonthlySpentNotInDataBase)) {
                firstIdMonthlySpentNotInDataBase++;
            } else {
                idMonthlySpentFound = true;
            }
        }
        MonthlySpent monthlySpentNotExist = new MonthlySpent();
        monthlySpentNotExist.setIdMonthlySpent(firstIdMonthlySpentNotInDataBase);
        List<MonthlySpent> monthlySpentList = new ArrayList<>();
        monthlySpentList.add(monthlySpentNotExist);
        String result = monthlySpentBusiness.becomeSpent(monthlySpentList, userConnected.getIdUser());
        assertEquals("Une ou plusieurs dépenses mensuelles n'existe pas", result);
    }

    @Test
    void shouldReturnErrorMessage_WhenMonthlySpentToTransformedToSpentIsNull() {
        createMonthlySpentOriginal();
        String result = monthlySpentBusiness.becomeSpent(null, userConnected.getIdUser());
        assertEquals("Impossible d'ajouter la dépense mensuelle aux dépenses", result);
    }

    @Test
    void shouldReturnErrorMessage_WhenIdUserTransformingMonthlySpentToSpentIsNull() {
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentOriginal();
        MonthlySpent monthlySpent = entityToModel(monthlySpentEntity);
        List<MonthlySpent> monthlySpentList = new ArrayList<>();
        monthlySpentList.add(monthlySpent);
        String result = monthlySpentBusiness.becomeSpent(monthlySpentList, null);
        assertEquals("Utilisateur non connecté", result);
    }

    private MonthlySpent modifyMonthlySpent(Long monthlySpentOriginId, float valueMonthlySpent, String nameMonthlySpent, String commentMonthlySpent, Long idSpentCategory, Boolean isActive) {
        MonthlySpent monthlySpentModified = new MonthlySpent(
                monthlySpentOriginId,
                valueMonthlySpent,
                nameMonthlySpent,
                commentMonthlySpent,
                idSpentCategory,
                isActive,
                userConnected.getIdUser()
        );
        return monthlySpentModified;
    }

    private MonthlySpentEntity createMonthlySpentOriginal() {
        MonthlySpentEntity monthlySpentOrigin = new MonthlySpentEntity();
        monthlySpentOrigin.setValueMonthlySpent(monthlySpentToCreate.getValueMonthlySpent());
        monthlySpentOrigin.setNameMonthlySpent(monthlySpentToCreate.getNameMonthlySpent());
        monthlySpentOrigin.setCommentMonthlySpent(monthlySpentToCreate.getCommentMonthlySpent());
        monthlySpentOrigin.setSpentCategoryEntity(spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()));
        monthlySpentOrigin.setUserEntity(userRepository.getById(userConnected.getIdUser()));
        monthlySpentOrigin.setIsActive(monthlySpentToCreate.getIsActive());
        return monthlySpentRepository.save(monthlySpentOrigin);
    }

    private MonthlySpent entityToModel(MonthlySpentEntity monthlySpentEntity) {
        MonthlySpent monthlySpent = new MonthlySpent();
        BeanUtils.copyProperties(monthlySpentEntity, monthlySpent);
        monthlySpent.setIdSpentCategorySelected(monthlySpentEntity.getSpentCategoryEntity().getIdSpentCategory());
        monthlySpent.setIdUserCreator(monthlySpentEntity.getUserEntity().getIdUser());
        return monthlySpent;
    }
}
