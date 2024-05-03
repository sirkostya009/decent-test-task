package ua.sirkostya009.decenttesttask.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Modifying
    @Query("update customer c set c.isActive = false where c.id = :id")
    void markCustomerAsDeleted(long id);

    @Query("select c from customer c where c.isActive = true")
    List<Customer> getActiveCustomers();

    @Query("select c from customer c where c.id = :id and c.isActive = true")
    Optional<Customer> getActiveCustomer(long id);
}
