package com.mq.gae.voucher.admin.api.vouchers;

import com.googlecode.objectify.Key;
import com.mq.gae.voucher.admin.api.batches.Batch;
import com.mq.gae.voucher.admin.api.campaigns.Campaign;
import com.mq.gae.voucher.admin.api.communities.Community;

import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/4/2015
 */
public class VoucherRedemptionService {
    private static final Logger logger = Logger.getLogger(VoucherRedemptionService.class.getName());
    private static final VoucherRedemptionService VOUCHER_SERVICE = new VoucherRedemptionService();

    public static VoucherRedemptionService getInstance() {
        return VOUCHER_SERVICE;
    }

    private VoucherRedemptionService() {

    }

    public long redeem(long communityId, String voucherCode, long id, String email) {
        Key<Community> communityKey = Key.create(Community.class, communityId);
        List<Voucher> vouchers = ofy().load().type(Voucher.class).ancestor(communityKey).filter("code", voucherCode).list();
        if (vouchers.size() != 1) {
            raise("redeem voucher by code " + voucherCode + " returns list:" + vouchers);
        }

        Voucher voucher = vouchers.get(0);
        validate(voucher);
        voucher.redeem(id, email);
        ofy().save().entity(voucher).now();

        Campaign campaign = voucher.batchRef.get().getCampaignRef().get();
        return campaign.productId;
    }

    private void validate(Voucher voucher) {
        if (voucher.redeemedDate != null) {
            raise("voucher " + voucher + " has been already redeemed");
        }

        Batch batch = voucher.batchRef.get();
        if (!batch.getIsActive()) {
            raise("voucher " + voucher + " refers to inactive batch " + batch);
        }

        Campaign campaign = batch.getCampaignRef().get();
        if (!campaign.isActive) {
            raise("voucher " + voucher + " refers to inactive campaign " + campaign);
        }
    }

    private void raise(String msg) {
        logger.info(msg);
        throw new RuntimeException(msg);
    }
}
