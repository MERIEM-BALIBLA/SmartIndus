import com.example.smartindus.DTO.Tache;
import com.example.smartindus.DTO.TacheMapper;
import com.example.smartindus.domain.TacheEntity;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.repository.InterventionRepository;
import com.example.smartindus.repository.TacheRepository;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.implementation.TacheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class TacheServiceImplTest {

    @InjectMocks
    private TacheServiceImpl tacheService;

    @Mock
    private TacheRepository tacheRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InterventionRepository interventionRepository;
    @Mock
    private TacheMapper tacheMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllTaches() {
        TacheEntity entity = new TacheEntity();
        Tache dto = new Tache();
        Page<TacheEntity> page = new PageImpl<>(List.of(entity));

        when(tacheRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(tacheMapper.toDto(entity)).thenReturn(dto);

        Page<Tache> result = tacheService.findAllTaches(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        verify(tacheRepository).findAll(any(PageRequest.class));
    }

    @Test
    void testGetTacheById_ShouldReturnTache() {
        UUID id = UUID.randomUUID();
        TacheEntity entity = new TacheEntity();
        Tache dto = new Tache();

        when(tacheRepository.findById(id)).thenReturn(Optional.of(entity));
        when(tacheMapper.toDto(entity)).thenReturn(dto);

        Tache result = tacheService.getTacheById(id);

        assertNotNull(result);
        verify(tacheRepository).findById(id);
    }

    @Test
    void testCreateTache_ShouldReturnSavedTache() {
        Tache input = new Tache();
        input.setUser_id(UUID.randomUUID().toString());

        TacheEntity entity = new TacheEntity();
        UserEntity user = new UserEntity();
        TacheEntity saved = new TacheEntity();
        Tache dto = new Tache();

        when(tacheMapper.toEntity(input)).thenReturn(entity);
        when(userRepository.findById(UUID.fromString(input.getUser_id()))).thenReturn(Optional.of(user));
        when(tacheRepository.save(entity)).thenReturn(saved);
        when(tacheMapper.toDto(saved)).thenReturn(dto);

        Tache result = tacheService.createTache(input);

        assertNotNull(result);
        verify(tacheRepository).save(entity);
    }

    @Test
    void testDeleteTache_ShouldCallDelete() {
        UUID id = UUID.randomUUID();
        TacheEntity entity = new TacheEntity();

        when(tacheRepository.findById(id)).thenReturn(Optional.of(entity));

        tacheService.deleteTache(id);

        verify(tacheRepository).delete(entity);
    }
}
