package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.entity.Bloc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        // Creating dummy objects for testing.
        Bloc bloc = new Bloc(1L, "Bloc A");
        chambre1 = new Chambre(1L, 101, TypeChambre.SIMPLE, new HashSet<>(), bloc);
        chambre2 = new Chambre(2L, 102, TypeChambre.DOUBLE, new HashSet<>(), bloc);
    }

    @Test
    public void testRetrieveAllChambres() {
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);
        when(chambreRepository.findAll()).thenReturn(chambres);

        List<Chambre> result = chambreService.retrieveAllChambres();
        assertEquals(2, result.size());
        assertEquals(101, result.get(0).getNumeroChambre());  // Check the number of the first chambre, not the whole object
        verify(chambreRepository, times(1)).findAll();
    }


    @Test
    public void testRetrieveChambre() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre1));

        Chambre result = chambreService.retrieveChambre(1L);
        assertEquals(101, result.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveChambreNotFound() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        Chambre result = chambreService.retrieveChambre(1L);
        assertNull(result);
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddChambre() {
        Chambre chambreToAdd = new Chambre(6, 103, TypeChambre.SIMPLE, new HashSet<>(), new Bloc(1L, "Bloc B"));
        Chambre savedChambre = new Chambre(3L, 103, TypeChambre.SIMPLE, new HashSet<>(), new Bloc(1L, "Bloc B"));

        when(chambreRepository.save(chambreToAdd)).thenReturn(savedChambre);

        Chambre result = chambreService.addChambre(chambreToAdd);
        assertEquals(3L, result.getIdChambre());
        assertEquals(103, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambreToAdd);
    }

    @Test
    public void testModifyChambre() {
        Chambre chambreToModify = new Chambre(1L, 104, TypeChambre.SIMPLE, new HashSet<>(), new Bloc(1L, "Bloc A"));

        when(chambreRepository.save(chambreToModify)).thenReturn(chambreToModify);

        Chambre result = chambreService.modifyChambre(chambreToModify);
        assertEquals(104, result.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambreToModify);
    }

    @Test
    public void testRemoveChambre() {
        Long chambreId = 1L;
        doNothing().when(chambreRepository).deleteById(chambreId);

        chambreService.removeChambre(chambreId);
        verify(chambreRepository, times(1)).deleteById(chambreId);
    }

    @Test
    public void testRecupererChambresSelonTyp() {
        TypeChambre typeChambre = TypeChambre.SIMPLE;
        List<Chambre> chambres = Arrays.asList(chambre1);
        when(chambreRepository.findAllByTypeC(typeChambre)).thenReturn(chambres);

        List<Chambre> result = chambreService.recupererChambresSelonTyp(typeChambre);
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getNumeroChambre());
        verify(chambreRepository, times(1)).findAllByTypeC(typeChambre);
    }

    @Test
    public void testTrouverChambreSelonEtudiant() {
        long cin = 12345L;
        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre1);

        Chambre result = chambreService.trouverchambreSelonEtudiant(cin);
        assertEquals(101, result.getNumeroChambre());
        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }
}
