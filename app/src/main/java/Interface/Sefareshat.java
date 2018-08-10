package Interface;

import java.util.List;

import model.SefareshatModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Sefareshat {
    @GET("getlistsefareshat")
    Call<List<SefareshatModel>> getAllSefareshat();
}
