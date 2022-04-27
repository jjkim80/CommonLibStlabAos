package com.dkitec.commonlib_stalb_aos.retrofit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dkitec.commonlib_stalb_aos.R;

import com.dkitec.commonlib_stalb_aos.retrofitthumbnailmdoel.CustomList;
import com.dkitec.commonlib_stalb_aos.retrofitthumbnailmdoel.Data;
import com.dkitec.commonlib_stalb_aos.retrofitthumbnailmdoel.RetrofitModel;
import com.dkitec.commonlib_stalb_aos.utils.Logger;

import java.util.List;

import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    private TextView textViewResult;
    private ImageView imageViewResult;
    private Context context;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        context = this;

        textViewResult = findViewById(R.id.text_view_result);
        imageViewResult = findViewById(R.id.image_view_result);
        progressBar = findViewById(R.id.progress_bar);


        RetrofitApiManager.getInstance().requestRetrofitThumbnail(new RetrofitInterface() {
            @Override
            public void onResponse(Response response) {
                try {
                    if (response.isSuccessful()) {
                        RetrofitModel retrofitModel = (RetrofitModel) response.body();
                        if (retrofitModel != null) {
                            Data data = retrofitModel.getData();
                            if (data != null) {
                                List<CustomList> customLists = data.getCustomList();
                                if (customLists != null && !customLists.isEmpty()) {
                                    String url = customLists.get(0).getImageUrl();
                                    Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    }).into(imageViewResult);
                                    textViewResult.setText("성공");
                                    Logger.d("결과 : 성공!");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Logger.e("결과 : null");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.d("t.getMessage() : " + t.getMessage());
                Logger.d("결과 : 실패!");
            }
        });
    }
}