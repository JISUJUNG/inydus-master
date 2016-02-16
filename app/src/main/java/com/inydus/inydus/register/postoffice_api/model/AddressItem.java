package com.inydus.inydus.register.postoffice_api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "newAddressListAreaCdSearchAll")
public class AddressItem {

    @Element(name = "zipNo", data = true)
    public int zipNo;

    @Element(name = "lnmAdres", data = true)
    public String InmAdres;

    @Element(name = "rnAdres", data = true)
    public String rnAdres;

    public AddressItem() {
    }
}
