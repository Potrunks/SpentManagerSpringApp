package fr.potrunks.gestiondepensebackend.entity;

import fr.potrunks.gestiondepensebackend.resources.ShoppingItemLogAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("shopping_item")
public class ShoppingItemLogEntity extends LogEntity {

    @Column(name = "action")
    private ShoppingItemLogAction action;
}
