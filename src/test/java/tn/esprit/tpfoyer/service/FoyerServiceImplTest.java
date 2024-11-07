package tn.esprit.tpfoyer.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoyerServiceImplTest {



    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @Test
    public void testRetrieveAllFoyers() {
        Foyer foyer1 = new Foyer(1L, "Foyer A", 100, null, null);
        Foyer foyer2 = new Foyer(2L, "Foyer B", 150, null, null);
        List<Foyer> foyers = Arrays.asList(foyer1, foyer2);

        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerService.retrieveAllFoyers();
        assertEquals(2, result.size());
        assertEquals("Foyer A", result.get(0).getNomFoyer());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveFoyer() {
        Foyer foyer = new Foyer(1L, "Foyer A", 100, null, null);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.retrieveFoyer(1L);
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddFoyer() {
        Foyer foyer = new Foyer(null, "Foyer A", 100, null, null);
        Foyer savedFoyer = new Foyer(1L, "Foyer A", 100, null, null);

        when(foyerRepository.save(foyer)).thenReturn(savedFoyer);

        Foyer result = foyerService.addFoyer(foyer);
        assertEquals(1L, result.getIdFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testModifyFoyer() {
        Foyer foyer = new Foyer(1L, "Updated Foyer", 200, null, null);

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.modifyFoyer(foyer);
        assertEquals("Updated Foyer", result.getNomFoyer());
        assertEquals(200, result.getCapaciteFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testRemoveFoyer() {
        Long foyerId = 1L;
        doNothing().when(foyerRepository).deleteById(foyerId);

        foyerService.removeFoyer(foyerId);
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}
