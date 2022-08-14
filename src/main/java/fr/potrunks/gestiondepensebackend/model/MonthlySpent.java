package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySpent {
    private Long idMonthlySpent;
    private Float valueMonthlySpent;
    private String nameMonthlySpent;
    private String commentMonthlySpent;
    private Long idSpentCategorySelected;
    private String nameMonthlySpentCategory;
    private Boolean isActive;
    private Long idUserCreator;
}
