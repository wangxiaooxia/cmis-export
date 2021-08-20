package com.cmis.export;

import java.util.List;


public class CarThreeHundredHitRule {

	private String rule_code;
	private String rule_name;
	private String rule_score;
	private List<CarThreeHundredRuleDetail> rule_detail;
//	private JsonArray rule_detail;

	public String getRule_code() {
		return rule_code;
	}

	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getRule_score() {
		return rule_score;
	}

	public void setRule_score(String rule_score) {
		this.rule_score = rule_score;
	}

//	public JsonArray getRule_detail() {
//		return rule_detail;
//	}
//
//	public void setRule_detail(JsonArray rule_detail) {
//		this.rule_detail = rule_detail;
//	}

	public List<CarThreeHundredRuleDetail> getRule_detail() {
		return rule_detail;
	}

	public void setRule_detail(List<CarThreeHundredRuleDetail> rule_detail) {
		this.rule_detail = rule_detail;
	}

}
