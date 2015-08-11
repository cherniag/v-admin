package com.mq.gae.voucher.admin.api;

import com.google.api.server.spi.config.Api;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/11/2015
 */
@Api(name = "voucheradmin",
        version = "v1",
        title = "Private API",
        description = "Private API is used for voucher management",
        scopes = {Constants.EMAIL_SCOPE}, // Access to OAuth2 API to view your email address
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.API_EXPLORER_CLIENT_ID,      // for api explorer
                Constants.SERVICE_ACCOUNT_CLIENT_ID})  // service account client id
public class AbstractEndPoint {
}
