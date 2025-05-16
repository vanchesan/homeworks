package org.example.module_4.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.module_4.DTO.UserForm;
import org.example.module_4.Entity.User;
import org.example.module_4.Service.UserService;

import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Tag(name = "POST-запросы")
    @Operation(
            summary = "добавляем пользователя в базу",
            description = "получаем DTO пользователя и сохраняем объект в базу"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно добавлен", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Введены не корректные данные")
    })

    @PostMapping("/api/add")
    public void addUser(@Valid @RequestBody UserForm user) {
        userService.addUser(user);

    }

    @Tag(name = "GET-запросы")
    @Operation(
            summary = "получаем всех пользователей из базы",
            description = "получаем всех пользователей из базы через userService"
    )
    @ApiResponse(responseCode = "200", description = "Данные успешно получены")
    @GetMapping("/api/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @Tag(name = "GET-запросы")
    @Operation(
            summary = "получаем пользователя по ID",
            description = "передаем в параметрах ID пользователя и возвращаем его"
    )
    @GetMapping("/api/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "пользователь получен успешно"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public EntityModel<User> getUserById(@Parameter(description = "ID пользователя") @PathVariable Integer id) {
        User user = userService.getUserById(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
    }

    @Tag(name = "DELETE - запрос")
    @Operation(
            summary = "удаляем пользователя из базы",
            description = "передаем в параметрах ID пользователя и удаляем его из базы"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "пользователь удален успешно"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/api")
    public void deleteUserById(@Parameter(description = "ID пользователя") @RequestParam Integer id) {
        userService.deleteUserById(id);
    }

    @Tag(name = "POST-запросы")
    @Operation(
            summary = "изменяем параметры пользователя",
            description = "передаем в параметрах  изменённое DTO пользователя и меняем данные в переданном ID в базе "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "данные успешно изменены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("api/{id}")
    public void updateUser(@RequestBody UserForm user, @Parameter(description = "ID пользователя") @PathVariable int id) {
        userService.updateUser(user, id);
    }
}
