package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.MonthlySpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlySpentIRepository extends JpaRepository<MonthlySpentEntity, Long> {

    /**
     * find all monthly spent of a user
     * @param userEntityWanted
     * @return List of Monthly Spent of the user wanted
     */
    List<MonthlySpentEntity> findByUserEntity(UserEntity userEntityWanted);

    /**
     * Find all monthly spent of a user and active
     * @param userEntityWanted
     * @return List of monthly spent to the user and active
     */
    List<MonthlySpentEntity> findByUserEntityAndIsActiveTrue(UserEntity userEntityWanted);
}
