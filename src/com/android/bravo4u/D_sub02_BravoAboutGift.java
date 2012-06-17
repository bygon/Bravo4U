package com.android.bravo4u;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
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

	private TextView whoseGiftText;
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
	    
	    puzzleWidth= giftWidth/3;
	    puzzleHeight= giftHeight/3;
		
        
        server =new X_BravoWebserver(this);
		Button sendbtn = (Button)findViewById(R.id.sendBtn);
		sendbtn.setOnClickListener(this);
        
		init();
	}
    
    private void init() 
    {
    	titleText();
    	initPuzzleCellPoints();
    	initBitmaps();
        sendData(0);
	}
    
    private void titleText()
    {
		Intent intent =getIntent();
		String name= intent.getExtras().get("name").toString();
		whoseGiftText=(TextView)findViewById(R.id.whoseGiftText);
		whoseGiftText.setText(name+"���� Ī���������");
    }
    
    private void initPuzzleCellPoints() 
    {
    	//TODO ���� ���� �������� �ʳ� - ���� ������ ũ�Ⱑ ����Ǹ� ��� �ؾߵǳ�?
		puzzleCellPoints = new ArrayList<Point>();
    	puzzleCellPoints.add(new Point(0, 0));
    	puzzleCellPoints.add(new Point(puzzleWidth, 0));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, 0));
    	puzzleCellPoints.add(new Point(0, puzzleHeight));
    	puzzleCellPoints.add(new Point(puzzleWidth, puzzleHeight));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, puzzleHeight));
    	puzzleCellPoints.add(new Point(0, puzzleHeight*2));
    	puzzleCellPoints.add(new Point(puzzleWidth, puzzleHeight*2));
    	puzzleCellPoints.add(new Point(puzzleWidth*2, puzzleHeight*2));
	}
    
	private void initBitmaps() 
	{
		// TODO ���� ������ ũ�Ⱑ ������ ���ٵ� ���� ���� �ذ�å�� �ƴѰ� ����
		// TODO 300*300 ���� ���̸� ���簢���� �ƴ� ������ ���� Ʋ������ ����
		
		//-------------------- ��ǥ���� �ٿ�ε� ----------------------------------
		
		Intent getintent = getIntent();
		String phone_num = getintent.getExtras().get("phone_num").toString();

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
	
    
    public void sendData(int sendingnum)
    {
      
    	try {
			//TODO ����ڸ� ��ٸ��� �� ���ִ� ��� ����..�ᱹ..�����带 ����ϴ°�.. �Ф�
			
			Intent intent =getIntent();
			String str1= intent.getExtras().get("phone_num").toString();
			String phone_num = str1.substring(1);
			
			String sendingpz = Integer.toString(sendingnum);
			
			
        	String str = server.getComplimentNumData(phone_num, sendingpz);
        	int complimentCount =Integer.parseInt(str);
        	updateComplimentPuzzle(complimentCount);
        	
        	
        }catch (Exception e) 
        {
        	Toast.makeText(D_sub02_BravoAboutGift.this, "��Ʈ��ũ ���� �Դϴ�.", Toast.LENGTH_LONG).show();
        }
    	

    }

    public void onClick(View v)
    {
    	sendData(1);		
    	
    }
}
