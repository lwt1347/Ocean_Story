package com.example.user.ocean_stroy;

/**
 * Created by USER on 2017-01-21.
 * 달팽이
 */

public class Ground_Touch_Snail extends Ground_Default_Body {

    /**
     * Ground_Touch_Snail 변수
     */
    private int snail_DrawStatus = 0;   //이미지 변화

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     */
    Ground_Touch_Snail(float window_Width, int width, int height, int hp) {
        super(window_Width, width, height, hp);
    }

    //********************************************************************************************//


    /**
     * 반환
     */
    public int get_Draw_Snail_Status()
    {
        return snail_DrawStatus/2; //물고기 헤엄 이미지 2번씩 송출
    }

    //********************************************************************************************//

    /**
     * 달팽이 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {

        ground_Point_Y += speed;
        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = 2 + (float)Math.random() * 3;
        }

        snail_DrawStatus++;
        if(snail_DrawStatus > 7){
            snail_DrawStatus = 0;
        }
    }

    //********************************************************************************************//
}
