import com.example.smartindus.DTO.User;
import com.example.smartindus.DTO.UserMapper;
import com.example.smartindus.domain.UserEntity;
import com.example.smartindus.domain.enums.Role;
import com.example.smartindus.repository.UserRepository;
import com.example.smartindus.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final UUID TEST_ID = UUID.randomUUID();
    private final String VALID_EMAIL = "test@example.com";
    private final String VALID_CIN = "AB12345";
    private final String VALID_PHONE = "0612345678";

    private User createValidUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail(VALID_EMAIL);
        user.setCin(VALID_CIN);
        user.setPhone(VALID_PHONE);
        user.setRole(Role.ADMIN);
        return user;
    }

    private UserEntity createValidUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(TEST_ID);
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setEmail(VALID_EMAIL);
        entity.setCin(VALID_CIN);
        entity.setPhone(VALID_PHONE);
        entity.setRole(Role.ADMIN);
        return entity;
    }

    @Test
    void createUser_WithInvalidData_ShouldThrowIllegalArgumentException() {
        // Arrange
        User invalidUser = new User();
        invalidUser.setFirstName(""); // Invalid: empty first name

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });

        assertEquals("Le prénom est obligatoire.", exception.getMessage());
    }

    @Test
    void findUser_WithNullId_ShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findUser(null);
        });

        assertEquals("L'ID ne peut pas être null.", exception.getMessage());
    }

    @Test
    void getUserByEmail_ShouldReturnUserEntity() {
        // Arrange
        UserEntity userEntity = createValidUserEntity();
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(userEntity));

        // Act
        Optional<UserEntity> result = userService.getUserByEmail(VALID_EMAIL);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    void validateUser_WithInvalidEmail_ShouldThrowIllegalArgumentException() {
        // Arrange
        User invalidUser = createValidUser();
        invalidUser.setEmail("invalid-email");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });

        assertEquals("Le format de l'email est invalide.", exception.getMessage());
    }

    @Test
    void validateUser_WithInvalidPhone_ShouldThrowIllegalArgumentException() {
        // Arrange
        User invalidUser = createValidUser();
        invalidUser.setPhone("1234"); // Invalid phone format

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });

        assertTrue(exception.getMessage().contains("téléphone"));
    }

    @Test
    void validateUser_WithInvalidCIN_ShouldThrowIllegalArgumentException() {
        // Arrange
        User invalidUser = createValidUser();
        invalidUser.setCin("123"); // Invalid CIN format

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });

        assertEquals("Le format du CIN est invalide.", exception.getMessage());
    }
}