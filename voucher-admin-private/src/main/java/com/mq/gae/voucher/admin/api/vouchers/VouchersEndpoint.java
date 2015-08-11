package com.mq.gae.voucher.admin.api.vouchers;

import com.google.api.server.spi.config.*;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.mq.gae.voucher.admin.api.AbstractEndpoint;
import com.mq.gae.voucher.admin.api.AuthorizationService;
import com.mq.gae.voucher.admin.api.Constants;

import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/4/2015
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
//@ApiReference(AbstractEndpoint.class)
public class VouchersEndpoint {
    static final Logger logger = Logger.getLogger(VouchersEndpoint.class.getName());
    AuthorizationService authorizationService = AuthorizationService.getInstance();
    VoucherService voucherService = VoucherService.getInstance();

    @ApiMethod(name = "communities.campaigns.batches.vouchers.getAll",
            path = "communities/{communityId}/campaigns/{campaignId}/batches/{batchId}/vouchers",
            httpMethod = GET)
    public List<Voucher> getAll(@Named("communityId") long communityId,
                                     @Named("campaignId") long campaignId,
                                     @Named("batchId") long batchId,
                                     @Nullable @DefaultValue("0") @Named("page") int page,
                                     @Nullable @DefaultValue("1000000") @Named("size") int size,
                                     User user) throws EntityNotFoundException, OAuthRequestException {
        logger.info("getAll communityId:" + communityId + ", campaignId:" + campaignId + ", batchId:" + batchId + ", page:" + page + ", size:" + size);
        authorizationService.authorize(user);
        return voucherService.findAll(communityId, campaignId, batchId, page, size);
    }

}
