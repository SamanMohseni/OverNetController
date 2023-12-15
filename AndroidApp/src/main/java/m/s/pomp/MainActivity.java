package m.s.pomp;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import m.s.pomp.httpRequest.API;
import m.s.pomp.httpRequest.SendDataController;

public class MainActivity extends AppCompatActivity {

    private Button btnOn;
    private Button btnOff;
    private ConstraintLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        run("what");

        handleBtns();
    }

    private void findViews(){
        btnOff = findViewById(R.id.off);
        btnOn = findViewById(R.id.on);
        back = findViewById(R.id.back);
    }

    private void run(String msg){
        SendDataController sendDataController = new SendDataController(new API.SendDataCallback() {
            @Override
            public void onResponse(boolean successful, int stateCode, String body) {
                Log.d("TAG", "successful: " + successful);
                Log.d("TAG", "stateCode: " + stateCode);
                Log.d("TAG", "body: " + body);
                if(body.equals("on")){
                    back.setBackgroundColor(getResources().getColor( R.color.colorAmber_A400));
                }
                else {
                    back.setBackgroundColor(getResources().getColor( R.color.gray));
                }
            }

            @Override
            public void onFailure(String cause) {
                Log.d("TAG", "cause: " + cause);
            }
        });

        sendDataController.start(msg, this);
    }

    private void handleBtns(){
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run("on");
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run("off");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run("what");
            }
        });
    }
}
