package org.example.module_4.Service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.example.module_4.DTO.UserForm;
import org.example.module_4.Entity.User;
import org.example.module_4.Repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;



@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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
            .name("Ivan")
            .email("ivan@mail.ru")
            .age(35)
            .build();

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void whenAddUser_thanCallSaveMethodInUserRepo() {

        given(userRepo.save(any(User.class))).willReturn(USER);
        userService.addUser(DTO);
        then(userRepo).should().save(argThat(user ->
                user.getName().equals(DTO.getName()) &&
                        user.getEmail().equals(DTO.getEmail()) &&
                        user.getAge().equals(DTO.getAge())
        ));
    }

    @Test
    void whenGetAllUsers_thanReturnAllUsersAndSize() {
        given(userRepo.findAll()).willReturn(List.of(USER));
        int result = userService.getAllUsers().size();
       assertEquals(result, 1);
        then(userRepo).should().findAll();

    }

    @Test
    void whenGetUserById_thanReturnUserById() {
        given(userRepo.findById(EXISTED_ID)).willReturn(Optional.of(USER));
        userService.getUserById(EXISTED_ID);
        assertEquals(USER, userService.getUserById(EXISTED_ID));
    }

    @Test
    void whenMissingUser_thanExceptionIsThrown() {
        given(userRepo.findById(MISSING_ID)).willThrow(new EntityNotFoundException("Пользователь не найден"));
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, ()->userService.getUserById(MISSING_ID));
        Assertions.assertThat(ex.getMessage()).isEqualTo("Пользователь не найден");
    }

    @Test
    void whenDeleteUserById_thanCallDeleteMethodInUserRepo() {
        willDoNothing().given(userRepo).deleteById(EXISTED_ID);
        userService.deleteUserById(EXISTED_ID);
        verify(userRepo).deleteById(EXISTED_ID);
    }

    @Test
    void whenUpdateUser_thanCallUpdateMethodInUserRepoAndEquals() {
        given(userRepo.findById(EXISTED_ID)).willReturn(Optional.of(USER));
        userService.updateUser(DTO, EXISTED_ID);
        then(userRepo).should().save(argThat(user -> user.getId() == EXISTED_ID &&
                user.getName().equals(DTO.getName()) &&
                user.getEmail().equals(DTO.getEmail()) &&
                user.getAge() == DTO.getAge()));
    }
}