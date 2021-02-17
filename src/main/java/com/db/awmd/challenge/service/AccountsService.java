package com.db.awmd.challenge.service;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferDetails;
import com.db.awmd.challenge.repository.AccountsRepository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountsService {

	@Autowired
	private NotificationService notificationService;

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

//todo: add proper values for accountId
	public boolean transferAmount(TransferDetails transferDetails) {
		// balance in senderAccount

		BigDecimal senderAccBalance = getAccount(transferDetails.getFromAccountId()).getBalance();
		BigDecimal receiverAccBalance = getAccount(transferDetails.getToAccountId()).getBalance();
		BigDecimal updatedSenderBalance = senderAccBalance.subtract(transferDetails.getAmountToTransfer());
		BigDecimal updatedReceiverBalance = receiverAccBalance.add(transferDetails.getAmountToTransfer());
		
		
		accountsRepository.updateAccount(updatedSenderBalance,
				transferDetails.getFromAccountId());
		accountsRepository.updateAccount(updatedReceiverBalance,
				transferDetails.getToAccountId());
//notification to sender
		notificationService.notifyAboutTransfer(
				Account.builder().accountId(transferDetails.getFromAccountId()).balance(updatedSenderBalance).build(),
				"Successfully transferred ");
//notification to receiver
		notificationService.notifyAboutTransfer(
				Account.builder().accountId(transferDetails.getToAccountId()).balance(updatedReceiverBalance).build(),
				"Successfully received ");
		return true;

	}
}
