package org.example.module_4.Controller;
import jakarta.persistence.EntityNotFoundException;
import org.example.module_4.DTO.UserForm;
import org.example.module_4.Entity.User;
import org.example.module_4.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;


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
    private static final UserForm DTO = UserForm.builder()
            .name("Oleg")
            .email("oleg@mail.ru")
            .age(30)
            .build();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void whenGetExistingUser_thenReturnsUser() {
        given(userService.getUserById(EXISTED_ID)).willReturn(USER);
        User result = userController.getUserById(EXISTED_ID);
        assertThat(result)
                .isNotNull()
                .isEqualTo(USER);
    }

    @Test
    void whenGetMissingUser_thenThrows() {
        given(userService.getUserById(MISSING_ID))
                .willThrow(new EntityNotFoundException("Пользователь не найден"));
        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> userController.getUserById(MISSING_ID)
        );
        assertThat(ex.getMessage()).isEqualTo("Пользователь не найден");
    }
    @Test
    void whenAddNewUser_thenCallUserServiceAddUserMethod() {
        userController.addUser(DTO);
        verify(userService).addUser(DTO);
    }

    @Test
    void whenGetAllUsers_thenReturnsAllUsersAndSizeList() {
        given(userService.getAllUsers()).willReturn(List.of(USER));
        List<User> result = userController.getAllUsers();
        assertThat(result)
                .hasSize(1)
                .contains(USER);
    }

    @Test
    void whenDeleteUserById_thenCallUserServiceDeleteUserMethod() {
        userController.deleteUserById(EXISTED_ID);
        verify(userService).deleteUserById(EXISTED_ID);
    }

    @Test
    void whenUpdateUser_thenCallUserServiceUpdateUserMethod() {
        userController.updateUser(DTO, EXISTED_ID);
       verify(userService).updateUser(DTO, EXISTED_ID);
    }
}


