package com.frss.model.mapping;

public class FrssClientReview {
	private long faultId;
	private String client;
	private int quality;
	private int reviewWay;
	private String contact;
	private String discription;
	
	public FrssClientReview() {
		
	}
	
	public long getFaultId(){
		return this.faultId;
	}
	public void setFaultId(long faultId) {
		this.faultId = faultId;
	}
	
	public String getClient() {
		return this.client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	public int getQuality() {
		return this.quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	public int getReviewWay() {
		return this.reviewWay;
	}
	public void setReviewWay(int reviewWay) {
		this.reviewWay = reviewWay;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDiscription() {
		return this.discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
}
