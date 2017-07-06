package com.example.q.tempproject;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.q.tempproject.util.JsonUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  private EditText etName;
  private Button btnSend;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etName = (EditText) findViewById(R.id.etName);
    btnSend = (Button)findViewById(R.id.btnSend);

    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        requestMessage(etName.getText().toString());
      }
    });
  }

  private void requestMessage(String name){
    OkHttpClient client = new OkHttpClient();
    HttpUrl httpUrl = new HttpUrl.Builder()
        .scheme("http")
        .host("13.124.41.33")
        .port(8081)
//        .addQueryParameter("name", name)
        .build();

    Request request = new Request.Builder()
        .url(httpUrl)
        .build();
    client.newCall(request).enqueue(callbackAfterGettingMessage);
  }

  private Callback callbackAfterGettingMessage = new Callback() {
    @Override
    public void onFailure(Request request, IOException e) {
      final AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
      adb.setTitle("Error!")
          .setMessage("Cannot connect to the Server")
          .setPositiveButton("OK", onOKClickListener);
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          adb.show();
        }
      });
    }

    public void onResponse(Response response) throws IOException {
      final String strJsonOutput = response.body().string();
      final JSONObject jsonOutput = JsonUtil.getJSONObjectFrom(strJsonOutput);
      final AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);

      adb.setTitle("Result")
          .setMessage(JsonUtil.getStringFrom(jsonOutput, "message"))
          .setPositiveButton("OK", onOKClickListener);

      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          adb.show();
        }
      });
    }
  };

  private DialogInterface.OnClickListener onOKClickListener = new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which){
      dialog.dismiss();
    }
  };
}
