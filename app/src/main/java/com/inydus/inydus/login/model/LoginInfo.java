package com.inydus.inydus.login.model;

import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;

public class LoginInfo {

    public String user_id;
    public String user_name;
    public String user_type;

    public SitterProfile sitter_profile;
    public ChildProfile child_profile;
}
