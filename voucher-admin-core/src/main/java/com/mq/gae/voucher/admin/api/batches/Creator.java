package com.mq.gae.voucher.admin.api.batches;

import com.googlecode.objectify.annotation.Index;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/11/2015
 */
public class Creator {
    @Index
    public String userName;


    @Override
    public String toString() {
        return "Creator{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
