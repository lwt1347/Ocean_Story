package com.example.user.ocean_story;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Menu_Sliding_Panel extends Activity {


    //일시정지 눌렀을때 뜨는 화면
    // android:theme="@android:style/Theme.Dialog" - > 다이얼로그 형식으로 띄우게된다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.menuslidingpanel);      //일시정지 눌렀을때 뜨는 xml 화면

    }
}
