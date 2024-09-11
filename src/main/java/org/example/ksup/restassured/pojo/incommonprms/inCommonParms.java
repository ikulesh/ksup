package org.example.ksup.restassured.pojo.incommonprms;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement
public class inCommonParms {
    private String userID;
    private String branchNumber;
    private String externalSystemCode;
    private String externalUserCode;
    private List<inCommonParmsExt> inCommonParmsExt;
}
