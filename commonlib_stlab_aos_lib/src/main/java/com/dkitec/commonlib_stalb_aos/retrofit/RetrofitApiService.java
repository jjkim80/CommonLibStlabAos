package com.dkitec.commonlib_stalb_aos.retrofit;



import com.dkitec.commonlib_stalb_aos.retrofitthumbnailmdoel.RetrofitModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiService {
    //GET 방식으로 요청하고 baseURL 뒤에 붙을 주소 지정--retrofit
    @GET("4I1S")
    Call<RetrofitModel> getRetrofitThumbnail();

}