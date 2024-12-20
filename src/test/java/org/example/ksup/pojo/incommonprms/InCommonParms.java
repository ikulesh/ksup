package org.example.ksup.pojo.incommonprms;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Part of the request body inCommonParms.
 * All params contains in request body.
 */
@Data
@XmlRootElement(name = "inCommonParms")
public class InCommonParms {
    private String userID;
    private String branchNumber;
    private String externalSystemCode;
    private String externalUserCode;
    private List<InCommonParmsExt> inCommonParmsExt;
}
