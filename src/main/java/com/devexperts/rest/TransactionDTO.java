package com.devexperts.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDTO {

	@JsonProperty("source_id")
	private Long sourceId;
	@JsonProperty("target_id")
	private Long targetId;
	@JsonProperty("amount")
	private Double amount;

	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
