package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);

}
