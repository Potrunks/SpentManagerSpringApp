package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.model.SpentCategory;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpentCategoryBusiness implements SpentCategoryIBusiness {

    @Autowired
    private SpentCategoryIRepository spentCategoryRepository;

    @Override
    public SpentCategoryEntity findById(Long idSpentCategorySelected) {
        log.info("Searching spent category id {}", idSpentCategorySelected);
        SpentCategoryEntity spentCategoryEntity = spentCategoryRepository.getById(idSpentCategorySelected);
        return spentCategoryEntity;
    }

    @Override
    public List<SpentCategory> getAll() {
        List<SpentCategoryEntity> spentCategoryEntities = spentCategoryRepository.findAll(Sort.by("nameSpentCategory"));
        List<SpentCategory> spentCategories = spentCategoryEntities
                .stream()
                .map(spentCategory -> new SpentCategory(
                        spentCategory.getIdSpentCategory(),
                        spentCategory.getNameSpentCategory()))
                .collect(Collectors.toList());
        return spentCategories;
    }

    @Override
    public Boolean verifySpentCategoryExist(String spentCategoryName) {
        log.warn("Start to verify if spent category " + spentCategoryName + " already exist in database...");
        if (spentCategoryRepository.findByNameSpentCategory(spentCategoryName) == null) {
            log.warn("The spent category " + spentCategoryName + " don't exist !!!");
            return false;
        }
        log.warn("The spent category " + spentCategoryName + " already exist !!!");
        return true;
    }

    @Override
    public Boolean createNewSpentCategories(List<String> spentCategoryNameList) {
        log.warn("Add list of spent category in the database...");
        Boolean success = true;
        for (String category : spentCategoryNameList
        ) {
            if (!verifySpentCategoryExist(category)) {
                log.warn("Add the spent category " + category + " ...");
                SpentCategoryEntity spentCategoryEntity = new SpentCategoryEntity();
                spentCategoryEntity.setNameSpentCategory(category);
                spentCategoryEntity = spentCategoryRepository.save(spentCategoryEntity);
                if (spentCategoryEntity.getIdSpentCategory() == null){
                    log.error("Error during spent category creation : " + category);
                    success = false;
                } else {
                    log.warn("The spent category " + category + " created !!!");
                }
            }
        }
        return success;
    }
}
