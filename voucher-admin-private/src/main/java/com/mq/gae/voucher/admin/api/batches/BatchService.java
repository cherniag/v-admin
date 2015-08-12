package com.mq.gae.voucher.admin.api.batches;

import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import com.mq.gae.voucher.admin.api.campaigns.Campaign;
import com.mq.gae.voucher.admin.api.communities.Community;
import com.mq.gae.voucher.admin.api.vouchers.VoucherService;

import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Author: Gennadii Cherniaiev
 * Date: 7/23/2015
 */
public class BatchService {
    private static final Logger logger = Logger.getLogger(BatchesEndpoint.class.getName());
    private static final BatchService BATCH_SERVICE = new BatchService();
    private VoucherService voucherService = VoucherService.getInstance();

    public static BatchService getInstance() {
         return BATCH_SERVICE;
    }

    private BatchService() {

    }

    public void createBatch(Batch batch, long communityId, long campaignId) {
        // make transactional
        Key<Community> communityKey = Key.create(Community.class, communityId);
        Key<Campaign> campaignKey = Key.create(communityKey, Campaign.class, campaignId);
        batch.campaignRef = Ref.create(campaignKey);
        ofy().save().entity(batch).now();
        voucherService.generate(batch);
    }


    public Batch findOne(long communityId, long campaignId, Long batchId) {
        Key<Community> communityKey = Key.create(Community.class, communityId);
        Key<Campaign> campaignKey = Key.create(communityKey, Campaign.class, campaignId);
        Key<Batch> batchKey = Key.create(campaignKey, Batch.class, batchId);
        return ofy().load().key(batchKey).now();
    }

    public List<Batch> findAll(long communityId, long campaignId, int page, int size, String sorting) throws BadRequestException {
        Key<Community> communityKey = Key.create(Community.class, communityId);
        Key<Campaign> campaignKey = Key.create(communityKey, Campaign.class, campaignId);
        logger.info("campaignKey: " + campaignKey.getString());

        if(!Batch.sortFields.contains(sorting)) {
            throw new BadRequestException("Sort column " + sorting + " not valid");
        }

        Query<Batch> batchQuery = ofy().load().type(Batch.class).ancestor(campaignKey);
        if (sorting != null) {
            batchQuery = batchQuery.order(sorting);
        }
        return batchQuery.offset(page * size).limit(size).list();
    }

    public Batch changeStatus(long batchId, long communityId, long campaignId, boolean isActive) {
        Key<Community> communityKey = Key.create(Community.class, communityId);
        Key<Campaign> campaignKey = Key.create(communityKey, Campaign.class, campaignId);
        Key<Batch> batchKey = Key.create(campaignKey, Batch.class, batchId);
        Batch batch = ofy().load().key(batchKey).now();
        batch.isActive = isActive;
        ofy().save().entity(batch).now();
        return batch;
    }
}
