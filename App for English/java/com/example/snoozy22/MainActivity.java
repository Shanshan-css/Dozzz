package com.example.snoozy22;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoozy22.ui.Database;
import com.example.snoozy22.ui.Message;
import com.example.snoozy22.utils.RegexSearch;
import com.example.snoozy22.utils.StringUtils;
import com.example.snoozy22.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected static final int RESULT_SPEECH = 1;
    int count = 0;
    static int k = 0;
    static int j = 0;
    static int l = 0;
    int i = 0;
    //views from activity
    TextView mTextTv;
    ImageButton mVoiceBtn;
    private SQLiteDatabase database;
    TextToSpeech my_tts_object;
    RegexSearch rs;
    List<Message> messageList;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageList = new ArrayList<>();

        mTextTv = findViewById(R.id.textTv);
        Database mDataBase = new Database(this);
        database = mDataBase.getWritableDatabase();//获得数据库写入权限
        mVoiceBtn = findViewById(R.id.voiceBtn);

        //button click onto show speech to text dialog
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakToText();
            }
        });

        //voice initialize
        my_tts_object = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //Check the Initialization of TTS Engine
                if(status == TextToSpeech.SUCCESS){
                    //set the language
                    Locale dutchLocal = new Locale("nl", "NL");
                    int result = my_tts_object.setLanguage(dutchLocal);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("MESSAGE", "Language not supported");
                        Intent intent = new Intent();
                        intent.setAction(
                                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(intent);
                        my_tts_object.setLanguage(dutchLocal);
                    }
                }
            }
        });
    }
    /**
     * Speech to text
     */
    private void speakToText() {
        //intent to show speech to text dialogue
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"nl-NL");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hallo, zeg iets.");
        //start intent
        try {
            //in there was no error
            //show dialog
            startActivityForResult(intent,RESULT_SPEECH);
            mTextTv.setText("");
        }catch (ActivityNotFoundException e){
            //if there was some error
            //get message of error and show
            Toast.makeText(getApplicationContext(),"Your device doesn't support to speak",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Text to speech
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_SPEECH:{
                if (resultCode == RESULT_OK && null != data){
                    //get text array from voice intent
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String resultString = "";
                    String received = "";
                    resultString += result.get(0);
                    /*ContentValues values = new ContentValues();
                    values.put("Answer", resultString);
                    long newRowId = database.insert("answers", null, values);*/

                    StringUtils su = new StringUtils();
                    received = su.question(0);
                    if (su.searchMatchStart(resultString)){
                        my_tts_object.speak(su.question(0), TextToSpeech.QUEUE_FLUSH,null,null);
                    }else {
                        if (su.searchMatchSorry(resultString)){
                            my_tts_object.speak(su.lastQuestion(i), TextToSpeech.QUEUE_FLUSH,null,null);
                        }else if(su.searchMatchNo(resultString)){
                            my_tts_object.speak(su.questionNo(i), TextToSpeech.QUEUE_FLUSH,null,null);
                            i++;
                        }else if(su.searchMatchYes(resultString)) {
                            my_tts_object.speak(su.questionYes(i), TextToSpeech.QUEUE_FLUSH,null,null);
                            i++;
                        } else if (su.questionHasTime(resultString)) {
                            my_tts_object.speak(su.questionYes(i-1), TextToSpeech.QUEUE_FLUSH,null,null);
                            i++;
                        }else if (su.searchMatchPeo(resultString)) {
                            my_tts_object.speak(su.question(1), TextToSpeech.QUEUE_FLUSH,null,null);
                            i++;
                        }else if (su.searchMatchNum(resultString)) {
                            my_tts_object.speak(su.questionYes(5), TextToSpeech.QUEUE_FLUSH,null,null);
                            i = 5;
                            i++;
                        } else if (!su.searchMatchSorry(resultString)&& !su.searchMatchNo(resultString) && !su.searchMatchYes(resultString)&&!su.questionHasTime(resultString)&&!su.searchMatchPeo(resultString)&&!su.searchMatchNum(resultString)&&!resultString.equals("bye")&&!resultString.equals("hello")) {
                            my_tts_object.speak(su.questionYes(i), TextToSpeech.QUEUE_FLUSH,null,null);
                            i++;

                        }
                    }
                    mTextTv.setText(resultString);
                }
                break;
            }
        }
    }
}