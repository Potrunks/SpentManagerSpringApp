package fr.potrunks.gestiondepensebackend.factory.impl;

import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.factory.ISpentFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SpentFactory implements ISpentFactory {
    @Autowired
    private SpentIBusiness spentBusiness;

    @Override
    public void spentDestructor(List<Long> spentIdToDestroy) {
        log.info("Start method spentDestructor() in SpentFactory");
        for (Long idSpent: spentIdToDestroy) {
            spentBusiness.deleteSpent(idSpent);
        }
        log.info("End method spentDestructor() in SpentFactory");
    }
}
