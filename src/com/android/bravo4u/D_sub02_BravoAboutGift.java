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
		if(name.equals("��")) whoseGiftText.setText(name+"�� Ī���������");
		else whoseGiftText.setText(name+"���� Ī���������");
    }
    
    private void promisePersonText()
    {
		String group_phone_num= getIntent().getExtras().get("group_phone_num").toString();
		String promisePersonData = server.getPromisePersonOnServer(group_phone_num);
		
	
		if(promisePersonData.equals("nobody")) pormiseText.setText("�ƹ��� ���� �Ȼ��� ��");
		else pormiseText.setText("���� ���ְڴٴ� ��� ��ȣ :"+ promisePersonData);
		
    }
    
    private void visibleSetting()
    {
		String name= getIntent().getExtras().get("name").toString();
		if(name.equals("��"))
		{
			sendbtn.setVisibility(sendbtn.GONE);
			promiseBtn.setVisibility(promiseBtn.GONE);
		}else
		{
			//pormiseText.setVisibility(pormiseText.GONE);
		}
    }
    
    private void initPuzzleCellPoints() 
    {
    	//TODO ���� ���� �������� �ʳ� - ���� ������ ũ�Ⱑ ����Ǹ� ��� �ؾߵǳ�?
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
		// TODO ���� ������ ũ�Ⱑ ������ ���ٵ� ���� ���� �ذ�å�� �ƴѰ� ����
		// TODO 300*300 ���� ���̸� ���簢���� �ƴ� ������ ���� Ʋ������ ����
		
		//-------------------- ��ǥ���� �ٿ�ε� ----------------------------------
		
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
        
        background = Bitmap.createBitmap(giftWidth, giftHeight, Config.ARGB_8888);
        canvas = new Canvas(background);

          
	}
	
	private void updateComplimentPuzzle(int ComplimentCount)
	{
		//TODO �������� Field �� ����� ������ ����� ������, ��ĥ �κе� ������ ����.
        canvas.drawBitmap(giftBitmap, 0, 0, null);
        
        for (int i = 0; i < ComplimentCount; i++) 
        {	
        	Point point = (Point)puzzleCellPoints.get(puzzleCellPoints.size()-i-1);
        	canvas.drawBitmap(puzzleCellBitmap, point.x, point.y, null);
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
        		
        		Toast.makeText(D_sub02_BravoAboutGift.this, name+ "���� Ī����ǥ�� �޼��߽��ϴ�!", Toast.LENGTH_LONG).show();
        	}
    		break;
    		
    	case R.id.promiseBtn:
    		String my_phone_num= getIntent().getExtras().get("my_phone_num").toString();
    		String group_phone_num= getIntent().getExtras().get("group_phone_num").toString();
    		
    		server.promisePersonUpdateOnServer(group_phone_num, my_phone_num);
    		Toast.makeText(getApplicationContext(),name +"���� ������ ���ֽñ�� ����ϼ̽��ϴ�.", Toast.LENGTH_LONG).show();
    		break;
    	}

    }	
    
    public Boolean sendData(int sendingnum)
    {
      
    	try {
			//TODO ����ڸ� ��ٸ��� �� ���ִ� ��� ����..�ᱹ..�����带 ����ϴ°�.. �Ф�
			
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
        		if(name.equals("��"))setNewImage(); // ���ο� �̹����� �������ش�.

        	}
        	
        	return true;
  	
        }catch (Exception e) 
        {
        	Toast.makeText(D_sub02_BravoAboutGift.this, "��Ʈ��ũ ���� �Դϴ�.", Toast.LENGTH_LONG).show();
        	return false;
        }
    }
    
    public Boolean sendCompliment(int sendingnum)
    {
      
    	try {
			//TODO ����ڸ� ��ٸ��� �� ���ִ� ��� ����..�ᱹ..�����带 ����ϴ°�.. �Ф� YEs
			
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
		alertDlg.setTitle("Ī����ǥ�� �޼��ϼ̽��ϴ�!");
		alertDlg.setMessage("���ο� ��ǥ������ �����Ͻðڽ��ϱ�?");
		alertDlg.setPositiveButton("��",new DialogInterface.OnClickListener() {

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
		alertDlg.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
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
		
		// ����

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
	        		setNewImage(); // ���ο� �̹����� �������ش�.
	        	}
			} else {
				Toast.makeText(context, "���������ϴ�", Toast.LENGTH_SHORT).show();
			}
		}
	}
}



  
  
  

