package com.android.bravo4u;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class D_sub03_BravoSelectPhoto extends Activity implements View.OnClickListener
{
	ImageView giftImg;
	Bitmap image_bitmap;
	Button changePhotoBtn;
	
	final int REQ_SELECT=0;
	
	private FileInputStream mFileInputStream = null;
	private URL connectUrl = null;
 
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";	
 
	private int deviceWidth;
	private int deviceHeight;
	private int giftWidth;
	private int giftHeight;
	
    public void onCreate(Bundle savedInstanceState)
	{
		setTheme(android.R.style.Theme_NoTitleBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_sub03_bravoselectphoto);
		
	    Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
	    deviceWidth = localDisplay.getWidth();
	    deviceHeight = localDisplay.getHeight();
	    
	    giftWidth = (deviceWidth/10) *8;
	    giftHeight = deviceHeight/2;

		
		giftImg =(ImageView)findViewById(R.id.giftImg);
		
		changePhotoBtn =(Button)findViewById(R.id.changePhotoBtn);
		changePhotoBtn.setOnClickListener(this);

		//��ǥ���� �ٿ�ε�
		
		Intent getintent = getIntent();
		String phone_num = getintent.getExtras().get("phone_num").toString();

		X_BravoWebserver server = new X_BravoWebserver(this);
		String imgurl = server.getUrlOnServer(phone_num);
		imgurl = imgurl.replace(" ", "%20");
		
		if(imgurl.contains(",")) imgurl = imgurl.replace(",", "");
		
		Toast.makeText(getApplicationContext(), imgurl, Toast.LENGTH_SHORT).show();
		
		X_BravoImageDownloader imageDownloader = new X_BravoImageDownloader();
		
		imageDownloader.download(imgurl, giftImg);
		
		
		

	}
    
    public void onClick(View v)
    {
	      
    	try {
			
	    	//���� �о�������� uri �ۼ��ϱ�.
	    	 Uri uri = Uri.parse("content://media/external/images/media");
	    	 //���� �����޶�� �Ͻ��� ����Ʈ ��ü �����ϱ�.
	         Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	         //����Ʈ�� ��û�� �����δ�. 
	         intent.setAction(Intent.ACTION_GET_CONTENT);
	         //��� �̹���
	         intent.setType("image/*");
	         //������� �޾ƿ��� ��Ƽ��Ƽ�� �����Ѵ�.
	         startActivityForResult(intent, REQ_SELECT);
	         
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
	 protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
	 {
			try{
				//����Ʈ�� �����Ͱ� ��� �Դٸ�
				if(!intent.getData().equals(null))
				{
				    //�ش����� �̹����� intent�� ��� �̹��� uri�� �̿��ؼ� Bitmap���·� �о�´�.
    				image_bitmap = Images.Media.getBitmap(getContentResolver(),intent.getData());
    				image_bitmap = Bitmap.createScaledBitmap(image_bitmap, giftWidth, giftHeight, false);
    				giftImg.setImageBitmap(image_bitmap);   
				}
			}catch(FileNotFoundException e) {
			    e.printStackTrace();
			}catch(IOException e) {
			    e.printStackTrace();
			}
			//������ �̹����� uri�� �о�´�.   
			Uri selPhotoUri = intent.getData();
		
			//���ε��� ������ url �ּ�
		    String urlString = "http://210.115.58.140/test7.php";
		    //�����θ� ȹ���Ѵ�!!! �߿�~
		    Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null,null,null,null);
		    c.moveToNext();
		    //���ε��� ������ ������ ������("_data") �� �ص� �ȴ�.
		    String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
		    Log.e("###������ ���� ���###", absolutePath);
		    
		   //���� ���ε� ����!
		   HttpFileUpload(urlString ,"", absolutePath);
		  
	    }
	 
		private void HttpFileUpload(String urlString, String params, String fileName) 
		{
			try {
				
				
				mFileInputStream = new FileInputStream(fileName);			
				connectUrl = new URL(urlString);
				Log.d("Test", "mFileInputStream  is " + mFileInputStream);
				
				// open connection 
				HttpURLConnection conn = (HttpURLConnection)connectUrl.openConnection();			
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				

				
				// write data
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());		
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName+"\"" + lineEnd);
				dos.writeBytes(lineEnd);
				

				
				int bytesAvailable = mFileInputStream.available();
				int maxBufferSize = 1024;
				int bufferSize = Math.min(bytesAvailable, maxBufferSize);
				
				byte[] buffer = new byte[bufferSize];
				int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
				
				Log.d("Test", "image byte is " + bytesRead);
				
				// read image
				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = mFileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
				}	
				
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				
				// close streams
				Log.e("Test" , "File is written");
				mFileInputStream.close();
				dos.flush(); // finish upload...			
				
				// get response
				int ch;
				InputStream is = conn.getInputStream();
				StringBuffer b =new StringBuffer();
				while( ( ch = is.read() ) != -1 )
				{
					b.append( (char)ch );
				}
				String s=b.toString().trim(); 
				String img_url = "http://210.115.58.140" +s;

				//Log.e("Test", "result = " + img_url);
				
				Intent get_intent01 =getIntent();
		    	String phone_num = get_intent01.getExtras().get("phone_num").toString().substring(1);
				
				X_BravoWebserver server = new X_BravoWebserver(this);
				String result = server.ImgUpdateOnServer(phone_num,img_url);
				
		    	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				dos.close();			
				
			} catch (Exception e) {
				Log.d("Test", "exception " + e.getMessage());
				// TODO: handle exception
			}		
		}
}