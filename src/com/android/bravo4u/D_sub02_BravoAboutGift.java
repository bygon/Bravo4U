package com.android.bravo4u;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class D_sub02_BravoAboutGift extends Activity implements View.OnClickListener
{

	TextView whoseGiftText;
	X_BravoWebserver server;
	private ArrayList<Point> puzzleCellPoints;
	private Bitmap giftBitmap;
	private Bitmap puzzleCellBitmap;
	private Canvas canvas;
	private Bitmap background;
	
    public void onCreate(Bundle savedInstanceState)
	{
		setTheme(android.R.style.Theme_NoTitleBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_sub02_bravoaboutgift);

        
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
    	puzzleCellPoints.add(new Point(100, 0));
    	puzzleCellPoints.add(new Point(200, 0));
    	puzzleCellPoints.add(new Point(0, 100));
    	puzzleCellPoints.add(new Point(100, 100));
    	puzzleCellPoints.add(new Point(200, 100));
    	puzzleCellPoints.add(new Point(0, 200));
    	puzzleCellPoints.add(new Point(100, 200));
    	puzzleCellPoints.add(new Point(200, 200));
	}
    
	private void initBitmaps() 
	{
		// TODO ���� ������ ũ�Ⱑ ������ ���ٵ� ���� ���� �ذ�å�� �ƴѰ� ����
		// TODO 300*300 ���� ���̸� ���簢���� �ƴ� ������ ���� Ʋ������ ����
		giftBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bbororo), 300, 300, false);
        puzzleCellBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nemo), 100, 100, false);
        
        background = Bitmap.createBitmap(giftBitmap.getWidth(), giftBitmap.getHeight(), Config.ARGB_8888);
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
