package com.mq.gae.voucher.admin.api.batches;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import com.mq.gae.voucher.admin.api.campaigns.Campaign;

import javax.persistence.Embedded;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Gennadii Cherniaiev
 * Date: 7/23/2015
 */
@Entity
public class Batch implements Serializable {

    public static Set<String> sortFields = new HashSet<String>(){{
        add("name");
        add("-name");
        add("creator.userName");
        add("-creator.userName");
        add("createDate");
        add("-createDate");
        add("generatedCodes");
        add("-generatedCodes");
    }};

    @Id
    Long id;
    @JsonIgnore
    @Parent
    Ref<Campaign> campaignRef;
    @Index
    String name;
    @Index
    Long generatedCodes;
    @Index
    Date createDate;
    @Unindex
    Date startDate;
    @Unindex
    Date endDate;
    @Unindex
    Creator creator;
    @Unindex
    Boolean isActive;

    public Batch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGeneratedCodes() {
        return generatedCodes;
    }

    public void setGeneratedCodes(Long generatedCodes) {
        this.generatedCodes = generatedCodes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Ref<Campaign> getCampaignRef() {
        return campaignRef;
    }

    public void setCampaignRef(Ref<Campaign> campaignRef) {
        this.campaignRef = campaignRef;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", generatedCodes=" + generatedCodes +
                ", createDate=" + createDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", creator='" + creator + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
