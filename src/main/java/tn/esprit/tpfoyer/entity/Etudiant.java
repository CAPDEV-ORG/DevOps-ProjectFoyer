package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder

@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idEtudiant;

    String nomEtudiant;
    String prenomEtudiant;
    long cinEtudiant;
    LocalDate dateNaissance;

    @ManyToMany(mappedBy = "etudiants")
    Set<Reservation> reservations;

}



