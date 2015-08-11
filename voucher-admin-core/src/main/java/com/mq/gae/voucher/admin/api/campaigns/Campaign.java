package com.mq.gae.voucher.admin.api.campaigns;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import com.mq.gae.voucher.admin.api.communities.Community;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/4/2015
 */
@Entity
public class Campaign {

    public static Set<String> sortFields = new HashSet<String>(){{
        add("name");
        add("-name");
    }};

    @Id
    public Long id;
    @Index
    public String name;
    @JsonIgnore
    @Parent
    public Ref<Community> communityRef;
    @Index
    public Long productId;
    public Boolean isActive;
    @Unindex
    public String deeplinkUrl;
    @Unindex
    public String redemptionPageUrl;

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", communityRef=" + communityRef +
                ", productId=" + productId +
                ", isActive=" + isActive +
                ", deeplinkUrl='" + deeplinkUrl + '\'' +
                ", redemptionPageUrl='" + redemptionPageUrl + '\'' +
                '}';
    }
}
