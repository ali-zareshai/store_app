package Interface;

import java.util.List;

import model.SefareshatModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Sefareshat {
    @GET("getlistsefareshat")
    Call<List<SefareshatModel>> getAllSefareshat();

    @GET("getsefaresh")
    Call<List<SefareshatModel>> getSefareshByUserId(@Query("id") String id);

    @POST("search/{id}")
    @FormUrlEncoded
    Call<List<SefareshatModel>> querySearch(@Field("type") String type,@Field("query") String query,@Path("id") String id);
}
