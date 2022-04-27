package com.dkitec.commonlib_stalb_aos.retrofit;


import retrofit2.Response;

public interface RetrofitInterface {
    void onResponse(Response response);

    void onFailure(Throwable t);
}
