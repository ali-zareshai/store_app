package Interface;

import java.util.List;

import model.ChatGroupModel;
import model.ChatMsgModel;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Chat {
    @GET("chat/groups")
    Call<List<ChatGroupModel>> getGroupsChat();

    @GET("chat/user/{user}/{group}")
    Call<List<ChatMsgModel>> getMsgChat(@Path("user")String user,@Path("group")String group);

    @POST("chat/user/{user}/{group}")
    @FormUrlEncoded
    Call<String> newTextMsg(@Path("user")String user,@Path("group")String group,@Field("msg_txt")String msg);

    @Multipart
    @POST("chat/user/img/{user}/{group}")
    Call<ResponseBody> uploadImg(@Path("user") String user, @Path("group")String group, @Part MultipartBody.Part file, @Part("image") RequestBody name);
}
