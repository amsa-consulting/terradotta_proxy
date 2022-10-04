package com.terradotta.terradotta_proxy.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Terradotta {
    /*
    @JsonProperty("SfContactID")
    private String SfContactID;
    @JsonProperty("passport")
    private String passport;
    @JsonProperty("bankStatement")
    private String bankStatement;
    @JsonProperty("RecSponsorAffidavit")
    private String RecSponsorAffidavit;
    @JsonProperty("RecPrevI20")
    private String RecPrevI20;
    @JsonProperty("RecI94")
    private String RecI94;
    @JsonProperty("RecVisa")
    private String RecVisa;
    @JsonProperty("IssueI20")
    private String IssueI20;
    @JsonProperty("SEVISFeePaid")
    private String SEVISFeePaid;
    */

    //the above variables were defined on the first version we developed, then
    //the terradota team contacted us and requested the variables to be changed as shown below

    @JsonProperty("SfContactID")
    private String SfContactID;
    @JsonProperty("AccessGranted")
    private String AccessGranted;
    @JsonProperty("FirstLoggedIn")
    private String FirstLoggedIn;
    @JsonProperty("I20RequestCreated")
    private String I20RequestCreated;
    @JsonProperty("IssueI20")
    private String IssueI20;
    @JsonProperty("VisaIssueDate")
    private String VisaIssueDate;
    }

