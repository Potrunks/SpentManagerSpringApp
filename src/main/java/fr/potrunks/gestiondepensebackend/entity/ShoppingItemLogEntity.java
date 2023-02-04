package fr.potrunks.gestiondepensebackend.entity;

import fr.potrunks.gestiondepensebackend.resources.ShoppingItemLogAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shopping_item_log")
@DiscriminatorValue("shopping_item")
public class ShoppingItemLogEntity extends LogEntity {

    @Column(name = "action")
    private ShoppingItemLogAction action;
}
