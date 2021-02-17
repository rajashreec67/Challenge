package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  void clearAccounts();
  
	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("update Account account SET account.balance = :updatedBalance WHERE accountId = :accountId"
	 * )
	 */
  int updateAccount(BigDecimal updatedBalance, String accountID
		  );
}
