package com.thunder.journalApplication.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must contain at least 3 characters")
    @Schema(
            description = "Username of the user",
            example = "zz",
            required = true
    )
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Schema(
            description = "Account password (minimum 8 characters)",
            example = "zz@12",
            required = true,
            format = "password"
    )
    private String password;

}
