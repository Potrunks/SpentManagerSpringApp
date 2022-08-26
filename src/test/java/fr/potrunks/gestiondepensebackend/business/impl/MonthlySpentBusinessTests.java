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
import fr.potrunks.gestiondepensebackend.utils.impl.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class MonthlySpentBusinessTests extends TestUtils {

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
        UserEntity newUserEntityForTest = createUserEntityForTest();
        SpentCategoryEntity newSpentCategoryForTest = createSpentCategoryEntityForTest("Entretien");
        userConnected = createUserModelForTest(newUserEntityForTest.getIdUser());
        monthlySpentToCreate = createMonthlySpentModelForTest(
                1000000f,
                "Entretien Enclos TRex",
                "J'ai dépensé sans compter",
                newSpentCategoryForTest.getIdSpentCategory(),
                newSpentCategoryForTest.getNameSpentCategory(),
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
        MonthlySpentEntity monthlySpentOrigin = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        MonthlySpent monthlySpentModified = modifyMonthlySpentModelForTest(
                monthlySpentOrigin.getIdMonthlySpent(),
                10f,
                "Dommage et interet accident parc",
                "Les raptors ont bouffé tout le monde",
                monthlySpentOrigin.getSpentCategoryEntity().getIdSpentCategory(),
                monthlySpentOrigin.getSpentCategoryEntity().getNameSpentCategory(),
                monthlySpentOrigin.getIsActive(),
                userConnected.getIdUser());
        String result = monthlySpentBusiness.updateMonthlySpent(monthlySpentModified);
        assertNull(result);
    }

    @Test
    void shouldReturnDifferentData_WhenModifyMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentOrigin = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        Integer monthlySpentBeforeModification = monthlySpentOrigin.hashCode();
        MonthlySpent monthlySpentModified = modifyMonthlySpentModelForTest(
                monthlySpentOrigin.getIdMonthlySpent(),
                10f,
                "Dommage et interet accident parc",
                "Les raptors ont bouffé tout le monde",
                monthlySpentOrigin.getSpentCategoryEntity().getIdSpentCategory(),
                monthlySpentOrigin.getSpentCategoryEntity().getNameSpentCategory(),
                monthlySpentOrigin.getIsActive(),
                userConnected.getIdUser());
        monthlySpentBusiness.updateMonthlySpent(monthlySpentModified);
        assertNotEquals(monthlySpentBeforeModification, monthlySpentRepository.getById(monthlySpentModified.getIdMonthlySpent()).hashCode());
    }

    @Test
    void shouldReturnErrorMessageNull_WhenDeleteMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentToDelete = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        String result = monthlySpentBusiness.deleteMonthlySpentById(monthlySpentToDelete.getIdMonthlySpent());
        assertNull(result);
    }

    @Test
    void shouldReturnNotFoundMessageFromDataBase_WhenDeleteMonthlySpentWithoutError() {
        MonthlySpentEntity monthlySpentToDelete = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
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
        createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
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
        createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        List<MonthlySpent> result = monthlySpentBusiness.getAllByIdUser(null);
        assertNull(result);
    }

    @Test
    void shouldReturnNull_WhenGetMonthlySpentByIdMonthlySpentNull() {
        createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        MonthlySpent result = monthlySpentBusiness.getById(null);
        assertNull(result);
    }

    @Test
    void shouldReturnNull_WhenGetMonthlySpentByIdMonthlySpentNotExistInDataBase() {
        createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
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
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        MonthlySpent monthlySpent = monthlySpentEntityToModel(monthlySpentEntity);
        MonthlySpent result = monthlySpentBusiness.getById(monthlySpentEntity.getIdMonthlySpent());
        assertEquals(monthlySpent, result);
    }

    @Test
    void shouldReturnNoErrorMessage_WhenMonthlySpentTransformingInSpentSuccessfully() {
        periodSpentBusiness.addNewPeriodSpent(userConnected.getIdUser(), new HashMap<String, Object>());
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        MonthlySpent monthlySpent = monthlySpentEntityToModel(monthlySpentEntity);
        List<MonthlySpent> monthlySpentList = new ArrayList<>();
        monthlySpentList.add(monthlySpent);
        String result = monthlySpentBusiness.becomeSpent(monthlySpentList, userConnected.getIdUser());
        assertNull(result);
    }

    @Test
    void shouldReturnErrorMessage_WhenIdUserTransformingMonthlySpentToSpentNotExist() {
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        MonthlySpent monthlySpent = monthlySpentEntityToModel(monthlySpentEntity);
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
        createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
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
        createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        String result = monthlySpentBusiness.becomeSpent(null, userConnected.getIdUser());
        assertEquals("Impossible d'ajouter la dépense mensuelle aux dépenses", result);
    }

    @Test
    void shouldReturnErrorMessage_WhenIdUserTransformingMonthlySpentToSpentIsNull() {
        MonthlySpentEntity monthlySpentEntity = createMonthlySpentEntityForTest(
                monthlySpentToCreate.getValueMonthlySpent(),
                monthlySpentToCreate.getNameMonthlySpent(),
                monthlySpentToCreate.getCommentMonthlySpent(),
                spentCategoryRepository.getById(monthlySpentToCreate.getIdSpentCategorySelected()),
                userRepository.getById(userConnected.getIdUser()),
                monthlySpentToCreate.getIsActive()
        );
        MonthlySpent monthlySpent = monthlySpentEntityToModel(monthlySpentEntity);
        List<MonthlySpent> monthlySpentList = new ArrayList<>();
        monthlySpentList.add(monthlySpent);
        String result = monthlySpentBusiness.becomeSpent(monthlySpentList, null);
        assertEquals("Utilisateur non connecté", result);
    }
}
