package tn.esprit.tpfoyer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplMockTest {
    @Mock
    EtudiantRepository etudiantRepository;
    @InjectMocks
    EtudiantServiceImpl etudiantServiceimpl;

    @Test
    public void testModifyEtudiant() {
        // Arrange
        Etudiant existingEtudiant = Etudiant.builder()
                .idEtudiant(1L)
                .nomEtudiant("Ancien Nom")
                .prenomEtudiant("Ancien Prenom")
                .cinEtudiant(14500925)
                .dateNaissance(LocalDate.of(2002, 2, 14))
                .build();

        Etudiant modifiedEtudiant = Etudiant.builder()
                .idEtudiant(1L)
                .nomEtudiant("Nouveau Nom")
                .prenomEtudiant("Nouveau Prenom")
                .cinEtudiant(14500925)
                .dateNaissance(LocalDate.of(2002, 2, 14))
                .build();

        // Simulation du comportement du repository
        when(etudiantRepository.save(modifiedEtudiant)).thenReturn(modifiedEtudiant);

        // Act
        Etudiant result = etudiantServiceimpl.modifyEtudiant(modifiedEtudiant);

        // Assert
        assertNotNull(result, "L'étudiant modifié ne doit pas être null");
        assertEquals("Nouveau Nom", result.getNomEtudiant(), "Le nom de l'étudiant modifié ne correspond pas");
        assertEquals("Nouveau Prenom", result.getPrenomEtudiant(), "Le prénom de l'étudiant modifié ne correspond pas");

        // Verify
        verify(etudiantRepository).save(modifiedEtudiant);
    }


}
