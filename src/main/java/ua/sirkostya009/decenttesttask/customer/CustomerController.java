package ua.sirkostya009.decenttesttask.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.decenttesttask.exception.NotFoundException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository repository;

    @PostMapping("/api/customers")
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto dto) {
        var customer = repository.save(Customer.builder()
                .created(System.currentTimeMillis())
                .updated(System.currentTimeMillis())
                .fullName(dto.fullName())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .isActive(true)
                .build());

        return CustomerDto.of(customer);
    }

    @GetMapping("/api/customers")
    public List<CustomerDto> getCustomers() {
        return repository.getActiveCustomers().stream()
                .map(CustomerDto::of)
                .toList();
    }

    @GetMapping("/api/customers/{id}")
    public CustomerDto getCustomer(@PathVariable long id) {
        var customer = repository.getActiveCustomer(id).orElseThrow(NotFoundException::new);

        return CustomerDto.of(customer);
    }

    @PutMapping("/api/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable long id, @Valid @RequestBody CustomerUpdateDto dto) {
        var customer = repository.getActiveCustomer(id).orElseThrow(NotFoundException::new);

        customer.setUpdated(System.currentTimeMillis());
        customer.setFullName(dto.fullName());
        customer.setPhoneNumber(dto.phoneNumber());
        repository.save(customer);

        return CustomerDto.of(customer);
    }

    @DeleteMapping("/api/customers/{id}")
    public void deleteCustomer(@PathVariable long id) {
        repository.markCustomerAsDeleted(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, ?>> handleValidationException(MethodArgumentNotValidException e) {
        var body = Map.of(
                "error", e.getBindingResult().getAllErrors().stream().map(error -> Map.of(
                        "message", error.getDefaultMessage(),
                        "field", ((FieldError) error).getField()
                ))
        );

        return ResponseEntity.badRequest().body(body);
    }
}
