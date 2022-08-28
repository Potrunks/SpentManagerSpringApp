package fr.potrunks.gestiondepensebackend.factory;

import java.util.List;

public interface ISpentFactory {
    /**
     * Delete spent with a list of ID
     */
    void spentDestructor(List<Long> spentIdToDestroy);
}
