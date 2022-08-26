package fr.potrunks.gestiondepensebackend.utils;

import fr.potrunks.gestiondepensebackend.entity.MonthlySpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.MonthlySpent;
import fr.potrunks.gestiondepensebackend.model.User;

public interface ITestUtils {

    /**
     * Create User Entity for test
     * @return User Entity for test
     */
    UserEntity createUserEntityForTest();

    /**
     * Create Spent Category Entity for test
     * @param newSpentCategoryName
     * @return Spent Category Entity for test
     */
    SpentCategoryEntity createSpentCategoryEntityForTest(String newSpentCategoryName);

    /**
     * Create User Model for test
     * @param newIdUser
     * @return User Model for test
     */
    User createUserModelForTest(Long newIdUser);

    /**
     * Create Monthly Spent Model for test
     * @param newValue
     * @param newName
     * @param newComment
     * @param newIdSpentCategory
     * @param isActive
     * @param newIdUser
     * @return Monthly Spent Model for test
     */
    MonthlySpent createMonthlySpentModelForTest(Float newValue, String newName, String newComment, Long newIdSpentCategory, String newNameMonthlySpentCategory, Boolean isActive, Long newIdUser);

    /**
     * Create Monthly Spent Entity for test
     * @param newValue
     * @param newName
     * @param newComment
     * @param newSpentCategoryEntity
     * @param newUserEntity
     * @param isActive
     * @return Monthly Spent Entity for test
     */
    MonthlySpentEntity createMonthlySpentEntityForTest(Float newValue, String newName, String newComment, SpentCategoryEntity newSpentCategoryEntity, UserEntity newUserEntity, Boolean isActive);

    /**
     * Modify Monthly Spent Model for test
     * @param idMonthlySpentToModify
     * @param modifyValue
     * @param modifyName
     * @param modifyComment
     * @param modifyIdSpentCategory
     * @param isActive
     * @param idUser
     * @return Modified Monthly Spent Model for test
     */
    MonthlySpent modifyMonthlySpentModelForTest(Long idMonthlySpentToModify, Float modifyValue, String modifyName, String modifyComment, Long modifyIdSpentCategory, String modifyNameMonthlySpentCategory, Boolean isActive, Long idUser);

    /**
     * Change a monthly spent entity into a model
     * @param monthlySpentEntityToConvert
     * @return Monthly spent model from entity
     */
    MonthlySpent monthlySpentEntityToModel(MonthlySpentEntity monthlySpentEntityToConvert);
}
