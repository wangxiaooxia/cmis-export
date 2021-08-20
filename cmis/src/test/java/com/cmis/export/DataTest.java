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
public class DataTest {

    private String risk_code;
    private double risk_score;
    private List<Hit_rules> hit_rules;
    private String share_url;
    public void setRisk_code(String risk_code) {
         this.risk_code = risk_code;
     }
     public String getRisk_code() {
         return risk_code;
     }

    public void setRisk_score(double risk_score) {
         this.risk_score = risk_score;
     }
     public double getRisk_score() {
         return risk_score;
     }

    public void setHit_rules(List<Hit_rules> hit_rules) {
         this.hit_rules = hit_rules;
     }
     public List<Hit_rules> getHit_rules() {
         return hit_rules;
     }

    public void setShare_url(String share_url) {
         this.share_url = share_url;
     }
     public String getShare_url() {
         return share_url;
     }

}