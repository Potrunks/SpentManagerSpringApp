package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpentIRepository extends JpaRepository<SpentEntity, Long> {

    /**
     * Find a list of spent of a user during a period spent
     * @param periodSpentEntity Period concerned
     * @param userEntity User concerned
     * @return List of spent of a user during a period spent
     */
    List<SpentEntity> findByPeriodSpentEntityAndUserEntity(PeriodSpentEntity periodSpentEntity, UserEntity userEntity);

    /**
     * Find a list of spent of a user during a period of spent and match with a category of spent
     * @param userEntity User concerned
     * @param periodSpentEntity Period concerned
     * @param spentCategoryEntity Category concerned
     * @return List of spent of a user during a period of spent and match with a category of spent
     */
    List<SpentEntity> findByUserEntityAndPeriodSpentEntityAndSpentCategoryEntity(UserEntity userEntity, PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    /**
     * Find a list of spent during a period of spent BUT no match with the category given
     * @param periodSpentEntity Period concerned
     * @param spentCategoryEntity Category NONE wanted
     * @return a list of spent during a period of spent BUT no match with the category given
     */
    List<SpentEntity> findByPeriodSpentEntityAndSpentCategoryEntityNot(PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    /**
     * Find a list of spent during a period of spent and match with the category given
     * @param periodSpentEntity Period concerned
     * @param spentCategoryEntity Category concerned
     * @return List of spent during a period of spent and match with the category given
     */
    List<SpentEntity> findByPeriodSpentEntityAndSpentCategoryEntity(PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    /**
     * Find list of spent during a period of spent
     * @param periodSpentEntity Period concerned
     * @return List of spent during a period of spent
     */
    List<SpentEntity> findByPeriodSpentEntity(PeriodSpentEntity periodSpentEntity);

    /**
     * Find a spent entity in a period spent wanted and correspondaing to a monthly spent wanted
     * @param periodSpentEntity
     * @param monthlySpentEntity
     * @return A spent entity with the criteria or null if not found
     */
    SpentEntity findByPeriodSpentEntityAndMonthlySpentEntity(PeriodSpentEntity periodSpentEntity, MonthlySpentEntity monthlySpentEntity);

    /**
     * Find all spent who are monthly spent at the origin
     */
    List<SpentEntity> findByMonthlySpentEntity(MonthlySpentEntity monthlySpentEntity);
}
