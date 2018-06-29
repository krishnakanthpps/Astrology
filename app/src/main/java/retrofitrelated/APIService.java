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
    @FormUrlEncoded
    @POST("index.php?r=site/login")
    Call<LoginResult> loginUser(
            @Field("username") String username,
            @Field("password") String password);

    //The register call
    @FormUrlEncoded
    @POST("index.php?r=users/create")
    Call<RegisterResponse> createUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("dateofbirth") String dateofbirth,
            @Field("timeofbirth") String timeofbirth,
            @Field("placeofbirth") String placeofbirth);



    //The forgotpassword call
    @FormUrlEncoded
    @POST("index.php?r=site/requestpasswordreset")
    Call<RegisterResponse> forgotPassword(
            @Field("email") String email);


    //The changepassword call
    @FormUrlEncoded
    @POST("index.php?r=users/changepassword")
    Call<RegisterResponse> changePassword(
            @Field("userid") String userid,
            @Field("oldpassword") String password,
            @Field("password") String newpassword
           );

    //profileview
    @GET("index.php?r=users/view")
    Call<ProfileViewResultResponse> loadProfileView(@Query("id") String id);

    //sidemenu_options
    @GET("index.php?r=horoscopeplanets/horoscopebhavas")
    //&id=145&bhavam=self
    Call<Bhavaasmodel> loadBhaavas(@Query("id") String id,@Query("bhavam") String bhavam);

    /*main dashboard chakras*/
    @GET("index.php?r=horoscopeplanets/viewnew")
        //&id=145&bhavam=self
    Call<ChakrasResult> loadChakras(@Query("id") String id);


}
