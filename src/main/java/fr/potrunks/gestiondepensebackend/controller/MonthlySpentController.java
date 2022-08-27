package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.IMonthlySpentBusiness;
import fr.potrunks.gestiondepensebackend.model.MonthlySpent;
import fr.potrunks.gestiondepensebackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * @return ResponseEntity with the success result
     */
    @DeleteMapping("/delete/{idMonthlySpent}")
    public ResponseEntity<String> deleteMonthlySpent(@PathVariable Long idMonthlySpent) {
        String error = monthlySpentBusiness.deleteMonthlySpentById(idMonthlySpent);
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
     * @return Monthly spent wanted
     */
    @GetMapping("/getOne/{idMonthlySpent}")
    public ResponseEntity<MonthlySpent> fetchOne(@PathVariable Long idMonthlySpent) {
        MonthlySpent result = monthlySpentBusiness.getById(idMonthlySpent);
        return ResponseEntity.ok(result);
    }

    /**
     * Transform monthly spent into a spent in order to add into DataBase
     */
    @PostMapping("/transformInto/spent/{idUserConnected}/{idMonthlySpentToTransform}")
    public ResponseEntity<String> transformIntoSpent(@PathVariable Long idMonthlySpentToTransform, @PathVariable Long idUserConnected) {
        List<MonthlySpent> monthlySpentToTransformList = new ArrayList<>();
        MonthlySpent monthlySpentToTransform = monthlySpentBusiness.getById(idMonthlySpentToTransform);
        monthlySpentToTransformList.add(monthlySpentToTransform);
        String error = monthlySpentBusiness.becomeSpent(monthlySpentToTransformList, idUserConnected);
        return ResponseEntity.ok(error);
    }
}
