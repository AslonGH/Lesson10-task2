package uz.pdp.appjparelationship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appjparelationship.entity.Address;

@Repository

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
