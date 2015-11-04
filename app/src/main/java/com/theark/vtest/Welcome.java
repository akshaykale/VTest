package com.theark.vtest;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class Welcome extends ActionBarActivity {
	
	public static final String SP_NAME = "com.theark.adobe";
	Button bt_go;
	Button bt_teacherSignUp, bt_studentSignUp;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        String ch = sp.getString(TeacherSignUp.SIGNUP_AS, "");
        if(ch.equals("")){
    		
    	}else if(ch.equals("t")){
    		startActivity(new Intent(Welcome.this,TeacherDashboard.class));
    		finish();
    	}else if(ch.equals("s")){
    		startActivity(new Intent(Welcome.this,StudentDashboard.class));
    		finish();
    	}
    	
    	init();
    	
       // bt_go = (Button) findViewById(R.id.bt_letsgo);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final ParallaxViewPager parallaxViewPager = ((ParallaxViewPager) findViewById(R.id.parallaxviewpager));
        parallaxViewPager
            .setOverlapPercentage(0.95f)
            .setAdapter(new MyAdapter(fragmentManager));

        parallaxViewPager.setPageTransformer(false, new PageTransformer() {
    		
    		@Override
    		public void transformPage(View view, float position) {
    			int pageWidth = view.getWidth();
    			
    			ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7;
/*    			iv1 = (ImageView) findViewById(R.id.i1);
    			iv2 = (ImageView) findViewById(R.id.i2);
    			iv3 = (ImageView) findViewById(R.id.i3);
    			iv4 = (ImageView) findViewById(R.id.i4);
    			iv5 = (ImageView) findViewById(R.id.i5);
    			iv6 = (ImageView) findViewById(R.id.i6);
    			iv7 = (ImageView) findViewById(R.id.i7);*/
     	       
    			//ImageView img = (ImageView) findViewById(R.id.imgadobe);
    	        //TextView tv1 = (TextView) findViewById(R.id.textView1);
    		    if (position < -1) { // [-Infinity,-1)
    		        // This page is way off-screen to the left.
    		        view.setAlpha(0);
    		 
    		    } else if (position <= 1) { // [-1,1]
    		          
    		    	//img.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
    				
    				//iv2.setTranslationX((float) (-(1 - position) * pageWidth));
    				 
/*    				iv6.setTranslationX((position) * (pageWidth / 2));
    		 		iv7.setTranslationX(-(position) * (pageWidth / 2));
    				iv1.setTranslationX((position) * (pageWidth / 2));
    				iv2.setTranslationX(-(position) * (pageWidth / 2));
    				iv3.setTranslationX((position) * (pageWidth / 4));
    				iv5.setTranslationX(-(position) * (pageWidth / 4));*/
    		 
    						  
    				  
    		    } else { // (1,+Infinity]
    		        view.setAlpha(0);
    		    }
    		}
           	});
        

    }
    
    private void init() {
    	bt_teacherSignUp = (Button) findViewById(R.id.bt_teachersignup);
    	bt_studentSignUp = (Button) findViewById(R.id.bt_studentsignup);
	}

	public void signupAsTeacher(View v){
    	SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
    	Editor editor = sp.edit();
    	editor.putInt("isfirsttime", 0);
    	editor.commit();
    	startActivity(new Intent(Welcome.this,TeacherSignUp.class));
    	finish();
    }
	public void signupAsStudent(View v){
    	SharedPreferences sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);
    	Editor editor = sp.edit();
    	editor.putInt("isfirsttime", 0);
    	editor.commit();
    	startActivity(new Intent(Welcome.this,StudentSignUp.class));
    	finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_submit) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

class MyAdapter extends FragmentPagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		
		if(arg0==0){
			fragment = new FragmentA();
		}if(arg0==1){
			fragment = new FragmentB();
		}if(arg0==2){
			fragment = new FragmentC();
		}
		
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
}
