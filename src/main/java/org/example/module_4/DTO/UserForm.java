package org.example.module_4.DTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserForm {
    private String name;
    private String email;
    private int age;
}
