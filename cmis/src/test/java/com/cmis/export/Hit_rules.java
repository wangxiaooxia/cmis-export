/**
  * Copyright 2021 bejson.com 
  */
package com.cmis.export;
import java.util.List;

/**
 * Auto-generated: 2021-08-10 15:35:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Hit_rules {

    private String rule_code;
    private String rule_name;
    private int rule_score;
    private List<List<Rule_detail>> rule_detail;
    public void setRule_code(String rule_code) {
         this.rule_code = rule_code;
     }
     public String getRule_code() {
         return rule_code;
     }

    public void setRule_name(String rule_name) {
         this.rule_name = rule_name;
     }
     public String getRule_name() {
         return rule_name;
     }

    public void setRule_score(int rule_score) {
         this.rule_score = rule_score;
     }
     public int getRule_score() {
         return rule_score;
     }

    public void setRule_detail(List<List<Rule_detail>> rule_detail) {
         this.rule_detail = rule_detail;
     }
     public List<List<Rule_detail>> getRule_detail() {
         return rule_detail;
     }

}