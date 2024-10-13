package org.example.ksup.pojo.inprms;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Part of the request body inParms.
 * All params contains in request body.
 */
@Data
@XmlRootElement(name = "inParms")
public class InParms {
    private String fl8pck;
    private String fl8sts;
    private String fl1grp;
    private String fl1pro;
    private String fl5sts;
    private String fl5tpr;
    private Integer prmmod;
    private String hrymod;
    private attrlst attrlst;
    private fllpfllst fllpfllst;
    private String alg;
    private String regcd;
    private String chancd;
    private Integer pagesize;
    private Integer pageindex;
    private String tp6cd;
}

