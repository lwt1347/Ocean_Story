package com.example.user.ocean_story;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by USER on 2017-01-21.
 * 게임의 모든 화면
 */

public class GameMain extends SurfaceView implements SurfaceHolder.Callback{

    /**
     * GameMain 변수 지정
     */

    private int drag_Action_Move = 0;           //드래그 이벤트 너무 빨리 일어나지 않도록 변수
    private int ground_Remove_Temp = -1;        //달팽이 삭제할 인덱스
    private int tempInt = 0;                    //템프 변수

    private int window_Width = 0;
    private int window_Height = 0;
    private int sound_Effect[] = new int[10];                        //효과음


    //두점 사이의 거리를 구하기위한 변수
    private int smallFishIndex      = 0;        //가장 가까운 물고기 인덱스
    private double pointXBig        = 0;
    private double pointXSmall      = 0;
    private double pointYBig        = 0;
    private double pointYSmall      = 0;
    private double smallFishTemp    = 1500;     //가장 가까운 물고기 찾기 위한 변수
    private double smallMathResult  = 0;        //가장 가까운 물고기 찾기 위한 변수
    private boolean eraser_Fish = false;        //물고기를 지우기 허가가 떨어졌을때




    private Game_Thread game_thread;                    //스레드 돌릴 클래스 선언
    private Game_Element_Thread game_element_thread;    //게임 요소 생성[물고기, 함정 등]쓰레드 생성
    private Main_Character main_Character;              //메인 캐릭터 생성
    private boolean mRun = true;                        //run 함수 제어 //퍼즈 걸도록 mRun 컨트롤
    private SurfaceHolder mSurfaceHolder;               //쓰레드 외부에서 SurfaceHolder를 얻기 위한 선언
    private BitmapDrawable image = null;                //메모리 절약 기법
    private Bitmap backGroundImg = null;                //배경이미지

    //pause 이미지
    private Bitmap pause_img[] = new Bitmap[2];

    /**
     * 기본 물고기 이미지
     */

    private Bitmap fish_Touch_Default_Hp1_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Hp2_img[] = new Bitmap[4];    //Hp2 물고기
    private Bitmap fish_Touch_Default_Hp3_img[] = new Bitmap[4];    //Hp3 물고기
    private Bitmap fish_Touch_Default_Hp4_img[] = new Bitmap[4];    //Hp4 물고기
    private Bitmap fish_Touch_Default_Hp5_img[] = new Bitmap[4];    //Hp5 물고기

    //드래그로 죽는 물고기 이미지
    private Bitmap fish_Drag_Default_img[] = new Bitmap[4];         //드래그로 죽는 물고기 이미지

    /**
     * 달팽이 이미지
     */
    private Bitmap ground_Touch_Snail_Hp1_img[] = new Bitmap[4];    //Hp1 달팽이
    private Bitmap ground_Touch_Snail_Hp2_img[] = new Bitmap[4];    //Hp2 달팽이
    private Bitmap ground_Touch_Snail_Hp3_img[] = new Bitmap[4];    //Hp3 달팽이
    private Bitmap ground_Touch_Snail_Hp4_img[] = new Bitmap[4];    //Hp4 달팽이
    private Bitmap ground_Touch_Snail_Hp5_img[] = new Bitmap[4];    //Hp5 달팽이

    /**
     * 꽃게 이미지
     */
    private Bitmap ground_Drag_Crab_img[] = new Bitmap[4];          //꽃게 이미지




    //메인 캐릭터 이미지
    private Bitmap main_Character_img[] = new Bitmap[3];        //메인 캐릭터

    //회전 물고기 비트맵 템프 변수
    private Bitmap temp_Fish = null;

    //바닥 생명체 비트맵 탬프 변수
    private Bitmap temp_Ground = null;

    /**
     * 기본 터치 물고기 이펙트
     */
    private Bitmap effect_Orange_img[] = new Bitmap[5];     //오렌지
    private Bitmap effect_Blue_img[] = new Bitmap[5];       //블루
    private Bitmap effect_Yellow_img[] = new Bitmap[5];     //옐로우
    private Bitmap effect_Green_img[] = new Bitmap[5];      //그린

    /**
     * 드래그 물고기 이펙트 팝
     *  달팽이 이펙트
     *  꽃게 이팩트
     */
    private Bitmap effect_Pop1_img[] = new Bitmap[5];
    private Bitmap effect_Pop2_img[] = new Bitmap[5];
    private Bitmap effect_Pop3_img[] = new Bitmap[5];
    private Bitmap effect_Pop4_img[] = new Bitmap[5];
    private Bitmap effect_Pop5_img[] = new Bitmap[5];
    private Bitmap effect_Pop6_img[] = new Bitmap[5];

    /**
     * 백그라운드 이펙트 동적인 화면 구성하기 위한 이미지
     */
    private Bitmap effect_Background_One_1_img[] = new Bitmap[8];
    private Bitmap effect_Background_Two_1_img[] = new Bitmap[8];

    //퍼지 이미지 변경
    boolean pause_Push = false;

    //물기고 생성
    private ArrayList<Fish_Default_Body> fish_List = new ArrayList<Fish_Default_Body>();            //물고기를 넣을 어레이 리스트
    private ArrayList<Ground_Default_Body> ground_List = new ArrayList<Ground_Default_Body>();      //물고기를 넣을 어레이 리스트

    private ArrayList<Float> effect_Fish_Draw_X = new ArrayList<Float>();                           //지워지는 물고기 위치에 이펙트 넣어야한다. //circly_X_Draw - > effect_Fish_Draw_X
    private ArrayList<Float> effect_Fish_Draw_Y = new ArrayList<Float>();

    private ArrayList<Float> effect_Ground_Draw_X = new ArrayList<Float>();                         //지워지는 달팽이 위치에 이펙트 넣어햐 한다. //effect_X_Pop2 - > effect_Ground_Draw_X
    private ArrayList<Float> effect_Ground_Draw_Y = new ArrayList<Float>();

    private ArrayList<Integer> effect_TempRandom_Temp = new ArrayList<Integer>();                   //이펙트 효과를 위한 랜덤 리스트


    private Draw_Image draw = new Draw_Image();
    private Paint paint = new Paint();
    private Canvas canvas;
    Thread background_Effect;                           //배경화면 쓰레드

    private Fish_Touch_Default fish_Touch_Default;      //기본 물고기 생성
    private Fish_Drag_Default fish_Drag_Default;        //드래그 물고기 생성
    private Ground_Touch_Snail ground_Touch_Snail;      //달팽이 생성
    private Ground_Drag_Crab ground_Drag_Crab;          //꽃게 생성

    private Background_Effect_One background_Effect_One;    //배경화면 1번 움직임
    private Background_Effect_Two background_Effect_Two;    //배경화면 1번 움직임


    private Bitmap effect_Temp;                         //어떤 이펙트를 넣을것인가 랜덤 변수
    private Random random = new Random();

    private Context _context;                           //화면 얻어오기
    private Display display;                            //디스플레이 넓이 구하기

    private String tempStr = "";

    private SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망
    //********************************************************************************************//






    /**
     * 기본 생성자
     */
    public GameMain(Context context) {
        super(context);


        _context = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        game_thread = new Game_Thread(mSurfaceHolder);      //그림이 그려지고, 게임 동작하는곳



        //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
        game_element_thread = new Game_Element_Thread();



        //메인캐릭터 생성
        main_Character = new Main_Character();

        //소리 로드
        sound_Effect[0] = soundPool.load(_context, R.raw.fish_default_sound_1, 1);      //팝1
        sound_Effect[1] = soundPool.load(_context, R.raw.fish_default_sound_2, 1);      //팝2
        sound_Effect[2] = soundPool.load(_context, R.raw.drag_sound_1, 1);      //드래그1
        sound_Effect[3] = soundPool.load(_context, R.raw.drag_sound_2, 1);      //드래그2

    }

    //********************************************************************************************//

    /**
     *  내부 클래스
     *  게임 요소 추가 할 스레드 [물고기, 바닥 생명체 등.]
     */
    class Game_Element_Thread extends Thread{


        @Override
        public synchronized void run() {
            while(mRun){

                try {
                    Thread.sleep(3500);

                        add_Fish_Touch_Default();           //기본 물고기 추가
                        add_Fish_Drag_Default();            //드래그 물고기 추가


                        add_Ground_Snail();                 //달팽이 추가
                        add_Ground_Crab();                  //꽃게 추가


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    //********************************************************************************************//

    /**
     *  이너 클래스
     *  내부 클래스 게임 스레드
     *  더블 버퍼링 및 게임 그리기
     */

    class Game_Thread extends Thread{

        /**
         *  Game_Thread 기본 생성자, 이미지 초기화
         */
        public Game_Thread(SurfaceHolder surfaceHolder) { //더블 버퍼링 같은것,

            backGroundImg = Init_Background_Image(_context, 0); //배경


            //퍼즈 이미지
            pause_img[0] = Init_Pause_Image(_context, 0);
            pause_img[1] = Init_Pause_Image(_context, 1);

            for(int i = 0; i < 4; i++) {
                fish_Touch_Default_Hp1_img[i] = Init_Fish_Touch_Default_Hp1_Image(_context, i); //캐릭터 이미지 추가 hp = 1
                fish_Touch_Default_Hp2_img[i] = Init_Fish_Touch_Default_Hp2_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp3_img[i] = Init_Fish_Touch_Default_Hp3_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp4_img[i] = Init_Fish_Touch_Default_Hp4_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp5_img[i] = Init_Fish_Touch_Default_Hp5_Image(_context, i); //캐릭터 이미지 추가 hp = 5

                ground_Touch_Snail_Hp1_img[i] = Init_Ground_Touch_Snail_Hp1_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp2_img[i] = Init_Ground_Touch_Snail_Hp2_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp3_img[i] = Init_Ground_Touch_Snail_Hp3_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp4_img[i] = Init_Ground_Touch_Snail_Hp4_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp5_img[i] = Init_Ground_Touch_Snail_Hp5_Image(_context, i);  //달팽이 이미지

                fish_Drag_Default_img[i] = Init_Fish_Drag_Default_Image(_context, i);            //드래그 물고기

                ground_Drag_Crab_img[i] = Init_Ground_Drag_Crab_Image(_context, i);              //꽃게 이미지


            }



            for(int i = 0; i < 3; i++){
                main_Character_img[i] = Init_Main_Character_Image(_context, i); //메인 캐릭터


            }
            for(int i=0; i<5; i++){

                effect_Orange_img[i] = Init_Effect_Orange_Image(_context, i); //이펙트
                effect_Blue_img[i]   = Init_Effect_Blue_Image(_context, i);
                effect_Yellow_img[i] = Init_Effect_Yellow_Image(_context, i);
                effect_Green_img[i]  = Init_Effect_Green_Image(_context, i);

                effect_Pop1_img[i] = Init_Effect_Pop1_Image(_context, i);
                effect_Pop2_img[i] = Init_Effect_Pop2_Image(_context, i);
                effect_Pop3_img[i] = Init_Effect_Pop3_Image(_context, i);
                effect_Pop4_img[i] = Init_Effect_Pop4_Image(_context, i);
                effect_Pop5_img[i] = Init_Effect_Pop5_Image(_context, i);
                effect_Pop6_img[i] = Init_Effect_Pop6_Image(_context, i);


            }
            for(int i=0; i<8; i++){
                effect_Background_One_1_img[i] = Init_Background_Effect_Background_One_1_Image(_context, i);    //배경 이펙트
                effect_Background_Two_1_img[i] = Init_Background_Effect_Background_Two_1_Image(_context, i);    //배경 이펙트
            }




        }


        /**
         *  이미지 초기화 기법 함수
         */

        //배경이미지
        public Bitmap Init_Background_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.background);
            return image.getBitmap();
        }
        //배경 이펙트
        public Bitmap Init_Background_Effect_Background_One_1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_background_one_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Two_1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_background_two_1 + num);
            return image.getBitmap();
        }


        //물고기 hp1 이미지
        public Bitmap Init_Fish_Touch_Default_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp2 이미지
        public Bitmap Init_Fish_Touch_Default_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp2_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp3 이미지
        public Bitmap Init_Fish_Touch_Default_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp3_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp4 이미지
        public Bitmap Init_Fish_Touch_Default_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp4_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp5 이미지
        public Bitmap Init_Fish_Touch_Default_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp5_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //드래그로 잡는 물고기 이미지
        public Bitmap Init_Fish_Drag_Default_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_shake1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }




        //달팽이 이미지
        public Bitmap Init_Ground_Touch_Snail_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp2_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp3_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp4_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp5_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //꽃게 이미지
        public Bitmap Init_Ground_Drag_Crab_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_crab_1 + num);     //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }





        //퍼즈 이미지
        public Bitmap Init_Pause_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.pause_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }



        //메인 캐릭터 이미지
        public Bitmap Init_Main_Character_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.main_character); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }





        //이펙트 효과
        public Bitmap Init_Effect_Orange_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_orange_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Blue_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_blue_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Yellow_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_yellow_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Green_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_green_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop1_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop2_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop3_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop4_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop5_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop6_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop6_1 + num);
            return image.getBitmap();
        }




    /**
     *  그리기 함수
     */

    public void doDraw(Canvas canvas) {

        /**
         *  배경이미지
         */
        draw.draw_Bmp(canvas, backGroundImg, 0, 0);



        /**
         * 그라운드 그리기 (달팽이) 가장 아랫부분에 깔려야 하기 때문에 가장 위쪽에서 그림
         */
        for(int i=0; i<ground_List.size(); i++) {

            //달팽이 움직임
            if (ground_List.get(i) instanceof Ground_Touch_Snail) {

                if (ground_List.get(i).get_Ground_Hp() == 5) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp5_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 4) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp4_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp3_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp2_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 1) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp1_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                }
            }
            //꽃게 움지임
            if(ground_List.get(i) instanceof Ground_Drag_Crab){
                    draw.draw_Bmp(canvas, ground_Drag_Crab_img[((Ground_Drag_Crab) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
            }
        }




        /**
         * 물고기 그리기
         */
        for(int i=0; i<fish_List.size(); i++){
            if(fish_List.get(i) instanceof Fish_Touch_Default) {
                /**
                 *  이미지 회전
                 */
                if(fish_List.get(i).get_Fish_Hp() == 1) {
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp1_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 2){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp2_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 3){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 4){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 5){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                /**
                 * 드래그로 죽이는 물고기
                 */
            }else if(fish_List.get(i) instanceof  Fish_Drag_Default){
                temp_Fish = draw.rotate_Image(fish_Drag_Default_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());
            }
        }


        /**
         * 메인 캐릭터 그리기
         */
        draw.draw_Bmp(canvas, main_Character_img[0], main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());



        /**
         * 배경 이펙트
         */
        draw.draw_Bmp(canvas,
                draw.rotate_Image(effect_Background_One_1_img[background_Effect_One.get_Draw_Background_Effect_Status()],background_Effect_One.get_Background_Angle())
                , background_Effect_One.get_Background_Point_X(), background_Effect_One.get_Background_Point_Y());

        draw.draw_Bmp(canvas, effect_Background_Two_1_img[background_Effect_Two.get_Draw_Background_Effect_Status()], main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());





        //퍼지 그리기
        if(!pause_Push) { //안눌렸을때
            draw.draw_Bmp(canvas, pause_img[0], window_Width - 100, 10);
        }else { //눌렸을때
            draw.draw_Bmp(canvas, pause_img[1], window_Width - 100, 10);
        }



    }


    /**
     * 게임이 동작하는 구간
     */


    public void run() {
        while (mRun) {
            //퍼즈 걸도록 mRun 컨트롤

                //canvas = null;
                try {
                    canvas = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {



                        /**
                         * 그림 그리기 구간
                         */
                        doDraw(canvas);
                        sleep(25);


                        //물고기 및 그라운드 생명체 체력 0 인것 삭제
                        delete_Fish();
                        delete_Ground();


                        //물고기 움직임을 하나의 쓰레드로 작동한다.
                        fish_Move();

                        //그라운드 움직임을 하나의 쓰레드로 작동합니다.
                        ground_Move();



                        //문어 공격 스피드에 따라서 터치 이벤트 제어
                        if (main_Character.get_Attack_Cool_time() != 0) {
                            main_Character.set_Attack_Cool_Time();
                        }

                    }

                } catch (Exception e) {

                } finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

        }
    }
    }

    /**
     * 게임 동작 함수
     */

    /**
     * 물고기 추가하기
     */
    public void add_Fish_Touch_Default(){

        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5);       //기본 fish_touch_default 물고기 생성
        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가


        //fish_Shake.startFishMove();
    }

    /**
     *  드래그 물고기 추가하기
     */
    public void add_Fish_Drag_Default(){
        fish_Drag_Default = new Fish_Drag_Default(window_Width, 30);       //드래그로 잡는 fish_Touch_Default 물고기생성
        fish_List.add(fish_Drag_Default);
    }


    /**
     * 그라운드 생성구간 (달팽이)
     */
    public void add_Ground_Snail(){

        ground_Touch_Snail = new Ground_Touch_Snail(window_Width,
                ground_Touch_Snail_Hp1_img[0].getWidth(),
                ground_Touch_Snail_Hp1_img[0].getHeight(), 5);

        ground_List.add(ground_Touch_Snail); //달팽이 생성

    }

    /**
     * 꽃게 추가
     */
    public void add_Ground_Crab(){
        ground_Drag_Crab = new Ground_Drag_Crab(window_Width,
                ground_Drag_Crab_img[0].getWidth(),
                ground_Drag_Crab_img[0].getHeight(), 30);

        ground_List.add(ground_Drag_Crab); //꽃게
    }





    /**
     * 물고기 움직임 -> 물고기 각 쓰레드를 주게 되면 부하가 심하대 따라서 한 함수로 모든 물고기를 제어한다.
     */

    public void fish_Move(){
        for(int i=0; i<fish_List.size(); i++){
            fish_List.get(i).fish_Object_Move();
        }
    }


    /**
     * 그라운드 움직임
     */

    public void ground_Move(){
        for(int i=0; i<ground_List.size(); i++){


            if(ground_List.get(i) instanceof Ground_Touch_Snail) {      //달팽이 무빙 함수 이용
                ((Ground_Touch_Snail) ground_List.get(i)).ground_Object_Move();
            }else if(ground_List.get(i) instanceof Ground_Drag_Crab){
                ((Ground_Drag_Crab) ground_List.get(i)).ground_Object_Move();
            }




        }
    }


    /**
     * 물고기 삭제
     */

    public void delete_Fish(){
        for(int i=0; i<fish_List.size();i++){
            //물고기 피가 0 이면 피 검사후에 피가 0 이면
            if(fish_List.get(i).get_Fish_Hp() == 0){
                fish_List.remove(i);
                break;
            }

            //물고기가 y축 으로 넘어가면 삭제
            if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30){
                fish_List.remove(i);
                break;
            }

        }
    }


    /**
     *  바닥 생명체 삭제
     */
    public void delete_Ground(){
        for(int i=0; i<ground_List.size(); i++){
            if(ground_List.get(i).get_Ground_Hp() == 0){
                ground_List.remove(i);
                break;
            }

            //물고기가 y축 으로 넘어가면 삭제
            if(ground_List.get(i).get_Ground_Point_Y() >= getHeight() - 30){
                ground_List.remove(i);
                break;
            }
        }
    }


    /**
     * 그라운드 생명체 대미지 넣기 (달팽이)
     */

    public boolean ground_Hit_Chose(float x, float y, int ground_Class){     //그라운드 객체의 종류 알아오기

        ground_Remove_Temp = -1;                    //달팽이 없을때를 기준 터치하는 곳의 달팽이 인덱스를 집어 넣는다.
        for(int i=0; i<ground_List.size(); i++){    // +- 45 는 판정을 좋게 하기 위해 추가

            if(        x >= ground_List.get(i).get_Ground_Point_X() - 45
                    && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + 45
                    && y >= ground_List.get(i).get_Ground_Point_Y() - 45
                    && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + 45){

                ground_Remove_Temp = i;
                break;

            }

        }

        //선택된 달팽이가 존재 한다면. && 달팽이라면
        if(ground_Remove_Temp >= 0 && ground_List.get(ground_Remove_Temp).get_Ground_Class() == 1 && ground_Class == ground_List.get(ground_Remove_Temp).get_Ground_Class()) {
            //Log.d("aaaa", ground_Class + "a" + ground_List.get(ground_Remove_Temp).get_Ground_Class());
            if(ground_List.get(ground_Remove_Temp).get_Ground_Hp() != 1) //달팽이 피가 1일때 클릭당하면 아무 이펙트 없음 이유는 굳이 이 루틴 하나 때문에 많은 코드를 넣기 불편함과 동시에 부하
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tempInt = random.nextInt(4);

                        for(int j=0; j<effect_Pop2_img.length-1; j++)
                        {
                            try {
                                Thread.sleep(15);
                                if(ground_Remove_Temp < 0){
                                    break;
                                }
                                //팝 이펙트 random.nextInt(6)
                                if(tempInt == 0){
                                    effect_Temp = effect_Pop2_img[j];
                                }else if(tempInt == 1){
                                    effect_Temp = effect_Pop3_img[j];
                                }else if(tempInt == 2){
                                    effect_Temp = effect_Pop4_img[j];
                                }else {
                                    effect_Temp = effect_Pop5_img[j];
                                }

                                 draw.draw_Bmp(canvas, effect_Temp,
                                        ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getWidth()) - 35,
                                        ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getHeight()) - 35);

                            }catch (Exception e){
                            }
                        }
                    }
                }).start();


            //클릭된 달팽이의체력을 깍는다.
            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
            return true;

            //선택된 꽃게가 있다면.
        }else if(ground_Remove_Temp >= 0 && ground_List.get(ground_Remove_Temp).get_Ground_Class() == 2 && ground_Class == ground_List.get(ground_Remove_Temp).get_Ground_Class()){
            tempInt = random.nextInt(5);


            effect_Temp = effect_Pop1_img[tempInt];

            draw.draw_Bmp(canvas, effect_Temp,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Crab_img[0].getWidth())-35 ,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Crab_img[0].getHeight())-35);


            //드래그된 꽃게의 체력을 깍는다.
            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
            soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.3F, 0.3F, 1, 1, 1.0F);   //드래그 사운드
        }



        return false;
    }




    /**
     * 물고기 히트
     */
    public void fish_Hit_Chose(int fish_Class){

        //물고기 삭제 1번 먼저
        if(fish_List.size() != 0) {         //물고기가 존재할때 눌러짐
            eraser_Fish = false;            //물고기 지우기 허가하기


            /**
             * 가장 가까운 물고기를 찾는다.
             */
            for(int i=0; i<fish_List.size(); i++){

                //전달받은 물고기 인자가 아닐때 생깜
                if(fish_List.get(i).get_Fish_Class() != fish_Class){
                    continue;
                }

                //대각선 길이를 통해 가장 가까운 거리를 찾는다.
                smallMathResult = pythagoras(fish_List.get(i).get_Fish_Point_X() , fish_List.get(i).get_Fish_Point_Y());

                if(smallMathResult < smallFishTemp){
                    smallFishTemp = smallMathResult;
                    smallFishIndex = i;                     //for 문안에서 가장 가까운 물고기를 찾는다.
                    eraser_Fish = true;
                }

            }
            smallFishTemp = 1500; //제일 가까운 물고기 찾기위한 템프변수

            if(eraser_Fish){


                if(fish_Class == 1){        //전달 받은 인자가 기본 물고기 일때.

                    effect_Fish_Draw_X.add(fish_List.get(smallFishIndex).get_Fish_Point_X());       //터치된 자리에 이펙트 4단계를 거쳐야 함으로
                    effect_Fish_Draw_Y.add(fish_List.get(smallFishIndex).get_Fish_Point_Y());
                    effect_TempRandom_Temp.add(random.nextInt(4));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 2; i < 5; i++) {
                                try {
                                    Thread.sleep(15);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                for (int j = 0; j < effect_Fish_Draw_X.size(); j++) {


                                    //랜덤 이팩트
                                    if (effect_TempRandom_Temp.get(0) == 0) {
                                        effect_Temp = effect_Orange_img[i];
                                    } else if (effect_TempRandom_Temp.get(0) == 1) {
                                        effect_Temp = effect_Blue_img[i];
                                    } else if (effect_TempRandom_Temp.get(0) == 2) {
                                        effect_Temp = effect_Yellow_img[i];
                                    } else {
                                        effect_Temp = effect_Green_img[i];
                                    }
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            effect_Fish_Draw_X.get(j) - 15
                                            , effect_Fish_Draw_Y.get(j));
                                }
                            }
                            effect_Fish_Draw_X.remove(0);
                            effect_Fish_Draw_Y.remove(0);
                            effect_TempRandom_Temp.remove(0);
                        }
                    }).start();
                    soundPool.play(sound_Effect[random.nextInt(2)], 0.3F, 0.3F, 1, 1, 1.0F);   //물고기 기본 팝 사운드
                }



                if(fish_List.get(smallFishIndex).get_Fish_Hp() > 0) {        //드래그 속도까 빨라서 0밑으로내려감 방지
                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경




                    if(fish_Class == 2) {
                        //드래그시 공격당한다는 느낌 주기 위해
                        draw.draw_Bmp(canvas, effect_Pop1_img[random.nextInt(5)],
                                fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Drag_Default_img[0].getWidth() - 25),
                                fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Drag_Default_img[0].getHeight()) - 35);
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.3F, 0.3F, 1, 1, 1.0F);   //드래그 사운드
                    }
                }



            }else {
                Log.i("발 터짐",window_Width + " ");
            }


        }


    }


    /**
     * 피타 고라스 함수 정의, 핸드폰 최하단 좌표 400, 1000 이랑 비교
     * 가장 가까운 물고기를 찾기 위해서
     */
    public double pythagoras(double x, double y){

        //피타고라스 정의 사용하기 위해 큰 x,y 값 도출
        if(400 >= x){
            pointXBig = 400;
            pointXSmall = x;
        }else if(400 <= x){
            pointXBig = x;
            pointXSmall = 400;
        }


        if(1000 >= y){
            pointYBig = 1000;
            pointYSmall = y;
        }else if(1000 <= y){
            pointYBig = y;
            pointYSmall = 1000;
        }

        return Math.sqrt(Math.pow((pointXBig - pointXSmall), 2) + Math.pow((pointYBig - pointYSmall), 2));
    }


    //********************************************************************************************//


    /**
     * 터치 이벤트
     */
    @Override
    public synchronized boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){           //손가락이 눌렸을때.


            //퍼즈 컨트롤
            if(pause_Effect(event.getX(), event.getY())){

            }else if(ground_Hit_Chose(event.getX(), event.getY(), 1)){ //달팽이 삭제

            }else {
                //문어 공격 속도로 제어한다. 쿨타임 효과
                if (main_Character.get_Attack_Cool_time() == 0) {
                    fish_Hit_Chose(1);                              //기본 물고기 터치 확인 == 1
                    main_Character.set_Attack_Cool_Time();
                }
            }


        } else if(event.getAction() == MotionEvent.ACTION_UP){      //때졌을때.




        } else if(event.getAction() == MotionEvent.ACTION_MOVE){    //드래그 중일때

            drag_Action_Move++;
            if(drag_Action_Move > 2) {

                if(ground_Hit_Chose(event.getX(), event.getY(), 2)){    //꽃게 삭제

                }else {
                    fish_Hit_Chose(2);                                  //드래그 물고기

                }

                drag_Action_Move = 0;
            }


        }


        return true;

    }


    /**
     * 퍼즈 이벤트 게임 정지
     */
    public boolean pause_Effect(float x, float y){

        //window_Width - 100, 10
        //퍼지 이미지의 위치
        if(x >= window_Width - 100
                && x <= window_Width - 100 + pause_img[0].getWidth()
                && y >= 10
                && y <= 10 + + pause_img[0].getHeight()){
            pause_Push = true;
            mRun = false;  //일시정지 화면 띄워야 함.
            return true;
        }
        return false;


    }





    //********************************************************************************************//

    /**
     * 오버라이드 된것 시작과 동시에 구성, 교체
     * @param holder
     */


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Log.i("[뷰]", "구성");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Log.i("[뷰]", "교체");
        window_Width = width; //화면의 크기
        window_Height = height;

        //배경 화면 동적 움직임 생성
        background_Effect_One = new Background_Effect_One(window_Width, window_Height);
        background_Effect = new Thread(background_Effect_One);
        background_Effect.start();                   //쓰레드 작동 방법

        background_Effect_Two = new Background_Effect_Two(window_Width, window_Height);
        background_Effect = new Thread(background_Effect_Two);
        background_Effect.start();

        if(game_thread.getState() == Thread.State.TERMINATED) {
            game_thread = new Game_Thread(holder);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
            game_thread.start(); //게임

        }else {
            game_thread.start();
            //gameMainThread.start();
        }

        if(game_element_thread.getState() == Thread.State.TERMINATED) {
            game_element_thread = new Game_Element_Thread();  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
            game_element_thread.start(); //게임

        }else {
            game_element_thread.start();
            //gameMainThread.start();
        }

        mRun = true;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
       //Log.i("[뷰]", "파괴");

        try{
            game_thread.join(); //쓰레드 종료
            game_element_thread.join();
            //gameMainThread.join();
        }catch (Exception e){

        }



    }

    //********************************************************************************************//
}
