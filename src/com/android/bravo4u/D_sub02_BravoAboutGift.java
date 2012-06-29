package com.android.bravo4u;

import java.lang.reflect.Member;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class D_sub02_BravoAboutGift extends Activity implements View.OnClickListener
{

	private TextView whoseGiftText ,pormiseText;
	private Button sendbtn, promiseBtn;
	private X_BravoWebserver server;
	private ArrayList<Point> puzzleCellPoints;
	private Bitmap giftBitmap;
	private Bitmap puzzleCellBitmap;
	private Bitmap puzzleBitmap0,puzzleBitmap1,puzzleBitmap2,puzzleBitmap3,puzzleBitmap4,
					puzzleBitmap5,puzzleBitmap6,puzzleBitmap7,puzzleBitmap8,puzzleBitmap9,
					puzzleBitmap10,puzzleBitmap11,puzzleBitmap12,puzzleBitmap13,puzzleBitmap14,
					puzzleBitmap15,puzzleBitmap16,puzzleBitmap17,puzzleBitmap18,puzzleBitmap19,
					puzzleBitmap20,puzzleBitmap21,puzzleBitmap22,puzzleBitmap23,puzzleBitmap24;
	private Bitmap[] puzzleBitmaps;
	
	private Canvas canvas;
	private Bitmap background;
	
	private int deviceWidth;
	private int deviceHeight;
	private int giftWidth;
	private int giftHeight;
	private int puzzleWidth;
	private int puzzleHeight;
	
	private int CountState =0;
	

	
    public void onCreate(Bundle savedInstanceState)
	{
		setTheme(android.R.style.Theme_NoTitleBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_sub02_bravoaboutgift);

		
	    Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
	    deviceWidth = localDisplay.getWidth();
	    deviceHeight = localDisplay.getHeight();
	    
	    giftWidth = (deviceWidth/10) *8;
	    giftHeight = deviceHeight/2;
	    
	    puzzleWidth= giftWidth/5;
	    puzzleHeight= giftHeight/5;
		
	    pormiseText =(TextView)findViewById(R.id.pormiseText);
	    
        server =new X_BravoWebserver(this);
        
		sendbtn = (Button)findViewById(R.id.sendBtn);
		promiseBtn = (Button)findViewById(R.id.promiseBtn);
		sendbtn.setOnClickListener(this);
		promiseBtn.setOnClickListener(this);
        
		
	}
    
    public void onResume()
    {
    	super.onResume();
    	init();
    }
    
    private void init() 
    {
    	titleText();
    	initPuzzleCellPoints();
    	initBitmaps();
    	visibleSetting();
    	promisePersonText();
        sendData(0);
	}
    
    private void titleText()
    {
		Intent intent =getIntent();
		String name= intent.getExtras().get("name").toString();
		whoseGiftText=(TextView)findViewById(R.id.whoseGiftText);
		if(name.equals("나")) whoseGiftText.setText(name+"의 칭찬퍼즐상태");
		else whoseGiftText.setText(name+"님의 칭찬퍼즐상태");
    }
    
    private void promisePersonText()
    {
		String group_phone_num= getIntent().getExtras().get("group_phone_num").toString();
		String promisePersonData = server.getPromisePersonOnServer(group_phone_num);
		String promise_person_name = "";
   		
		X_BravoGetAddress getAddress =new X_BravoGetAddress(this);
		ArrayList<String> groupArray = getAddress.arrangeAddress();
		String my_phone_num= getIntent().getExtras().get("my_phone_num").toString();
		groupArray.add("내"+"#"+my_phone_num);
		
		//Toast.makeText(getApplicationContext(), promisePersonData+ , duration)
		for(int i=0; i<groupArray.size(); i++)
        {
    		String[] array = groupArray.get(i).split("#");

    		String phone_num = array[array.length-1];
    		
    		if(phone_num.equals(promisePersonData))
    		{
    			promise_person_name = array[array.length-2]; // 폰번호에 해당하는 이름
    			//String phoneNum = array[array.length-1];
    		}else
    		{
    			promise_person_name = "["+promisePersonData+"]의 분";
    		}
        }
	
		if(promisePersonData.equals("nobody")) pormiseText.setText("아무도 선물 안사줌 ㅠ");
		else pormiseText.setText(promise_person_name+"가(이) 선물을 사주기로 약속했습니다.");
		
    }
    
    private void visibleSetting()
    {
//		String name= getIntent().getExtras().get("name").toString();
//		if(name.equals("나"))
//		{
//			sendbtn.setVisibility(sendbtn.GONE);
//			promiseBtn.setVisibility(promiseBtn.GONE);
//		}else
//		{
//			//pormiseText.setVisibility(pormiseText.GONE);
//		}
    }
    
    private void initPuzzleCellPoints() 
    {
    	//TODO 별로 좋아 보이지는 않네 - 선물 사진의 크기가 변경되면 어떻게 해야되나?
		puzzleCellPoints = new ArrayList<Point>();
    	puzzleCellPoints.add(new Point(0, 0));
    	puzzleCellPoints.add(new Point(puzzleWidth, 0));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, 0));
    	puzzleCellPoints.add(new Point(puzzleWidth*3, 0));
    	puzzleCellPoints.add(new Point(puzzleWidth*4, 0));
    	
    	puzzleCellPoints.add(new Point(0, puzzleHeight));
    	puzzleCellPoints.add(new Point(puzzleWidth, puzzleHeight));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, puzzleHeight));
    	puzzleCellPoints.add(new Point(puzzleWidth*3, puzzleHeight));
    	puzzleCellPoints.add(new Point(puzzleWidth*4, puzzleHeight));
    	
    	puzzleCellPoints.add(new Point(0, puzzleHeight*2));
    	puzzleCellPoints.add(new Point(puzzleWidth, puzzleHeight*2));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, puzzleHeight*2));
    	puzzleCellPoints.add(new Point(puzzleWidth*3, puzzleHeight*2));
    	puzzleCellPoints.add(new Point(puzzleWidth*4, puzzleHeight*2));
    	
    	puzzleCellPoints.add(new Point(0, puzzleHeight*3));
    	puzzleCellPoints.add(new Point(puzzleWidth, puzzleHeight*3));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, puzzleHeight*3));
    	puzzleCellPoints.add(new Point(puzzleWidth*3, puzzleHeight*3));
    	puzzleCellPoints.add(new Point(puzzleWidth*4, puzzleHeight*3));
    	
    	puzzleCellPoints.add(new Point(0, puzzleHeight*4));
    	puzzleCellPoints.add(new Point(puzzleWidth, puzzleHeight*4));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, puzzleHeight*4));
    	puzzleCellPoints.add(new Point(puzzleWidth*3, puzzleHeight*4));
    	puzzleCellPoints.add(new Point(puzzleWidth*4, puzzleHeight*4));
    	
    	
	}
    
	private void initBitmaps() 
	{
		// TODO 선물 사진의 크기가 제각기 일텐데 별로 좋은 해결책이 아닌거 같음
		// TODO 300*300 으로 줄이면 정사각형이 아닌 사진은 많이 틀어져서 보임
		
		//-------------------- 목표사진 다운로드 ----------------------------------
		
		Intent getintent = getIntent();
		String phone_num = getintent.getExtras().get("group_phone_num").toString();

		X_BravoWebserver server = new X_BravoWebserver(this);
		String imgurl = server.getUrlOnServer(phone_num);
		imgurl = imgurl.replace(" ", "%20");
		
		if(imgurl.contains(",")) imgurl = imgurl.replace(",", "");
		
		Toast.makeText(getApplicationContext(), imgurl, Toast.LENGTH_SHORT).show();
		

		X_BravoImageDownloader imageDownloader = new X_BravoImageDownloader();
		
		giftBitmap= imageDownloader.download(imgurl, null);
		giftBitmap = Bitmap.createScaledBitmap(giftBitmap, giftWidth, giftHeight, false);
        puzzleCellBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nemo), puzzleWidth, puzzleHeight, false);
        puzzleDecodeResource();
        background = Bitmap.createBitmap(giftWidth, giftHeight, Config.ARGB_8888);
        canvas = new Canvas(background);

          
	}
	
	public void puzzleDecodeResource()
	{
		puzzleBitmaps = new Bitmap[25];
		
		for(int i=0; i< puzzleBitmaps.length; i++)
		{
			int resID = getResources().getIdentifier("puzzle_"+i, "drawable", "com.android.bravo4u");
			puzzleBitmaps[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), resID),
					 puzzleWidth+20, puzzleHeight+20, false);
		}

	 
	}
	
	private void updateComplimentPuzzle(int ComplimentCount)
	{
		//TODO 변수들을 Field 로 만들어 버려서 사용은 쉽지만, 고칠 부분도 있을거 같음.
        canvas.drawBitmap(giftBitmap, 0, 0, null);
        
//        for (int i = 0; i < ComplimentCount; i++) 
//        {	
//        	Point point = (Point)puzzleCellPoints.get(puzzleCellPoints.size()-i-1);
//        	canvas.drawBitmap(puzzleCellBitmap, point.x, point.y, null);
//        }
        for (int i = 0; i < ComplimentCount; i++) 
        {	
//        	Point point = (Point)puzzleCellPoints.get(puzzleCellPoints.size()-i-1);
        	Point point = (Point)puzzleCellPoints.get(i);
        	canvas.drawBitmap(puzzleBitmaps[i], point.x, point.y, null);
        }
        
        ImageView img = (ImageView)findViewById(R.id.img);
        img.setImageBitmap(background);
	}
	  

    public void onClick(View v)
    {
    	String name= getIntent().getExtras().get("name").toString();
    	switch(v.getId())
    	{
    	case R.id.sendBtn:
    		
        	if(CountState >0)
    		{

        		SendingPuzzle sendpuzzle = new SendingPuzzle(this);
        		sendpuzzle.execute();
    		
    		}
        	else if(CountState==0)
        	{	
        		
        		Toast.makeText(D_sub02_BravoAboutGift.this, name+ "님은 칭찬목표를 달성했습니다!", Toast.LENGTH_LONG).show();
        	}
    		break;
    		
    	case R.id.promiseBtn:
    		String my_phone_num= getIntent().getExtras().get("my_phone_num").toString();
    		String group_phone_num= getIntent().getExtras().get("group_phone_num").toString();
    		
    		server.promisePersonUpdateOnServer(group_phone_num, my_phone_num);
    		Toast.makeText(getApplicationContext(),name +"님의 선물을 사주시기로 약속하셨습니다.", Toast.LENGTH_LONG).show();
    		break;
    	}

    }	
    
    public Boolean sendData(int sendingnum)
    {
      
    	try {
			//TODO 사용자를 기다리게 할 수있는 방법 없뜸..결국..쓰레드를 써야하는가.. ㅠㅠ
			
			Intent intent =getIntent();
			String str1= intent.getExtras().get("group_phone_num").toString();
			String group_phone_num =str1;
			
//			if(str1.substring(0,1).equals("0"))
//			{
//				group_phone_num = str1.substring(1);
//			}
			
			String sendingpz = Integer.toString(sendingnum);
			
        	String str = server.getComplimentNumData(group_phone_num, sendingpz);
        	int complimentCount = Integer.parseInt(str);
        	CountState= complimentCount;
        	updateComplimentPuzzle(complimentCount);
        	
        	if(CountState==0)
        	{
        		String name= getIntent().getExtras().get("name").toString();
        		if(name.equals("나"))setNewImage(); // 새로운 이미지를 지정해준다.

        	}
        	
        	return true;
  	
        }catch (Exception e) 
        {
        	Toast.makeText(D_sub02_BravoAboutGift.this, "네트워크 오류 입니다.", Toast.LENGTH_LONG).show();
        	return false;
        }
    }
    
    public Boolean sendCompliment(int sendingnum)
    {
      
    	try {
			//TODO 사용자를 기다리게 할 수있는 방법 없뜸..결국..쓰레드를 써야하는가.. ㅠㅠ YEs
			
			String phoneNumber = getIntent().getExtras().get("group_phone_num").toString();
			
//			if(phoneNumber.substring(0,1).equals("0")) {
//				phoneNumber = phoneNumber.substring(1);
//			}
			
			String sendingpz = Integer.toString(sendingnum);
        	CountState= Integer.parseInt(server.getComplimentNumData(phoneNumber, sendingpz));
        	
        	return true;
  	
        }catch (Exception e) {
        	return false;
        }
    }
    

    
    public void setNewImage()
    {
    	AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
		alertDlg.setTitle("칭찬목표를 달성하셨습니다!");
		alertDlg.setMessage("새로운 목표선물을 지정하시겠습니까?");
		alertDlg.setPositiveButton("네",new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) 
			{
				
				Intent get_intent01 =getIntent();
		    	String phone_numstr = get_intent01.getExtras().get("group_phone_num").toString();
		    	
				Intent intent =new Intent(D_sub02_BravoAboutGift.this,D_sub03_BravoSelectPhoto.class);
				intent.putExtra("phone_num", phone_numstr);
				startActivity(intent);
				
        		sendData(25);
				
				dialog.dismiss();
				
				
			}
		});
		alertDlg.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
		});
		alertDlg.show();
    }
    

	
	private class SendingPuzzle extends AsyncTask<Void, Void, Boolean> {

		private Context context;
		private ProgressDialog progressDialog = null;

		public SendingPuzzle(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Sending puzzle. Please wait...");
			progressDialog.show();
		}
		
		// 고고고

		@Override
		protected Boolean doInBackground(Void... unused) {
			return sendCompliment(1);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			if (result) {
				updateComplimentPuzzle(CountState);
	        	if(CountState==0) {
	        		setNewImage(); // 새로운 이미지를 지정해준다.
	        	}
			} else {
				Toast.makeText(context, "에러났습니다", Toast.LENGTH_SHORT).show();
			}
		}
	}
}



  
  
  

