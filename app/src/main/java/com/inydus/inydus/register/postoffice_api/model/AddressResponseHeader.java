package com.inydus.inydus.register.postoffice_api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "cmmMsgHeader")
public class AddressResponseHeader {

    @Element(name = "totalCount", required = false)
    public int totalCount;

    @Element(name = "countPerPage", required = false)
    public int countPerPage;

    @Element(name = "totalPage", required = false)
    public int totalPage;

    @Element(name = "currentPage", required = false)
    public int currentPage;

    @Element(name = "requestMsgId", required = false)
    public String requestMsgId;

    @Element(name = "responseMsgId", required = false)
    public String responseMsgId;

    @Element(name = "responseTime", required = false)
    public String responseTime;

    @Element(name = "successYN", required = false)
    public String successYN;

    @Element(name = "returnCode", required = false)
    public String returnCode;

    @Element(name = "errMsg", required=false)
    public String errMsg;

    public AddressResponseHeader() {
    }
}
