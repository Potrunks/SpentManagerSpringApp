package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.IMonthlySpentBusiness;
import fr.potrunks.gestiondepensebackend.model.MonthlySpent;
import fr.potrunks.gestiondepensebackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://spent-manager-react-app.herokuapp.com/"})
@RestController
@RequestMapping("/spentmanager/monthlyspent/")
public class MonthlySpentController {

    @Autowired
    private IMonthlySpentBusiness monthlySpentBusiness;

    /**
     * Create new monthly spent
     * @param monthlySpentInput
     * @return ResponseEntity with the success result
     */
    @PostMapping("/new")
    public ResponseEntity<String> createNewMonthlySpent(@RequestBody MonthlySpent monthlySpentInput) {
        String error = monthlySpentBusiness.createMonthlySpent(monthlySpentInput.getIdUserCreator(), monthlySpentInput);
        return ResponseEntity.ok(error);
    }

    /**
     * Update monthly spent
     * @param monthlySpentInput
     * @return ResponseEntity with the success result
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateMonthlySpent(@RequestBody MonthlySpent monthlySpentInput) {
        String error = monthlySpentBusiness.updateMonthlySpent(monthlySpentInput);
        return ResponseEntity.ok(error);
    }

    /**
     * Delete monthly spent
     * @param monthlySpentToDelete
     * @return ResponseEntity with the success result
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMonthlySpent(@RequestBody MonthlySpent monthlySpentToDelete) {
        String error = monthlySpentBusiness.deleteMonthlySpentById(monthlySpentToDelete.getIdMonthlySpent());
        return ResponseEntity.ok(error);
    }

    /**
     * Fetch all monthly spent by user connected
     * @param userConnected
     * @return List of monthly spent of the user connected
     */
    @PostMapping("/getAll/byUserConnected")
    public ResponseEntity<List<MonthlySpent>> fetchAllByUser(@RequestBody User userConnected) {
        List<MonthlySpent> result = monthlySpentBusiness.getAllByIdUser(userConnected.getIdUser());
        return ResponseEntity.ok(result);
    }

    /**
     * Fetch one monthly spent
     * @param monthlySpentToFetch
     * @return Monthly spent wanted
     */
    @GetMapping("/getOne")
    public ResponseEntity<MonthlySpent> fetchOne(@RequestBody MonthlySpent monthlySpentToFetch) {
        MonthlySpent result = monthlySpentBusiness.getById(monthlySpentToFetch.getIdMonthlySpent());
        return ResponseEntity.ok(result);
    }

    /**
     * Transform monthly spent into a spent in order to add into DataBase
     * @param monthlySpentToTransformList
     * @param userTransformingMonthlySpent
     * @return ResponseEntity with the success result
     */
    @PostMapping("/transformInto/spent")
    public ResponseEntity<String> transformIntoSpent(@RequestBody List<MonthlySpent> monthlySpentToTransformList, @RequestBody User userTransformingMonthlySpent) {
        String error = monthlySpentBusiness.becomeSpent(monthlySpentToTransformList, userTransformingMonthlySpent.getIdUser());
        return ResponseEntity.ok(error);
    }
}
