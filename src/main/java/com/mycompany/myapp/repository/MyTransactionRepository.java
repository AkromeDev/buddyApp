package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.MyTransaction;

/**
 * Spring Data  repository for the MyTransaction entity.
 */
@Repository
public interface MyTransactionRepository extends JpaRepository<MyTransaction, Long> {

    @Query(value = "select distinct myTransaction from MyTransaction myTransaction left join fetch myTransaction.transactionHistories",
        countQuery = "select count(distinct myTransaction) from MyTransaction myTransaction")
    Page<MyTransaction> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct myTransaction from MyTransaction myTransaction left join fetch myTransaction.transactionHistories")
    List<MyTransaction> findAllWithEagerRelationships();

    @Query("select myTransaction from MyTransaction myTransaction left join fetch myTransaction.transactionHistories where myTransaction.id =:id")
    Optional<MyTransaction> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select MY_TRANSACTION.* from MY_TRANSACTION MY_TRANSACTION where MY_TRANSACTION.userid=:id", nativeQuery = true)
	List<MyTransaction> findAllFromUser(@Param("id")Long id);
    
    @Query(value = "select balance from BUDDY where buddy.id=:userid", nativeQuery = true)
	Long checkSenderBalance(@Param("userid")Long userid);
    
    @Query(value = "select email from BUDDY where buddy.email=:email", nativeQuery = true)
    String checkIfEmailExists(@Param("email")String email);
    
    @Modifying
    @Query(value = "update buddy set balance = balance+:amount where buddy.email=:email", nativeQuery = true)
	void updateRecieverBalance(@Param("amount") Long amount, @Param("email") String email);
    
    @Modifying
    @Query(value = "update buddy set balance = balance-:amount where buddy.id=:userid", nativeQuery = true)
	void updateSenderBalance(@Param("amount") Long amount, @Param("userid") Long userid);

}
