package fr.potrunks.gestiondepensebackend.entity;

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
@Table(name = "error_log")
@DiscriminatorValue("error")
public class ErrorLogEntity extends LogEntity{

    @Column(name = "message")
    private String message;

    @Column(name = "method_concerned")
    private String methodConcerned;
}
