package com.inydus.inydus.network;

import com.inydus.inydus.findSitter.findsitter.model.Sitter;
import com.inydus.inydus.findSitter.request.model.Proposal;
import com.inydus.inydus.login.model.Authentication;
import com.inydus.inydus.login.model.LoginInfo;
import com.inydus.inydus.login.model.User;
import com.inydus.inydus.main.mypage.model.Playdiary;
import com.inydus.inydus.profile.parent.child_profile.model.ChildProfile;
import com.inydus.inydus.profile.sitter.profile.model.SitterProfile;
import com.inydus.inydus.register.postoffice_api.model.AddressResponse;
import com.inydus.inydus.register.register.model.RegisterUser;
import com.inydus.inydus.request_box.request_box.model.MyRequest;
import com.inydus.inydus.request_box.request_box.model.State;
import com.inydus.inydus.request_box.request_detail.model.MyRequestDetail;
import com.squareup.okhttp.RequestBody;

import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface NetworkService {

    @GET("/session")
    Call<User> getSession();

    //로그인
    @POST("/session/sign/in")
    Call<LoginInfo> login(@Body Authentication authentication);

    //로그아웃
    @GET("/session/sign/out")
    Call<Object> logout();

    //이메일 중복 확인
    @GET("/membership/{user_id}")
    Call<String> duplicationTest(@Path("user_id") String email);

    //우편번호 검색
    @GET("/postal/retrieveNewAdressAreaCdSearchAllService/retrieveNewAdressAreaCdSearchAllService/getNewAddressListAreaCdSearchAll")
    Call<AddressResponse> getAddress(@QueryMap HashMap<String, String> parameters);

    //회원가입
    @POST("/membership")
    Call<User> registerUser(@Body RegisterUser user);


    //비밀번호 확인
    @POST("/membership/pwconfirm")
    Call<Object> confirm(@Body Authentication authentication);

    //비밀번호 변경
    @POST("/membership/updatepasswd")
    Call<Object> changepw(@Body Authentication authentication);

    //선생님 목록 가져오기
    @GET("/search")
    Call<List<Sitter>> getSitterList(@QueryMap HashMap<String, String> conditions);

    //선생님 상세 정보
    @GET("/sit_profile/{id}")
    Call<SitterProfile> getSitterProfile(@Path("id") String id);

    //신청하기(엄마 -> 선생님)
    @POST("/proposal")
    Call<String> sendProposal(@Body Proposal proposal);

    //신청함
    @GET("/proposal/thumbnail/{sitter_id}")
    Call<List<MyRequest>> getRequests(@Path("sitter_id") String sitter_id);

    //신청함 상세보기
    @GET("/proposal/detail/{parent_id}/{sitter_id}")
    Call<List<MyRequestDetail>> getDetailRequest(@Path("parent_id") String parent_id, @Path("sitter_id") String sitter_id);

    //신청 수락 or 거절
    @POST("/proposal/state")
    Call<String> sendAcceptOrDeny(@Body State state);

    //선생님 프로필 보내기
    @Multipart
    @POST("/sit_profile")
    Call<SitterProfile> sendSitterProfile(@Part("profile\"; filename=\"profile.jpg\" ") RequestBody image_profile,
                                          @Part("certificate\"; filename=\"doc.jpg\" ") RequestBody document,
                                          @Part("cover\"; filename=\"cover.jpg\" ") RequestBody image_cover,
                                          @Part("contents") SitterProfile sitterProfile);

    //아이 프로필 받기
    @GET("/child/{parent_id}")
    Call<List<ChildProfile>> getChildProfile(@Path("parent_id") String parent_id);

    //아이 프로필 보내기
    @Multipart
    @POST("/child")
    Call<String> sendChildProfile(@Part("child_profile\"; filename=\"image.jpg\" ") RequestBody image,
                                  @Part("contents") ChildProfile childProfile);

    //놀이 일기 리스트 받기
    @GET("/play/thumbnail/{user_id}")
    Call<List<Playdiary>> getPlayDiaryList(@Path("user_id") String user_id);

    //놀이 일기 디테일 받기
    @GET("/play/detail")
    Call<Playdiary> getPlayDiaryDetail(@QueryMap HashMap<String, String> playdetail);

    //놀이 일기 쓰기
    @Multipart
    @POST("/play")
    Call<String> sendPlayDiary(@Part("play_diary\"; filename=\"image.jpg\" ") RequestBody image1,
                               @Part("play_diary\"; filename=\"image.jpg\" ") RequestBody image2,
                               @Part("play_diary\"; filename=\"image.jpg\" ") RequestBody image3,
                               @Part("contents") Playdiary playDiary);

}
