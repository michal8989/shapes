package pl.com.andruszko.shapes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private DrawView drawView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	        setContentView(R.layout.activity_main);
	        drawView = (DrawView) findViewById(R.id.drawView);
	        
	        Button btAddShape = (Button) findViewById(R.id.btAddShape);
	        btAddShape.setOnClickListener(this);
			
	        Button btSuckIn = (Button) findViewById(R.id.btSuckIn);
	        btSuckIn.setOnClickListener(this);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public DrawView getDrawView() {
		return drawView;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.btAddShape: { drawView.addRandomShape(); } break;
			case R.id.btSuckIn: { drawView.suckIn(); } break;
		}
		
	}
		
}
