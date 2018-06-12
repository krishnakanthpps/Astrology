package retrofitrelated;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


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
            @Field("password") String password,
            @Field("newpassword") String newpassword);
}
