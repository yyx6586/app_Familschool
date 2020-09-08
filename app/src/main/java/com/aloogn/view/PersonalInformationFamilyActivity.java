package com.aloogn.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.util.StringUtil;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonalInformationFamilyActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private Button personalInformation_btn_save;
    private EditText personalInformation_et_studentNumber, personalInformation_et_studentName, personalInformation_et_studentSex,
            personalInformation_et_parentName, personalInformation_et_parentPhone;
    private CircleImageView personalInformation_circleImageView;
    private File picture;

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;
        private String studentName;
        private String studentSex;
        private String parentName;
        private String parentPhone;

        public MyTask(String account, String studentName, String studentSex, String parentName, String parentPhone) {
            this.account = account;
            this.studentName = studentName;
            this.studentSex = studentSex;
            this.parentName = parentName;
            this.parentPhone = parentPhone;
        }

        @Override
        protected String doInBackground(String... strings) {
            Map<String,Object> map = new HashMap<>();
            map.put("account",account);
            map.put("studentName",studentName);
            map.put("studentSex",studentSex);
            map.put("parentName",parentName);
            map.put("parentPhone",parentPhone);

            String token = (String) ((MyApplication)getApplication()).get("token",null);

            Call call = OkHttpUtil.getInstance().post("user/personalInformationFamily",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        String msg = (String) jsonObject.optString("msg");

                        Looper.prepare();
                        MyToastUtil.showLongToast(PersonalInformationFamilyActivity.this,msg);
                        Looper.loop();;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_family);

        init();

        mTitle.setText("个人信息");

        String account = (String) ((MyApplication)getApplication()).get("account",null);
        personalInformation_et_studentNumber.setText(account);

        personalInformation_btn_save.setOnClickListener(this);

        personalInformation_circleImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personalInformation_btn_save:

                String account = personalInformation_et_studentNumber.getText().toString();
                String studentName = personalInformation_et_studentName.getText().toString();
                String studentSex = personalInformation_et_studentSex.getText().toString();
                String parentName = personalInformation_et_parentName.getText().toString();
                String parentPhone = personalInformation_et_parentPhone.getText().toString();

                if(StringUtil.isNullOrEmpty(studentName)){
                    MyToastUtil.showLongToast(PersonalInformationFamilyActivity.this,"学生姓名不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(studentSex)){
                    MyToastUtil.showLongToast(PersonalInformationFamilyActivity.this,"学生性别不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(parentName)){
                    MyToastUtil.showLongToast(PersonalInformationFamilyActivity.this,"家长姓名不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(parentPhone)){
                    MyToastUtil.showLongToast(PersonalInformationFamilyActivity.this,"家长手机号不能为空");
                    return;
                }

                if(!StringUtil.isPhone(parentPhone)){
                    MyToastUtil.showLongToast(PersonalInformationFamilyActivity.this,"手机号码格式不正确，请重新输入");
                    return;
                }

                MyTask myTask = new MyTask(account,studentName,studentSex,parentName,parentPhone);
                myTask.execute();

                break;

            case R.id.personalInformation_circleImageView:
                changeImage();
        }
    }

    //修改头像
    public void changeImage(){
        final String font[] = { "拍照", "从图库选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 6){ //调用相机成功

            Uri uri = Uri.fromFile(picture);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            this.sendBroadcast(intent);  // 发送广播让MediaScanner 扫描我们制定的文件
            // 这样在系统的相册中我们就可以找到所拍摄的照片了
            Bitmap bitmap = BitmapFactory.decodeFile(picture.toString());
            personalInformation_circleImageView = findViewById(R.id.personalInformation_circleImageView);
            if(bitmap != null) {
                personalInformation_circleImageView.setImageBitmap(bitmap);
            }
        }else if(requestCode == 7) {
            //从图库选择照片
            try {
                if(data.getData() != null) {  //不选中直接返回
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    personalInformation_circleImageView = findViewById(R.id.personalInformation_circleImageView);
                    personalInformation_circleImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    cursor.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void init(){
        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        personalInformation_et_studentNumber = (EditText) findViewById(R.id.personalInformation_et_studentNumber);
        personalInformation_et_studentName = (EditText) findViewById(R.id.personalInformation_et_studentName);
        personalInformation_et_studentSex = (EditText) findViewById(R.id.personalInformation_et_studentSex);
        personalInformation_et_parentName = (EditText) findViewById(R.id.personalInformation_et_parentName);
        personalInformation_et_parentPhone = (EditText) findViewById(R.id.personalInformation_et_parentPhone);
        personalInformation_btn_save = (Button) findViewById(R.id.personalInformation_btn_save);
        personalInformation_circleImageView = (CircleImageView) findViewById(R.id.personalInformation_circleImageView);
    }
}
