package ua.sirkostya009.decenttesttask.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        long id,
        @NotNull
        @Size(min = 2, max = 50)
        String fullName,
        @NotNull
        @Pattern(regexp = "^.*@.*$")
        @Size(min = 2, max = 100)
        String email,
        @Pattern(regexp = "^\\+\\d{6,14}$")
        String phoneNumber
) {
    public static CustomerDto of(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
