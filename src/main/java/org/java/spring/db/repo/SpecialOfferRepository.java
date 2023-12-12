package org.java.spring.db.repo;


import java.util.List;

import org.java.spring.db.pojo.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Integer> {
	
}
