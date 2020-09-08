package com.aloogn.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.view.LoginActivity;
import com.aloogn.view.PersonalInformationFamilyActivity;
import com.aloogn.view.PersonalInformationSchoolActivity;
import com.aloogn.view.UpdatePasswordActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment{

    private ImageView my_imageView_into;
    private LinearLayout linearLayout_useHelp, linearLayout_versionInformation, linearLayout_updatePassword, linearLayout_returnLogin;
    private CircleImageView my_circleImageView;
    private File picture;
    private TextView my_tv_name;
    private String name;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);

//        my_tv_name = view.findViewById(R.id.my_tv_name);
//        my_tv_name.setText(name);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

//        name = (String) ((MyApplication)getActivity().getApplication()).get("name",null);

        my_circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });

        my_imageView_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer role = Integer.parseInt(String.valueOf(((MyApplication)getActivity().getApplication()).get("role",null)));

                if(role == 1){
                    startActivity(new Intent(getActivity(), PersonalInformationSchoolActivity.class));
                }

                if(role == 2){
                    startActivity(new Intent(getActivity(), PersonalInformationFamilyActivity.class));
                }
            }
        });

        linearLayout_useHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        linearLayout_versionInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        linearLayout_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),UpdatePasswordActivity.class));
            }
        });

        linearLayout_returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApplication)getActivity().getApplication()).clear();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }

    //修改头像
    public void changeImage(){
        final String font[] = { "拍照", "从图库选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(font, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(font[which]){
                    case "拍照":
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss",
                                Calendar.getInstance(Locale.CHINA)) + ".jpg";
                        picture = new File(Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + "/DCIM/Camera", fileName);
                        Uri imageUri = Uri.fromFile(picture);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//直接使用，没有缩小
                        startActivityForResult(intent,6);

                        break;
                    case "从图库选择":
                        Intent i = new Intent(
                                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 7);

                }
            }
        }).setNegativeButton("Cancle", null)
                .setPositiveButton("OK", null).create().show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 6){ //调用相机成功

            Uri uri = Uri.fromFile(picture);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            getActivity().sendBroadcast(intent);  // 发送广播让MediaScanner 扫描我们制定的文件
            // 这样在系统的相册中我们就可以找到所拍摄的照片了
            Bitmap bitmap = BitmapFactory.decodeFile(picture.toString());
            my_circleImageView = getActivity().findViewById(R.id.my_circleImageView);
            if(bitmap != null) {
                my_circleImageView.setImageBitmap(bitmap);
            }
        }else if(requestCode == 7) {
            //从图库选择照片
            try {
                if(data.getData() != null) {  //不选中直接返回
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    my_circleImageView = getActivity().findViewById(R.id.my_circleImageView);
                    my_circleImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    cursor.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void init(){
        my_imageView_into = (ImageView) getActivity().findViewById(R.id.my_imageView_into);
        linearLayout_useHelp = (LinearLayout) getActivity().findViewById(R.id.linearLayout_useHelp);
        linearLayout_versionInformation = (LinearLayout) getActivity().findViewById(R.id.linearLayout_versionInformation);
        linearLayout_updatePassword = (LinearLayout) getActivity().findViewById(R.id.linearLayout_updatePassword);
        linearLayout_returnLogin = (LinearLayout) getActivity().findViewById(R.id.linearLayout_returnLogin);
        my_circleImageView = (CircleImageView) getActivity().findViewById(R.id.my_circleImageView);
    }
}
