package com.xs.joel.geofen.Model;

import java.util.List;

/**
 * Created by joel_ on 15/2/2018.
 */

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;
    public List<Result> results;

    public MyResponse() {

    }

    public MyResponse(long multicast_id, int success, int failure, int canonical_ids, List<Result> results) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
        this.results = results;
    }

    public long getMulticast_id() {
        return multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public List<Result> getResults() {
        return results;
    }
}
