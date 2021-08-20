package com.cmis.export;

import java.util.List;

public class CarThreeHundredData {

	private String risk_code;
	private String risk_score;
	private String share_url;
	

	private List<CarThreeHundredHitRule> hit_rules;

	public String getRisk_code() {
		return risk_code;
	}

	public void setRisk_code(String risk_code) {
		this.risk_code = risk_code;
	}

	public String getRisk_score() {
		return risk_score;
	}

	public void setRisk_score(String risk_score) {
		this.risk_score = risk_score;
	}

	public List<CarThreeHundredHitRule> getHit_rules() {
		return hit_rules;
	}

	public void setHit_rules(List<CarThreeHundredHitRule> hit_rules) {
		this.hit_rules = hit_rules;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	
}
