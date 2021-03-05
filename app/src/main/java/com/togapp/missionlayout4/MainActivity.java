package com.togapp.missionlayout4;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {

    EditText inputMessage;
    TextView inputCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_main.xml 에서 찾아오기
        inputMessage = findViewById(R.id.inputMessage);
        inputCount = findViewById(R.id.inputCount);

        // activity_main.xml에서 전송 버튼 찾아오기
        Button sendButton = findViewById(R.id.sendButton);
        // sendButton에 클릭 이벤트 설정하기
        // activity_main.xml의 sendButton에 클릭이벤트 setOnClickListener(new View.OnClickListener(){})
        // inputMessage 안에 있는 텍스트 가져오기
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputMessage.getText().toString();
                // sendButton을 누르면 전송할 메시지가 뜨도록 설정
                // - getApplicationContext() : 액티비티를 참조할 수 없는 경우 getApplicationContext() 메서드를 호출하면 Context 객체가 반환됩니다.
                Toast.makeText(getApplicationContext(), "전송할 메시지\n\n" + message, Toast.LENGTH_LONG).show();
            }
        });

        // activity_main.xml에서 닫기 버튼 찾아오기
        Button closeButton = findViewById(R.id.closeButton);
        // closeButton 에도 이벤트 설정하기
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 액티비티 종료
                finish();
            }
        });

        // - TextWatcher 객체 텍스트가 변경될 때마다 발생하는 이벤트 처리
        // 텍스트 변경 전/시/후
        TextWatcher textWatcher = new TextWatcher() {

            //텍스트가 입력될때마다 텍스트 숫자를 세어준다.
            @Override
            public void beforeTextChanged(CharSequence str, int start, int before, int count) {
            //public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // String[] 문자 배열 byte 기본값 0
                // String 문자열 원하는 인코딩으로 변환 검색키워드 String 인코딩, 자바 파라미터 인코딩
                //
                byte[] bytes = null;
                try {
                    // 바이트로 리턴받기
                    // KSC5601은 안드로이드에서 한글을 byte로 변환하는 방식이라고 한다.
                    bytes = str.toString().getBytes("KSC5601");
                    // 배열에 저장된 길이값 가져오기
                    int strCount = bytes.length;
                    // 가져온 길이값은 inputCount라는 TextView에 나타내기
                    inputCount.setText(strCount + " / 80바이트");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //텍스트가 입력된후에 정해진 글자수보다 초과했을때 잘라서 넣어준다.
            @Override
            public void afterTextChanged(Editable strEditable) {
                // 입력된 모든 문자
                String str = strEditable.toString();
                try {
                    // 바이트로 리턴아서 배열에저장
                    byte[] strBytes = str.getBytes("KSC5601");
                    if(strBytes.length > 80){
                        strEditable.delete(strEditable.length()-2, strEditable.length()-1);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };

        inputMessage.addTextChangedListener(textWatcher);


    }

}