package ua.sirkostya009.decenttesttask.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerUpdateDto(
        @NotNull
        @Size(min = 2, max = 50)
        String fullName,
        @Pattern(regexp = "^\\+\\d{6,14}$")
        String phoneNumber
) {
}
