package m.s.pomp.httpRequest;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendDataController {
    private API.SendDataCallback sendDataCallback;

    private Response<String> responseGlobal;

    public SendDataController(API.SendDataCallback sendDataCallback){
        this.sendDataCallback = sendDataCallback;
    }

    public void start(String text, final Context context){
                Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<String> call = api.sendData(text);

        Log.d("TAG", "Sending data...");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                responseGlobal = response;

                if(!response.isSuccessful()) {
                    Log.d("TAG", "Sending data, response: fail");
                    Log.d("TAG", "Sending data, response code: " + responseGlobal.code());

                    sendDataCallback.onResponse(responseGlobal.isSuccessful(), responseGlobal.code(), null);
                }
                else {
                    Log.d("TAG", "Sending data, response: successful");
                    //successful:
                    sendDataCallback.onResponse(responseGlobal.isSuccessful(), responseGlobal.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendDataCallback.onFailure(t.getMessage());
            }
        });
    }
}
