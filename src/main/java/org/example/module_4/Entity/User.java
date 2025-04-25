package org.example.module_4.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя может содержить от 2 до 50 симвлов")
    @Column(name = "name", nullable = false)
    private String name;
    @Email(message = "Неккоретный email1")
    @Column(name = "email", unique = true)
    private String email;
    @Check(constraints = "age >= 0 AND age <=120")
    private Integer age;
    @Column(name = "date")
    @CreationTimestamp
    private LocalDate created_at;
}


