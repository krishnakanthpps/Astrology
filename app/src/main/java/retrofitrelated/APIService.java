package retrofitrelated;

import models.Bhavaasmodel;
import models.ChakrasResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by harsha on 5/30/2018.
 */

public interface APIService {

    //The login call
    /*Login with useremail and password,firebase notification token and device type-android/ios*/
    @FormUrlEncoded
    @POST("index.php?r=site/login")
    Call<LoginResult> loginUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("deviceToken") String deviceToken,
            @Field("deviceType") String deviceType);

    //The register call
    /*signup with all details manditory*/
    @FormUrlEncoded
    @POST("index.php?r=users/create")
    Call<RegisterResponse> createUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("dateofbirth") String dateofbirth,
            @Field("timeofbirth") String timeofbirth,
            @Field("placeofbirth") String placeofbirth,
            @Field("deviceToken") String deviceToken,
            @Field("deviceType") String deviceType);


    //The forgotpassword call
    /*forgotpassword with email*/
    @FormUrlEncoded
    @POST("index.php?r=site/requestpasswordreset")
    Call<RegisterResponse> forgotPassword(
            @Field("email") String email);


    //The changepassword call
    /*changepassword with userid,oldpassword,newpassword*/
    @FormUrlEncoded
    @POST("index.php?r=users/changepassword")
    Call<RegisterResponse> changePassword(
            @Field("userid") String userid,
            @Field("oldpassword") String password,
            @Field("password") String newpassword
    );

    //profileview
    /*profileview with userid*/
    @GET("index.php?r=users/view")
    Call<ProfileViewResultResponse> loadProfileView(@Query("id") String id);

    //sidemenu_options
    /*for all bhavaas common api just pass userid and bhavam id*/
    @GET("index.php?r=horoscopeplanets/horoscopebhavas")
    //&id=145&bhavam=1
    /*self=1,marriage=4*/
    Call<Bhavaasmodel> loadBhaavas(@Query("id") String id, @Query("bhavam") String bhavam);

    /*main dashboard chakras*/
    /*main dashboard with chakras and expand and collapse webcall*/
    @GET("index.php?r=horoscopeplanets/viewnew")
    Call<ChakrasResult> loadChakras(@Query("id") String id);

    /*option view mychart*/
    /*sidemenu option with mychart details userid as input*/
    @GET("index.php?r=horoscopeplanets/viewnew2")
    /*output along with chakras and all bhavas strengths*/
    Call<ChakrasResult> loadMyChartChakras(@Query("id") String id);


    //The update user profile
    /*profile update with userid and firstname,lastname and phone,address details only editable.*/
    @FormUrlEncoded
    @POST("index.php?r=users/update")
    /*username,email and dateofbirth and timeofbirth non editable*/
    Call<RegisterResponse> updateUserProfile(
            @Field("userid") String userid,
            @Field("first_name") String firstname,
            @Field("last_name") String lastname,
            @Field("dateofbirth") String dateofbirth,
            @Field("timeofbirth") String timeofbirth,
            @Field("mobile") String mobile,
            @Field("placeofbirth") String placeofbirth,
            @Field("current_city") String city,
            @Field("current_state") String state,
            @Field("current_country") String country,
            @Field("address") String address);
}
