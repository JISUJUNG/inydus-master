package com.inydus.inydus.register.postoffice_api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "NewAddressListResponse")
public class AddressResponse {

    @ElementList(name = "newAddressListAreaCdSearchAll", entry = "newAddressListAreaCdSearchAll", inline = true, required = false)
    public List<AddressItem> itemlist;

    @Element(name = "cmmMsgHeader")
    public AddressResponseHeader addressResponseHeader;

    public AddressResponse() {
    }
}
