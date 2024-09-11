package org.example.ksup.restassured.pojo.inprms;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class inParms {
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

