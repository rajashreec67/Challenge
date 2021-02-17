package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class TransferDetails {
	
	private String fromAccountId;
	
	private String toAccountId;
	
	private BigDecimal amountToTransfer;

}
