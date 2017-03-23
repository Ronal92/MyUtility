package com.jinwoo.android.myutility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jinwoo.android.myutility.dummy.DummyContent;
import com.jinwoo.android.myutility.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FiveFragment extends Fragment {



    private final int REQ_CAMERA = 100;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    List<String> datas = new ArrayList<>();

    private static View view;
    MyItemRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    // 사진촬영후 임시로 저장할 파일 공간
    private Uri fileUri=null;






    public FiveFragment() {
    }


    @SuppressWarnings("unused")
    public static FiveFragment newInstance(int columnCount) {  // Method area 영역에 올라감
        FiveFragment fragment = new FiveFragment();
        Bundle args = new Bundle(); // Bundle 생성
        args.putInt(ARG_COLUMN_COUNT, columnCount); // Bundle에 columnCount 담는다.
        fragment.setArguments(args); // setArguments는 자동적으로 getArguments를 호출한다.
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // Heap 영역에 올라감.(객체생성하면)
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT); // setArguments에서 온거임.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if( view!=null ){
            return view;
        }
        Context context = getContext();
        view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // 카메라 처리
        Button btnCamera = (Button)view.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(clickListener);

        // 이미지 데이터 불러오기.
        datas = loadData();
        Log.d("DDP","================================= entered into onCreateView  :   " + datas.get(0));
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        if(mColumnCount<=1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context,mColumnCount));
        }
        adapter = new MyItemRecyclerViewAdapter(getContext(),datas);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<String> loadData() {
        // 폰에서 이미지를 가져온후 datas 에 세팅한다
        ContentResolver resolver = getContext().getContentResolver();
        // 1. 데이터 Uri 정의 ( 미디어 정보인 이미지를 가져오기 위한 Uri)
        Uri target = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 2. projection 정의 ( 어떤 데이터를 받을지 String 배열을 파라미터로 설정해 넘긴다. )
        String projection[] = { MediaStore.Images.ImageColumns.DATA };
        // 정렬 추가 - 날짜순 역순 정렬
        String order = MediaStore.Images.ImageColumns.DATE_ADDED+" DESC";

        // 3. 데이터 가져오기
        Cursor cursor = resolver.query(target, projection, null, null, order);
        if(cursor != null){ // cursor는 한행씩 참조한다. 여기서는 컬럼이 하나만 있기 때문에 getString(0)으로 처리한다.
            while(cursor.moveToNext()){
                String uriString = cursor.getString(0);
                datas.add(uriString);
            }
            cursor.close();
        }
        return datas;
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch(v.getId()){
                case R.id.btnCamera:
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 누가 버전부터 기본 Action Image Capture 로는 처리안됨
                    // --- 카메라 촬영 후 미디어 컨텐트 uri 를 생성해서 외부저장소에 저장한다 ---
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // 저장할 미디어 속성을 정의하는 클래스
                        ContentValues values = new ContentValues(1);
                        // 속성중에 파일의 종류를 정의
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        // 전역변수로 정의한 fileUri에 외부저장소 컨텐츠가 있는 Uri를 임시로 생성해서 넣어준다.
                        fileUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        // 위에서 생성한 fileUri를 사진저장공간으로 사용하겠다고 설정 (인텐트에 담는다.)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // Uri에 읽기와 쓰기 권한을 시스템에 요청(시스템으로 넘겨준다.)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    // --- 여기 까지 컨텐트 uri 강제세팅 ---
                    startActivityForResult(intent, REQ_CAMERA);
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQ_CAMERA:
                if(resultCode == RESULT_OK){
                    Log.d("DDP","================================= entered into onAcitivityResult() before :   " + datas.get(0));
                    //fileUri = data.getData();
                    datas = loadData();
                    Log.d("DDP","================================= entered into onAcitivityResult() after :   " + datas.get(0));
                    //어뎁터에 변경된 데이터 반영하기
                    adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
                    recyclerView.setAdapter(adapter);
                    // 얘만 호출하면 되야됨...
                    adapter.notifyDataSetChanged();
                }
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
