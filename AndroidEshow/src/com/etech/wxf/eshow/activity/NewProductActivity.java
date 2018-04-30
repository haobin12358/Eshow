package com.etech.wxf.eshow.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.common.HttppostEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewProductActivity extends Activity{
	
	private String url_new_product = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/products/new_product";
	
	private TextView tv1, tv3, tv5, tv7, tvtitle, tvedit;
	private EditText et1, et3, et5, et7;
	private ViewGroup tv_top;
	private Button btn1;
	
	private HttppostEntity postEntity;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.item_new_product);
		init();
	}
	
	private String Uid;
	private int index;
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			Uid = bd.getString("Uid");
			index = bd.getInt("index");
		}catch(Exception e){
			e.printStackTrace();
			index = 0;
		}
	}
	
	void init(){
		tv_top = (ViewGroup)findViewById(R.id.tv_top);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvedit = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		et1 = (EditText)findViewById(R.id.et_1);
		et3 = (EditText)findViewById(R.id.et_3);
		et5 = (EditText)findViewById(R.id.et_5);
		et7 = (EditText)findViewById(R.id.et_7);
		btn1 = (Button)findViewById(R.id.btn_1);
		btn1.setOnClickListener(new_product);

		tvtitle.setText("发布商品");
		tvedit.setText("返回");
		tvedit.setOnClickListener(back);
	}
	
	private String response;
	private OnClickListener new_product = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			try {
				final JSONObject obj = new JSONObject();
				obj.put("Pno", et1.getText().toString());
				obj.put("Pname", et3.getText().toString());
				obj.put("Pbrand", et5.getText().toString());
				obj.put("Psize", Integer.parseInt(et7.getText().toString()));
				if(obj != null){
					new Thread(){
						public void run(){
							try {
								response = postEntity.doPost(obj, url_new_product);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}.start();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(true){
				if(response != null){
					break;
				}
			}
			Intent intent = new Intent(NewProductActivity.this, MainActivity.class);
			intent.putExtra("index", 0);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private OnClickListener back = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(NewProductActivity.this, MainActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 0);
			startActivity(intent);
			finish();
		}
		
	};

	
}
