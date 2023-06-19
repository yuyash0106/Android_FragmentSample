package com.websarva.wings.android.fragmentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MenuThanksFragment extends Fragment {
    /**
     * 大画面かどうかの判定フラグ。
     * trueが大画面。falseが通常画面。
     * 判定ロジックは同一画面にリストフラグメントが存在するかどうかで行う。
     */
    private boolean _isLayoutXLarge = true;


    /**
     * このフラグメントが所属するアクティビティオブジェクト。
     */
    private Activity _parentActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuThanksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuThanksFragment newInstance(String param1, String param2) {
        MenuThanksFragment fragment = new MenuThanksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //親クラスのおｎCreate（）の呼び出し。
        super.onCreate(savedInstanceState);
        //所属するアクティビティオブジェクトを取得。
        _parentActivity = getActivity();

        //フラグメントマネージャーを取得。
        FragmentManager manager = getFragmentManager();
        //フラグメントマネージャーからメニューリストフラグメントを取得。
        MenuListFragment menuListFragment = (MenuListFragment) manager.findFragmentByTag(String.valueOf(R.id.fragmentMenuList));
        //メニューリストフラグメントかnull、
        if (menuListFragment == null){
            //画面判定フラグを通常画面とする。
            _isLayoutXLarge = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //フラグメントで表示する画面をXMLファイルからインフレートする。
        View view = inflater.inflate(R.layout.fragment_menu_thanks,container,false);

        //Buttonオブジェクトを宣言。
        Bundle extras;
        //大画面の場合・・・
        if (_isLayoutXLarge){
            //このフラグメントに埋め込まれた引継ぎデータを取得。
            extras = getArguments();
        }
        //通常画面の場合。
        else{
            //所属アクティビティからインテントを取得。
            Intent intent = _parentActivity.getIntent();
            //インテントから引継ぎデータをまとめたもの（Buttonオブジェクト）を取得。
            extras = intent.getExtras();
        }

        //所属アクティビティからインテントを取得。
        Intent intent = _parentActivity.getIntent();

        //注文した定食名と金額変数を用意。引継ぎデータがうまく取得できなかったときのために””で初期化。
        String menuName = "";
        String menuPrice = "";
        //引継ぎデータ（Buttonオブジェクト）が存在すてば。。。
        if (extras != null){
            //定食名と金額を取得。
            menuName = extras.getString("menuName");
            menuPrice = extras.getString("menuPrice");
        }
        //定食名と金額を表示させるTextViewを取得。
        TextView tvMenuName = view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice = view.findViewById(R.id.tvMenuPrice);

        //戻るボタンを取得。
        Button btBackButton = view.findViewById(R.id.btBackButton);
        //戻るボタンにリスナを登録。
        btBackButton.setOnClickListener(new ButtonClickListener());

        //インフレートされた画面を戻り値として返す。
        return view;
    }

    /**
     * ボタンが押されたときの処理が記述されたメンバクラス。
     */
    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view){
            //大画面の場合
            if (_isLayoutXLarge){
                //フラグメントマネージャーを取得。
                FragmentManager manager = getFragmentManager();
                //フラグメントトランザクションの開始。
                FragmentTransaction transaction = manager.beginTransaction();
                //自分自身を削除。
                transaction.remove(MenuThanksFragment.this);
                //フラグメントトランザクションのコミット。
                transaction.commit();
            }
            else {
                //自分自身が所属するアクティビティを終了。
                _parentActivity.finish();
            }
            _parentActivity.finish();
        }
    }
}