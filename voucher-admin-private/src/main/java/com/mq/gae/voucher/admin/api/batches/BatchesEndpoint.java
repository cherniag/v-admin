package com.mq.gae.voucher.admin.api.batches;

import com.google.api.server.spi.config.*;
import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.mq.gae.voucher.admin.api.AbstractEndpoint;
import com.mq.gae.voucher.admin.api.AuthorizationService;
import com.mq.gae.voucher.admin.api.Constants;

import javax.inject.Named;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.*;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;
import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;

/**
 * Author: Gennadii Cherniaiev
 * Date: 7/24/2015
 * <p/>
 * Check oauth2 working: https://developers.google.com/oauthplayground
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
public class BatchesEndpoint {
    static final Logger logger = Logger.getLogger(BatchesEndpoint.class.getName());
    BatchService batchService = BatchService.getInstance();
    AuthorizationService authorizationService = AuthorizationService.getInstance();


    @ApiMethod(name = "communities.campaigns.batches.getOne",
            path = "communities/{communityId}/campaigns/{campaignId}/batches/{batchId}",
            httpMethod = GET)
    public Batch getOne(@Named("communityId") long communityId,
                        @Named("campaignId") long campaignId,
                        @Named("batchId") long batchId,
                        User user) throws OAuthRequestException {
        logger.info("getOne communityId:" + communityId + ", campaignId:" + campaignId + ", batchId:" + batchId);
        authorizationService.authorize(user);
        return batchService.findOne(communityId, campaignId, batchId);
    }

    @ApiMethod(name = "communities.campaigns.batches.getAll",
            path = "communities/{communityId}/campaigns/{campaignId}/batches",
            httpMethod = GET)
    public List<Batch> getAll(@Named("communityId") long communityId,
                              @Named("campaignId") long campaignId,
                              @Named("page") int page,
                              @Named("size") int size,
                              @Nullable @Named("sorting") String sorting,
                              User user) throws OAuthRequestException, BadRequestException {
        logger.info("getAll communityId:" + communityId + ", campaignId:" + campaignId + ", page:" + page + ", size:" + size + ", sorting:" + sorting);
        authorizationService.authorize(user);
        return batchService.findAll(communityId, campaignId, page, size, sorting);
    }

    @ApiMethod(name = "communities.campaigns.batches.create",
            path = "communities/{communityId}/campaigns/{campaignId}/batches",
            httpMethod = POST)
    public void create(@Named("communityId") long communityId,
                       @Named("campaignId") long campaignId,
                       Batch batch,
                       User user) throws OAuthRequestException {
        logger.info("create communityId:" + communityId + ", campaignId:" + campaignId + ", batch:" + batch);
        authorizationService.authorize(user);
        batchService.createBatch(batch, communityId, campaignId);
    }

    @ApiMethod(name = "communities.campaigns.batches.activate",
            path = "communities/{communityId}/campaigns/{campaignId}/batches/{batchId}/activate",
            httpMethod = PUT)
    public Batch activate(@Named("communityId") long communityId,
                          @Named("campaignId") long campaignId,
                          @Named("batchId") long batchId,
                          User user) throws OAuthRequestException {
        logger.info("activate communityId:" + communityId + ", campaignId:" + campaignId + ", batchId:" + batchId);
        authorizationService.authorize(user);
        return batchService.changeStatus(batchId, communityId, campaignId, true);
    }

    @ApiMethod(name = "communities.campaigns.batches.deactivate",
            path = "communities/{communityId}/campaigns/{campaignId}/batches/{batchId}/deactivate",
            httpMethod = PUT)
    public Batch deactivate(@Named("communityId") long communityId,
                            @Named("campaignId") long campaignId,
                            @Named("batchId") long batchId,
                            User user) throws OAuthRequestException {
        logger.info("deactivate communityId:" + communityId + ", campaignId:" + campaignId + ", batchId:" + batchId);
        authorizationService.authorize(user);
        return batchService.changeStatus(batchId, communityId, campaignId, false);
    }

}
