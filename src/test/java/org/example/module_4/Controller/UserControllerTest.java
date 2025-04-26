package org.example.module_4.Controller;
import org.example.module_4.Entity.User;
import org.example.module_4.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final int EXISTED_ID = 1;
    private static final int MISSING_ID = 2;

    private static final User USER = User.builder()
            .id(EXISTED_ID)
            .name("Ivan")
            .email("ivan@mail.ru")
            .age(35)
            .created_at(LocalDate.now())
            .build();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void whenGetExistingUser_thenReturnsUser() {
        // Given
        given(userService.getUserById(EXISTED_ID)).willReturn(USER);

        // When
        User result = userController.getUserById(EXISTED_ID);

        // Then
        assertThat(result)
                .isNotNull()
                .isEqualTo(USER);
    }

    @Test
    void whenGetMissingUser_thenThrows() {
        // Given
        given(userService.getUserById(MISSING_ID))
                .willThrow(new IllegalArgumentException("User not found"));

        // When / Then
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userController.getUserById(MISSING_ID)
        );
        assertThat(ex.getMessage()).isEqualTo("User not found");
    }
}


