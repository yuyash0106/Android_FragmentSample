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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListFragment extends Fragment {

    /**
     * 大画面かどうかの判定フラグ。
     * trueが大画面。falseが通常画面。
     * 判定ロジックは同一画面に注文完了表示用フレームレイアウトが存在するかどうかで行う。
     */
    private boolean _isLayoutXLarge = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        //親クラスのメソッド呼び出し。
        super.onActivityCreated(savedInstanceState);
        //自分が所属するアクティビティからMenuThanksFrameを取得。
        View menuThanksFrame = _parentActivity.findViewById(R.id.menuThanksFrame);
        //MenuThanksFrameがnull、つまり存在しないなら・・・
        if (menuThanksFrame == null){
            //画面判定フラグを通常画面とする。
            _isLayoutXLarge = false;
        }
    }

    /**
     * このフラグメントが所属するアクティビティオブジェクト。
     */
    private Activity _parentActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //親クラスのonCreate()の呼び出し。
        super.onCreate(savedInstanceState);
        //所属するアクティビティオブジェクトを取得。
        _parentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //フラグメントで表示する画面をｘｍｌファイルからインフラーとする。
        View view = inflater.inflate(R.layout.fragment_menu_list,container,false);
        //画面部品ListViewを取得。
        ListView lvMenu = view.findViewById(R.id.lvMenu);
        //SimpleAdapterで使用するListオブジェクトを用意。
        List<Map<String,String >> menuList = new ArrayList<>();

        //「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        Map<String,String> menu = new HashMap<>();
        menu.put("name","から揚げ定食");
        menu.put("price","800円");
        menuList.add(menu);
        //「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        menu=new HashMap<>();
        menu.put("name","ハンバーグ定食");
        menu.put("price","850円");
        menuList.add(menu);


        //SimpleAdapter第4引数from用データの用意。
        String[] from = {"name","price"};
        //SimpleAdapter第５引数from用データの用意。
        int[] to = {android.R.id.text1,android.R.id.text2};

        //SimpleAdapterを生成。
        SimpleAdapter adapter = new SimpleAdapter(_parentActivity,menuList,android.R.layout.simple_list_item_2,from,to);
        //アダプタの登録。
        lvMenu.setAdapter(adapter);
        //リスナの登録。
        lvMenu.setOnItemClickListener(new ListItemClickListener());
        //院フレートされた画面を戻り値として返す。
        return view;
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMap型！
            Map<String,String> item = (Map<String, String>) parent.getItemAtPosition(position);
            //定食名と金額を取得。
            String menuName = item.get("name");
            String menuPrice = item.get("price");

            //引継ぎデータをまとめて格納できるButtonオブジェクトを生成。
            Bundle bundle = new Bundle();
            //Buttonオブジェクトに引継ぎデータを格納。
            bundle.putString("menuName",menuName);
            bundle.putString("menuPrice",menuPrice);
            //大画面の場合。
            if (_isLayoutXLarge){
                //フラグメントマネージャーの取得。
                FragmentManager manager = getFragmentManager();
                //フラグメントトランザクションの開始。
                FragmentTransaction transaction = manager.beginTransaction();
                //注文完了フラグメントを生成。
                MenuThanksFragment menuThanksFragment = new MenuThanksFragment();
                //引継ぎデータを注文完了フラグメントに格納。
                menuThanksFragment.setArguments(bundle);
                //生成した注文完了フラグメントをMenuThanksFrameレイアウト部品に追加。
                transaction.replace(R.id.menuThanksFrame,menuThanksFragment);
                //フラグメントトランザクションのコミット。
                transaction.commit();
            }
            //通常画面の場合。
            else {
                //インテントオブジェクトを生成。
                Intent intent = new Intent(_parentActivity,MenuThanksActivity.class);
                //第2画面に送るデータを格納。ここではButtonオブジェクトとしてまとめて格納。
                intent.putExtras(bundle);
                //第2画面の起動。
                startActivity(intent);
            }

        }
    }
}

