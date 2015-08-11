package com.mq.gae.voucher.admin.api.communities;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.mq.gae.voucher.admin.api.AbstractEndPoint;
import com.mq.gae.voucher.admin.api.AuthorizationService;
import com.mq.gae.voucher.admin.api.Constants;

import javax.inject.Named;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.*;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/4/2015
 */
public class CommunityEndpoint extends AbstractEndPoint{
        static final Logger logger = Logger.getLogger(CommunityEndpoint.class.getName());
        CommunityService communityService = CommunityService.getInstance();
        AuthorizationService authorizationService = AuthorizationService.getInstance();


        @ApiMethod(name = "communities.getOne",
                path = "communities/{communityId}",
                httpMethod = GET)
        public Community getCommunity(@Named("communityId") long communityId,
                              User user) throws EntityNotFoundException, OAuthRequestException {
                authorizationService.authorize(user);
                logger.info("getOne with id " + communityId);
                return communityService.findOne(communityId);
        }

        @ApiMethod(name = "communities.getAll",
                path = "communities",
                httpMethod = GET)
        public List<Community> getCommunities(User user) throws EntityNotFoundException, OAuthRequestException {
                authorizationService.authorize(user);
                logger.info("getAll");
                return communityService.findAll();
        }

        @ApiMethod(name = "communities.create",
                path = "communities",
                httpMethod = POST)
        public void create(Community Community,
                           User user) throws ParseException, OAuthRequestException {
                authorizationService.authorize(user);
                logger.info("create: Community=" + Community);
                communityService.createCommunity(Community);
        }
}
