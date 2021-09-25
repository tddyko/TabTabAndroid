package com.yjrlab.tabdoctor.network.service;

import com.yjrlab.tabdoctor.model.BaseModel;
import com.yjrlab.tabdoctor.model.DoubtModel;
import com.yjrlab.tabdoctor.model.PopupModel;
import com.yjrlab.tabdoctor.model.PushQuestionModel;
import com.yjrlab.tabdoctor.model.ResponseModel;
import com.yjrlab.tabdoctor.model.UserDoubtModel;
import com.yjrlab.tabdoctor.network.enums.QuestionSearchField;
import com.yjrlab.tabdoctor.network.enums.QuestionSortField;
import com.yjrlab.tabdoctor.network.enums.SortMethod;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jongrakmoon on 2017. 6. 6..
 */

public interface PushService {
    //푸쉬 질의 결과 업로드 하기
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("push/reply")
    Call<ResponseModel<PushQuestionModel>> postQuestion(@Field("mem_id") long memId, @Field("data") String data);

    //푸쉬 질문목록 가져오기
    //Date 형식 yyyyMMdd
    @Headers({"Accept: application/json"})
    @GET("push/answer_list")
    Call<ResponseModel<List<PushQuestionModel>>> getQuestions(@Query("mem_id") long memId, @Query("sdate") String startDate, @Query("edate") String endDate);


}
