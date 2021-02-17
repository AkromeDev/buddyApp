package com.mycompany.myapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.MyTransaction;
import com.mycompany.myapp.repository.MyTransactionRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.MyTransactionService;

/**
 * Service Implementation for managing {@link MyTransaction}.
 */
@Service
@Transactional
public class MyTransactionServiceImpl implements MyTransactionService {

    private final Logger log = LoggerFactory.getLogger(MyTransactionServiceImpl.class);

    private final MyTransactionRepository myTransactionRepository;

    public MyTransactionServiceImpl(MyTransactionRepository myTransactionRepository) {
        this.myTransactionRepository = myTransactionRepository;
    }

    @Override
    public MyTransaction save(MyTransaction myTransaction) throws Exception {
        log.debug("Request to save MyTransaction : {}", myTransaction);
        myTransaction.setUserid(SecurityUtils.getCurrentUserId().get());
        
        Long actualBalance = myTransactionRepository.checkSenderBalance(myTransaction.getUserid());
        String recieverEmail = myTransactionRepository.checkIfEmailExists(myTransaction.getEmail());
        
	        if (actualBalance > myTransaction.getAmount() && recieverEmail != null) {
	        
		        myTransactionRepository.updateRecieverBalance(myTransaction.getAmount(), myTransaction.getEmail());
		        myTransactionRepository.updateSenderBalance(myTransaction.getAmount(), myTransaction.getUserid());
		        
	        } else {
	        	throw new Exception ("Inssuficient funds or wrong email. Your balance is currently of : " + actualBalance + "€");
	        }
        
	    return myTransactionRepository.save(myTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyTransaction> findAll() {
        log.debug("Request to get all MyTransactions");
        return myTransactionRepository.findAllWithEagerRelationships();
    }

    @Override
	public List<MyTransaction> findAllFromUser(Optional<Long> id) {
    	log.debug("Request to get all MyTransactions from the current user");
    	return myTransactionRepository.findAllFromUser(id.get());
	}

    public Page<MyTransaction> findAllWithEagerRelationships(Pageable pageable) {
        return myTransactionRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MyTransaction> findOne(Long id) {
        log.debug("Request to get MyTransaction : {}", id);
        return myTransactionRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyTransaction : {}", id);
        myTransactionRepository.deleteById(id);
    }

}
