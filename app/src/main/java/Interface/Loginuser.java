package Interface;

import model.LoginModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Loginuser {
    @POST("login")
    @FormUrlEncoded
    Call<LoginModel> checkuser(@Field("username") String user,@Field("pass") String pass);
}
