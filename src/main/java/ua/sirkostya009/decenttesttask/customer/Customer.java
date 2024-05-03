package ua.sirkostya009.decenttesttask.customer;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "customer_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_id_seq", allocationSize = 1) // make sure id incrementing sequence is named customer_id_seq
    private long id;

    @Column(name = "created", nullable = false)
    private long created;

    @Column(name = "updated", nullable = false)
    private long updated;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Override
    public final boolean equals(Object o) {
        return this == o || (o instanceof Customer other && id == other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, fullName, email, phoneNumber, isActive);
    }
}
