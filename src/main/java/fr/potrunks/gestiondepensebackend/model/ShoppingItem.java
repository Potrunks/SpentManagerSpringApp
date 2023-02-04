package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItem {

    private String name;

    private Integer quantity;

    private Long userIdConcerned;

    private LocalDate date;

    private String action;
}
