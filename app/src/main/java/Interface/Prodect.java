package Interface;

import java.util.List;

import model.ProdectsModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Prodect {
    @POST("getprodect/{index}")
    @FormUrlEncoded
    Call<List<ProdectsModel>> getprodect(@Path("index")String index, @Field("type") String type,@Field("query") String query,@Field("shart") String shart);

}
