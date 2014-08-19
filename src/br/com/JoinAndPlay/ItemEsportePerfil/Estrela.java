package br.com.JoinAndPlay.ItemEsportePerfil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.view.View;

public class Estrela extends View {


	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	Path p = new Path();  
Rect rect= new Rect();


	Path path = new Path();
	public Estrela(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public Estrela(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	public Estrela(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onDraw(Canvas cav){
		int y=getHeight();
		int x=getWidth();
		rect.set(0 , 0, x*30, y);
		p.moveTo((int)(x*0.5),(int)(y*0));   
		p.lineTo((int)(x*0.3),(int)(y*0.30));   
		p.lineTo((int)(x*0),(int)(y*0.30));   
		p.lineTo((int)(x*0.2),(int)(y*0.6));   
		p.lineTo((int)(x*0),(int)(y)); 
		p.lineTo((int)(x*0.5),(int)(y*0.8));   
		p.lineTo((int)(x),(int)(y));   

		p.lineTo((int)(x*0.8),(int)(y*0.6));   
		p.lineTo((int)(x*1),(int)(y*0.3));   
		p.lineTo((int)(x*0.7),(int)(y*0.3));   
		p.lineTo((int)(x*0.5),(int)(y*0));  
		p.close();

		//p.setLastPoint(x, (int)(y*0.3));
		paint.setShadowLayer((float) 0.1,2,2,Color.BLACK);

		paint.setColor(Color.rgb(255, 215, 0));
		paint.setStyle(Paint.Style.FILL_AND_STROKE); 
		cav.clipRect(rect, Op.INTERSECT);
		cav.drawPath(p, paint);

		paint.setColor(Color.BLACK);


	}

}
