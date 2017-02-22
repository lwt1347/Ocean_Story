package com.example.user.ocean_story;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class GameActivity extends AppCompatActivity {

    LinearLayout slidingMenuPanel;
    Animation translateDownAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //인텐트 받는 작업을 수행하고

        //게임메인 뷰를 켠다. 게임이 동작하는 구간.

        GameMain _gGameMain = new GameMain(this);
        _gGameMain.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.0f
        ));
        setContentView(_gGameMain);
        //_gGameMain.setZOrderOnTop(false);

        /*
        // 슬라이딩 됨 주석 다시 달고 정지 눌렀을때 이 화면 불러와야함
        setContentView(R.layout.menuslidingpanel);      //화면에 뷰를올린다  setContentView
        translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        slidingMenuPanel = (LinearLayout) findViewById(R.id.menuslidingpanel);
        slidingMenuPanel.setVisibility(View.VISIBLE);
        slidingMenuPanel.startAnimation(translateDownAnim);
        */
    }





}
