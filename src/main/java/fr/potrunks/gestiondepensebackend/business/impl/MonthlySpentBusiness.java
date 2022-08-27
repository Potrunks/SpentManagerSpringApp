package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.BusinessUtils;
import fr.potrunks.gestiondepensebackend.business.IMonthlySpentBusiness;
import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.*;
import fr.potrunks.gestiondepensebackend.model.MonthlySpent;
import fr.potrunks.gestiondepensebackend.model.Spent;
import fr.potrunks.gestiondepensebackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MonthlySpentBusiness extends BusinessUtils implements IMonthlySpentBusiness {

    @Autowired
    private SpentIBusiness spentBusiness;

    @Autowired
    private MonthlySpentIRepository monthlySpentRepository;
    @Autowired
    private UserIRepository userRepository;
    @Autowired
    private SpentCategoryIRepository spentCategoryRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentRepository;
    @Autowired
    private SpentIRepository spentRepository;

    @Override
    public String createMonthlySpent(Long idUserConnected, MonthlySpent monthlySpentToCreate) {
        log.info("New monthly spent {} creating by user id {}...", monthlySpentToCreate.getNameMonthlySpent(), idUserConnected);
        MonthlySpentEntity newMonthlySpent = setupNewMonthlySpent(monthlySpentToCreate, idUserConnected);
        try{
            newMonthlySpent = monthlySpentRepository.save(newMonthlySpent);
        } catch (Exception e) {
            log.warn("Error during creation the monthly spent id {}", monthlySpentToCreate.getNameMonthlySpent());
            log.error(e.getMessage());
            return "Une erreur est survenue pendant la création de la dépense mensuelle";
        }
        if (newMonthlySpent.getIdMonthlySpent() != null) {
            log.info("New monthly spent {} successfully created", monthlySpentToCreate.getNameMonthlySpent());
            return null;
        }
        log.warn("Error during creation the new monthly spent {}", monthlySpentToCreate.getNameMonthlySpent());
        return "Une erreur est survenue pendant la création de la dépense mensuelle";
    }

    private MonthlySpentEntity setupNewMonthlySpent(MonthlySpent monthlySpentToSetup, Long idUserCreator) {
        MonthlySpentEntity newMonthlySpent = new MonthlySpentEntity();
        newMonthlySpent.setValueMonthlySpent(monthlySpentToSetup.getValueMonthlySpent());
        newMonthlySpent.setNameMonthlySpent(monthlySpentToSetup.getNameMonthlySpent());
        newMonthlySpent.setCommentMonthlySpent(monthlySpentToSetup.getCommentMonthlySpent());
        newMonthlySpent.setSpentCategoryEntity(spentCategoryRepository.getById(monthlySpentToSetup.getIdSpentCategorySelected()));
        newMonthlySpent.setUserEntity(userRepository.getById(idUserCreator));
        newMonthlySpent.setIsActive(monthlySpentToSetup.getIsActive());
        return newMonthlySpent;
    }

    @Override
    public String updateMonthlySpent(MonthlySpent monthlySpentToModify) {
        log.info("Monthly spent id {} updating...", monthlySpentToModify.getIdMonthlySpent());
        MonthlySpentEntity monthlySpentModified = monthlySpentRepository.getById(monthlySpentToModify.getIdMonthlySpent());
        if (monthlySpentModified == null) {
            log.warn("Monthly spent id {} not found", monthlySpentToModify.getIdMonthlySpent());
            return "La dépense mensuelle n'existe pas dans la base de données";
        }
        monthlySpentModified = setupModifyMonthlySpent(monthlySpentToModify, monthlySpentModified);
        try {
            monthlySpentRepository.save(monthlySpentModified);
        } catch (Exception e) {
            log.warn("Error during updating the monthly spent id {}", monthlySpentToModify.getIdMonthlySpent());
            log.error(e.getMessage());
            return "Une erreur est survenue pendant la modification de la dépense mensuelle";
        }
        log.info("Monthly spent id {} updating successfully", monthlySpentToModify.getIdMonthlySpent());
        return null;
    }

    private MonthlySpentEntity setupModifyMonthlySpent(MonthlySpent monthlySpentModify, MonthlySpentEntity monthlySpentEntityOrigin) {
        monthlySpentEntityOrigin.setValueMonthlySpent(monthlySpentModify.getValueMonthlySpent());
        monthlySpentEntityOrigin.setNameMonthlySpent(monthlySpentModify.getNameMonthlySpent());
        monthlySpentEntityOrigin.setCommentMonthlySpent(monthlySpentModify.getCommentMonthlySpent());
        monthlySpentEntityOrigin.setSpentCategoryEntity(spentCategoryRepository.getById(monthlySpentModify.getIdSpentCategorySelected()));
        monthlySpentEntityOrigin.setIsActive(monthlySpentModify.getIsActive());
        return monthlySpentEntityOrigin;
    }

    @Override
    public String deleteMonthlySpentById(Long idMonthlySpentToDelete) {
        log.info("Monthly spent id {} deleting...", idMonthlySpentToDelete);
        MonthlySpentEntity monthlySpentToDelete = monthlySpentRepository.getById(idMonthlySpentToDelete);
        if (monthlySpentToDelete == null) {
            log.warn("Monthly spent id {} don't found in database", idMonthlySpentToDelete);
            return "La dépense mensuelle n'existe pas dans la base de données";
        }
        deleteAllConstraintInSpent(monthlySpentToDelete);
        try {
            monthlySpentRepository.delete(monthlySpentToDelete);
        } catch (Exception e) {
            log.warn("Error during deleting the monthly spent id {}", idMonthlySpentToDelete);
            log.error(e.getMessage());
            return "Une erreur est survenue pendant la suppression de la dépense mensuelle";
        }
        log.info("Monthly spent id {} deleting successfully", idMonthlySpentToDelete);
        return null;
    }

    private void deleteAllConstraintInSpent(MonthlySpentEntity monthlySpentToRemoveFromSpentTable) {
        log.info("Start to delete all constraint of monthly spent id {} from spent table in db", monthlySpentToRemoveFromSpentTable.getIdMonthlySpent());
        List<SpentEntity> spentEntityToRemoveConstraintList = spentRepository.findByMonthlySpentEntity(monthlySpentToRemoveFromSpentTable);
        if (spentEntityToRemoveConstraintList != null && spentEntityToRemoveConstraintList.size() != 0) {
            for (SpentEntity spentToRemoveConstraint: spentEntityToRemoveConstraintList) {
                spentToRemoveConstraint.setMonthlySpentEntity(null);
                spentRepository.save(spentToRemoveConstraint);
            }
            log.info("All constraint removed");
        }
    }

    @Override
    public List<MonthlySpent> getAllByIdUser(Long idUserWanted) {
        log.info("Get all monthly spent of the user id {} in progress...", idUserWanted);
        if (idUserWanted == null) {
            log.warn("Id user must not be null");
            return null;
        }
        List<MonthlySpentEntity> monthlySpentEntityList = monthlySpentRepository.findByUserEntity(userRepository.getById(idUserWanted));
        List<MonthlySpent> monthlySpentList = fromEntityToModel(monthlySpentEntityList);
        log.info("Get all monthly spent of the user id {} finished", idUserWanted);
        return monthlySpentList;
    }

    private List<MonthlySpent> fromEntityToModel(List<MonthlySpentEntity> monthlySpentEntityList) {
        List<MonthlySpent> monthlySpentList = monthlySpentEntityList.stream().map(monthlySpent -> new MonthlySpent(
                monthlySpent.getIdMonthlySpent(),
                monthlySpent.getValueMonthlySpent(),
                monthlySpent.getNameMonthlySpent(),
                monthlySpent.getCommentMonthlySpent(),
                monthlySpent.getSpentCategoryEntity().getIdSpentCategory(),
                monthlySpent.getSpentCategoryEntity().getNameSpentCategory(),
                monthlySpent.getIsActive(),
                monthlySpent.getUserEntity().getIdUser()
        )).collect(Collectors.toList());
        return monthlySpentList;
    }

    @Override
    public MonthlySpent getById(Long idMonthlySpentWanted) {
        log.info("Get monthly spent id {} in progress...", idMonthlySpentWanted);
        if (idMonthlySpentWanted == null) {
            log.warn("Id monthly spent must not be null");
            return null;
        }
        if (!monthlySpentRepository.existsById(idMonthlySpentWanted)) {
            log.warn("Monthly spent with id {} not found", idMonthlySpentWanted);
            return null;
        }
        MonthlySpentEntity monthlySpentEntity = monthlySpentRepository.getById(idMonthlySpentWanted);
        List<MonthlySpentEntity> monthlySpentEntityList = new ArrayList<>();
        monthlySpentEntityList.add(monthlySpentEntity);
        List<MonthlySpent> monthlySpentList = fromEntityToModel(monthlySpentEntityList);
        log.info("Get monthly spent id {} finished", idMonthlySpentWanted);
        return monthlySpentList.get(0);
    }

    @Override
    public String becomeSpent(List<MonthlySpent> monthlySpentToSpentifyList, Long idUserCreatingSpent) {
        log.info("Starting process transforming monthly spent into spent...");
        String checkUserPermissionResult = checkUserPermission(idUserCreatingSpent, userRepository);
        if (checkUserPermissionResult != null) {
            return checkUserPermissionResult;
        }
        UserEntity userTransformingMonthlySpentToSpent = userRepository.getById(idUserCreatingSpent);
        if (monthlySpentToSpentifyList == null || monthlySpentToSpentifyList.contains(null)) {
            log.warn("Monthly spent must not be null");
            return "Impossible d'ajouter la dépense mensuelle aux dépenses";
        }
        List<Spent> newSpentList = new ArrayList<>();
        PeriodSpentEntity periodSpentInProgress = periodSpentRepository.findByEndDatePeriodSpentIsNull();
        for (MonthlySpent monthlySpent : monthlySpentToSpentifyList) {
            log.info("Transform monthly spent id {} in spent in progress...", monthlySpent.getIdMonthlySpent());
            if (!monthlySpentRepository.existsById(monthlySpent.getIdMonthlySpent())) {
                log.warn("Monthly spent id {} not found in database", monthlySpent.getIdMonthlySpent());
                return "Une ou plusieurs dépenses mensuelles n'existe pas";
            }
            MonthlySpentEntity monthlySpentEntity = monthlySpentRepository.getById(monthlySpent.getIdMonthlySpent());
            if (spentRepository.findByPeriodSpentEntityAndMonthlySpentEntity(periodSpentInProgress, monthlySpentEntity) == null) {
                SpentCategoryEntity spentCategoryEntity = spentCategoryRepository.getById(monthlySpent.getIdSpentCategorySelected());
                Spent newSpent = new Spent(
                        null,
                        monthlySpent.getValueMonthlySpent(),
                        null,
                        monthlySpent.getNameMonthlySpent(),
                        monthlySpent.getCommentMonthlySpent(),
                        null,
                        null,
                        null,
                        spentCategoryEntity.getIdSpentCategory(),
                        null,
                        monthlySpent.getIdMonthlySpent()
                );
                newSpentList.add(newSpent);
            } else {
                log.warn("Monthly spent id {} already exist in data base", monthlySpent.getIdMonthlySpent());
            }
        }
        List<SpentEntity> newSpentEntityList = new ArrayList<>();
        try {
            newSpentEntityList = spentBusiness.create(userTransformingMonthlySpentToSpent, periodSpentInProgress, newSpentList);
        } catch (Exception e) {
            log.warn("Error during transformation of monthly spent id in spent");
            log.warn(e.getMessage());
        }
        for (SpentEntity spentEntity : newSpentEntityList) {
            if (spentEntity.getIdSpent() != null) {
                log.info("Monthly spent transformed into spent id {} successfully", spentEntity.getIdSpent());
            } else {
                return "Une erreur est survenue pendant l'ajout de la dépense mensuelle";
            }
            return null;
        }
        return "Une erreur est survenue pendant l'ajout de la dépense mensuelle";
    }

    @Override
    public List<MonthlySpent> getAllMonthlySpentActiveByUser(UserEntity userEntity, PeriodSpentEntity periodSpentEntityInProgress) {
        log.info("Verify if Monthly Spent Entity active exist for User id {}", userEntity.getIdUser());
        List<MonthlySpent> monthlySpentList;
        List<MonthlySpentEntity> monthlySpentEntityList = monthlySpentRepository.findByUserEntityAndIsActiveTrue(userEntity);
        if (monthlySpentEntityList == null) {
            log.info("User id {} have no monthly spent active", userEntity.getIdUser());
            return null;
        } else {
            log.info("Add monthly spent active to the new period spent id {} in progress...", periodSpentEntityInProgress.getIdPeriodSpent());
            monthlySpentList = monthlySpentEntityList.stream().map(monthlySpentEntity -> new MonthlySpent(
                    monthlySpentEntity.getIdMonthlySpent(),
                    monthlySpentEntity.getValueMonthlySpent(),
                    monthlySpentEntity.getNameMonthlySpent(),
                    monthlySpentEntity.getCommentMonthlySpent(),
                    monthlySpentEntity.getSpentCategoryEntity().getIdSpentCategory(),
                    monthlySpentEntity.getSpentCategoryEntity().getNameSpentCategory(),
                    monthlySpentEntity.getIsActive(),
                    monthlySpentEntity.getUserEntity().getIdUser()
            )).collect(Collectors.toList());
            return monthlySpentList;
        }
    }
}
