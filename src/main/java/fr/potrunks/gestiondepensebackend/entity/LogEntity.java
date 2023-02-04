package fr.potrunks.gestiondepensebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "child_object")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userLogged;

    @Column(name = "date")
    private LocalDate date;
}
