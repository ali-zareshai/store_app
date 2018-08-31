package Interface;

import java.util.List;

import model.ChatGroupModel;
import model.ChatMsgModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
}
