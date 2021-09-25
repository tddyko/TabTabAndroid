package com.yjrlab.tabdoctor.network.service;

import com.yjrlab.tabdoctor.model.BannerModel;
import com.yjrlab.tabdoctor.model.BaseModel;
import com.yjrlab.tabdoctor.model.BodyPartSymptom;
import com.yjrlab.tabdoctor.model.DoubtModel;
import com.yjrlab.tabdoctor.model.GroupedBodyPartSymptomModel;
import com.yjrlab.tabdoctor.model.GroupedHealthDicModel;
import com.yjrlab.tabdoctor.model.MainContentModel;
import com.yjrlab.tabdoctor.model.PopupModel;
import com.yjrlab.tabdoctor.model.ResponseModel;
import com.yjrlab.tabdoctor.model.UserDoubtModel;
import com.yjrlab.tabdoctor.network.enums.BodyPartGenderField;
import com.yjrlab.tabdoctor.network.enums.BodyPartSearchField;
import com.yjrlab.tabdoctor.network.enums.BodyPartSortField;
import com.yjrlab.tabdoctor.network.enums.MainSearchField;
import com.yjrlab.tabdoctor.network.enums.MainSortField;
import com.yjrlab.tabdoctor.network.enums.SortMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

/**
 * Created by jongrakmoon on 2017. 6. 5..
 */

public interface MainService {

    //팝업 목록 가져오기
    @Headers({"Accept: application/json"})
    @GET("popup/list")
    Call<ResponseModel<List<PopupModel>>> getPopups();

    //특정 팝업가져오기
    @Headers({"Accept: application/json"})
    @GET("popup/view/{popup_id}")
    Call<ResponseModel<PopupModel>> getPopup(@Path("popup_id") int popupId);

    //베너 목록 가져오기
    @Headers({"Accept: application/json"})
    @GET("banner/list")
    Call<ResponseModel<List<BannerModel>>> getBanners();

    //특정 배너 가져오기
    @Headers({"Accept: application/json"})
    @GET("banner/view/{banner_id}")
    Call<ResponseModel<BannerModel>> getBanner(@Path("banner_id") int bannerId);


    //메인 컨텐츠 목록 가져오기(불필요한값은 null)
    @Headers({"Accept: application/json"})
    @GET("maindisplay/list?forder=desc")
    Call<ResponseModel<List<MainContentModel>>> getMainContents(@Query("page") int page, @Query("per_page") int offset);


    //특정 메인 컨텐츠 가져오기(상세)
    @Headers({"Accept: application/json"})
    @GET("maindisplay/view/{main_id}")
    Call<ResponseModel<MainContentModel>> getMainContent(@Path("main_id") int mainContentsId);


    //성별 증상가져오기(그림에서 터치시)
    @Headers({"Accept: application/json"})
    @GET("bodys/part_of_body?bs_level=1")
    Call<ResponseModel<List<GroupedBodyPartSymptomModel>>> getBodyPartSymptomWithSex(@Query("pob_group") BodyPartGenderField bodyPartGenderField);


    //건강의심정보 최초 로그인 시 업로드하기
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("push/login_doubt")
    Call<ResponseModel<BaseModel>> postHealthLoginDoubt(@Field("mem_id") long memId, @Field("data") String data);

    //건강의심정보 업데이트하기
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("push/doubt_update")
    Call<ResponseModel<BaseModel>> postHealthDoubt(@Field("mem_id") long memId, @Field("data") String data);


    //진행헀던 의심정보 가져오기
    @Headers({"Accept: application/json"})
    @GET("members/doubt")
    Call<ResponseModel<UserDoubtModel>> getHealthDoubts(@Query("email") String email);

}
