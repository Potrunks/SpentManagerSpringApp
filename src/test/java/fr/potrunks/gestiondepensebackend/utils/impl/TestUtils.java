package fr.potrunks.gestiondepensebackend.utils.impl;

import fr.potrunks.gestiondepensebackend.entity.MonthlySpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.MonthlySpent;
import fr.potrunks.gestiondepensebackend.model.User;
import fr.potrunks.gestiondepensebackend.repository.MonthlySpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import fr.potrunks.gestiondepensebackend.utils.ITestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TestUtils implements ITestUtils {

    @Autowired
    private UserIRepository userRepository;
    @Autowired
    private SpentCategoryIRepository spentCategoryRepository;
    @Autowired
    private MonthlySpentIRepository monthlySpentRepository;

    @Override
    public UserEntity createUserEntityForTest() {
        return userRepository.save(new UserEntity());
    }

    @Override
    public SpentCategoryEntity createSpentCategoryEntityForTest(String newSpentCategoryName) {
        return spentCategoryRepository.save(new SpentCategoryEntity(
                null,
                newSpentCategoryName,
                null,
                null
        ));
    }

    @Override
    public User createUserModelForTest(Long newIdUser) {
        return new User(
                newIdUser,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public MonthlySpent createMonthlySpentModelForTest(Float newValue, String newName, String newComment, Long newIdSpentCategory, String newNameMonthlySpentCategory, Boolean isActive, Long newIdUser) {
        return new MonthlySpent(
                null,
                newValue,
                newName,
                newComment,
                newIdSpentCategory,
                newNameMonthlySpentCategory,
                isActive,
                newIdUser
        );
    }

    @Override
    public MonthlySpentEntity createMonthlySpentEntityForTest(Float newValue, String newName, String newComment, SpentCategoryEntity newSpentCategoryEntity, UserEntity newUserEntity, Boolean isActive) {
        return monthlySpentRepository.save(new MonthlySpentEntity(
                null,
                newValue,
                newName,
                newComment,
                isActive,
                newSpentCategoryEntity,
                null,
                newUserEntity
        ));
    }

    @Override
    public MonthlySpent modifyMonthlySpentModelForTest(Long idMonthlySpentToModify, Float modifyValue, String modifyName, String modifyComment, Long modifyIdSpentCategory, String modifyNameMonthlySpentCategory, Boolean isActive, Long idUser) {
        return new MonthlySpent(
                idMonthlySpentToModify,
                modifyValue,
                modifyName,
                modifyComment,
                modifyIdSpentCategory,
                modifyNameMonthlySpentCategory,
                isActive,
                idUser
        );
    }

    @Override
    public MonthlySpent monthlySpentEntityToModel(MonthlySpentEntity monthlySpentEntityToConvert) {
        MonthlySpent monthlySpent = new MonthlySpent();
        BeanUtils.copyProperties(monthlySpentEntityToConvert, monthlySpent);
        monthlySpent.setIdSpentCategorySelected(monthlySpentEntityToConvert.getSpentCategoryEntity().getIdSpentCategory());
        monthlySpent.setIdUserCreator(monthlySpentEntityToConvert.getUserEntity().getIdUser());
        monthlySpent.setNameMonthlySpentCategory(monthlySpentEntityToConvert.getSpentCategoryEntity().getNameSpentCategory());
        return monthlySpent;
    }
}
