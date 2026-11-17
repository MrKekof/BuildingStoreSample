package com.navisdevs.buildingstore.content;

import jakarta.validation.constraints.NotBlank;

public record PageRequest(
        @NotBlank(message = "Поле не может быть пустым") String slug,
        @NotBlank(message = "Поле не может быть пустым") String title ,
        @NotBlank(message = "Поле не может быть пустым") String content

)
{
}
