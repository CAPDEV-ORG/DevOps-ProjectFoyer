package tn.esprit.tpfoyer;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer. OrderAnnotation.class)
@SpringBootTest
 class FoyerServiceImplTest {

    @Autowired
    private FoyerRepository foyerRepository;
    @Autowired
    private FoyerServiceImpl foyerserviceimpl;

    @Autowired
    private UniversiteRepository universiteRepository;
    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private EtudiantServiceImpl etudiantserviceimpl;


    @Test
     void testaddFoyer(){
        // Arrange
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Test Junit")
                .capaciteFoyer(50)
                .build();

        // Act
        Foyer savedFoyer = foyerserviceimpl.addFoyer(foyer);

        // Assert
        assertTrue(savedFoyer.getIdFoyer() > 0, "L'ID du foyer enregistré doit être supérieur à 0");
        assertEquals(foyer.getNomFoyer(), savedFoyer.getNomFoyer());
        assertEquals(foyer.getCapaciteFoyer(), savedFoyer.getCapaciteFoyer());
    }

    @Test
     void testAddEtudiant() {
        // Arrange
        Etudiant etudiant = Etudiant.builder()
                .nomEtudiant("etudiant Test Junit")
                .prenomEtudiant("test")
                .cinEtudiant(14500925)
                .dateNaissance(LocalDate.of(2002, 2, 14))
                .build();
        // Act
        Etudiant savedEtudiant = etudiantserviceimpl.addEtudiant(etudiant);

        // Assert
        assertNotNull(savedEtudiant, "L'étudiant sauvegardé ne doit pas être null");
        assertEquals("etudiant Test Junit", savedEtudiant.getNomEtudiant(), "Le nom de l'étudiant sauvegardé ne correspond pas");
        assertEquals(LocalDate.of(2002, 2, 14), savedEtudiant.getDateNaissance(), "La date de naissance de l'étudiant sauvegardé ne correspond pas");
    }
}
