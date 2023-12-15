package m.s.pomp.httpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    String BASE_URL = "http://REPLACE_WITH_YOUR_SERVER_ADDRESS:8080/";

    @POST("main")
    Call<String> sendData(@Body String text);

    interface SendDataCallback{

        void onResponse(boolean successful, int stateCode, String body);

        void onFailure(String cause);
    }

}
