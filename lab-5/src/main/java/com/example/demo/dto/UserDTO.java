package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Noumert on 13.08.2021.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private Long id;
    @NotNull
    private String email;
    @Size(min = 8, max = 16)
    @NotNull
    private String password;
}

