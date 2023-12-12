package org.java.spring.db.serv;

import java.util.List;

import org.java.spring.db.pojo.SpecialOffer;
import org.java.spring.db.repo.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService {
	
	@Autowired
	private SpecialOfferRepository specialOfferRepository;
	
	public List<SpecialOffer> findAll() {
		
		return specialOfferRepository.findAll();
	}
	public SpecialOffer findById(int id) {
		
		return specialOfferRepository.findById(id).get();
	}
	public void save(SpecialOffer specialOffer) {
		
		specialOfferRepository.save(specialOffer);
	}
	public void delete(SpecialOffer specialOffer) {
			
		specialOfferRepository.delete(specialOffer);
	}
}
