package com.yjrlab.tabdoctor.network.service;

import com.yjrlab.tabdoctor.model.BaseModel;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.HealthDicModel;
import com.yjrlab.tabdoctor.model.ResponseModel;
import com.yjrlab.tabdoctor.model.SelfDiagnosisModel;
import com.yjrlab.tabdoctor.model.UserModel;
import com.yjrlab.tabdoctor.network.enums.BodyPartGenderField;
import com.yjrlab.tabdoctor.network.enums.BodyPartSearchField;
import com.yjrlab.tabdoctor.network.enums.HealthSearchField;
import com.yjrlab.tabdoctor.network.enums.HealthSortField;
import com.yjrlab.tabdoctor.network.enums.NPorgStatus;
import com.yjrlab.tabdoctor.network.enums.SortMethod;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public interface UserService {
    //회원가입하기
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("members/user")
    Call<ResponseModel<UserModel>> signup(@FieldMap Map<String, Object> userModel);

    //이메일로 회원정보 조회하기
    @Headers({"Accept: application/json"})
    @GET("members/user")
    Call<ResponseModel<UserModel>> getInfo(@Query("email") String email
            , @Query("mem_token") String token);

    //성별 증상가져오기(그림에서 터치시)
    @Headers({"Accept: application/json"})
    @GET("bodys/part_of_body")
    Call<ResponseModel<List<GroupedBodyPartSymptomModel>>> getBodyPartSymptom(@Query("pob_group") BodyPartGenderField bodyPartGenderField
            , @Query("sfield") BodyPartSearchField bodyPartSearchField, @Query("skeyword") String keyword, @Query("bs_level") int level, @Query("pob_id") int pobId, @Query("bs_pid") int bsId);

    //성별 증상가져오기(그림에서 터치시)
    @Headers({"Accept: application/json"})
    @GET("bodys/part_of_body")
    Call<ResponseModel<List<GroupedBodyPartSymptomModel>>> getBodyPartSymptomFirst(@Query("pob_id") int pobId, @Query("bs_id") int bsId);


    //성별 증상가져오기(자가진단 히스토리)
    @Headers({"Accept: application/json"})
    @GET("bodys/part_of_body")
    Call<ResponseModel<List<GroupedBodyPartSymptomModel>>> getBodyPartSymptomBefore(@Query("pob_id") int pobId, @Query("bs_id") int bsId);

    //자가진단 증상 검색하기
    @Headers({"Accept: application/json"})
    @GET("bodys/part_of_body")
    Call<ResponseModel<List<GroupedBodyPartSymptomModel>>> getSearchSymptom(@Query("pob_group") BodyPartGenderField bodyPartGenderField
            , @Query("bs_group_code") String bsGroupCode, @Query("bs_content") String keyword);

    //자가진단 업로드하기
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("selfdiagnosis/update")
    Call<ResponseModel<UserModel>> postSelfDiagnosises(@Field("mem_id") long memberId, @Field("data") String data);

    //진행했던 자가진단 목록 가져오기(의심분포표에 사용)
    @Headers({"Accept: application/json"})
    @GET("selfdiagnosis/user")
    Call<ResponseModel<List<SelfDiagnosisModel>>> getSelfDiagnosises(@Query("mem_id") long memberId);


    //진행했던 자가진단 목록 가져오기(질문내역 조회에 사용)
    @Headers({"Accept: application/json"})
    @GET("selfdiagnosis/user?bs_group_code=A")
    Call<ResponseModel<List<SelfDiagnosisModel>>> getSelfDiagnosises(@Query("mem_id") long memberId, @Query("sdate") String startDate, @Query("edate") String endDate);


    //진행했던 자가진단 목록 가져오기(질문내역 조회에 사용)
    @Headers({"Accept: application/json"})
    @GET("selfdiagnosis/user?bs_group_code=R")
    Call<ResponseModel<List<SelfDiagnosisModel>>> getDoubtDistribute(@Query("mem_id") long memberId);


    //건강백과 목록 가져오기
    @Headers({"Accept: application/json"})
    @GET("health/list")
    Call<ResponseModel<List<HealthDicModel>>> getHealthDics(@Query("findex") HealthSortField healthSortField, @Query("forder") SortMethod sortMethod
            , @Query("sfield") HealthSearchField healthSearchField, @Query("skeyword") String keyword, @Query("page") int page, @Query("per_page") int offset);

    //특정 건강백과 가져오기
    @Headers({"Accept: application/json"})
    @GET("health/view/{health_code}")
    Call<ResponseModel<HealthDicModel>> getHealthDic(@Path("health_code") int healthCode);


    //협력기관 동의
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("members/np_organization/{mem_id}")
    Call<ResponseModel<BaseModel>> postNporgAgree(@Path("mem_id") long memId, @Field("mem_nporg_agree") NPorgStatus agree);


}
