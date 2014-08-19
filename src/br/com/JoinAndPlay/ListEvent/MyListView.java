package br.com.JoinAndPlay.ListEvent;

import br.com.JoinAndPlay.R;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class MyListView extends ListView {

	private void config(Context context){
		
		//redundancia para versoes antigas do android
		this.setDivider(getResources().getDrawable(R.drawable.linha));
		this.setDividerHeight(20);
		View v=new View(context);
		v.setMinimumHeight(15);
		this.addHeaderView(v);
	}
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		config(context);
	}
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		config(context);

	}
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		config(context);

	}
	Boolean db=true;
@Override 
protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
	if(db){
		
	return super.drawChild(canvas, child, drawingTime);
	}
	  // get top left coordinates
    int left = child.getLeft();
    int top = child.getTop();

    // get offset to center
    int centerX = child.getWidth() / 2;
    int centerY = child.getHeight() / 2;

    // get absolute center of child
    float pivotX = left + centerX;
    float pivotY = top + centerY;

    // calculate distance from center
    float centerScreen = getHeight() / 2;
    float distFromCenter = (pivotY - centerScreen) / centerScreen;

    // calculate scale and rotation
    float scale = (float)(1 - 0.9 * (1 - Math.cos(distFromCenter)));    if(distFromCenter<0) distFromCenter=-distFromCenter;

    //float rotation = 1 * distFromCenter;
    canvas.save();

   /* Camera a = new Camera();
   a.translate(0, 0, 0);
a.rotateX(-rotation);
    Matrix matrix = new Matrix();
	a.getMatrix(matrix );
   canvas.concat(matrix);
	// draw Round rectangle to canvas
   */
    // create Xfer mode
    //canvas.rotate(rotation, pivotX, pivotY);
    canvas.scale(scale, scale, pivotX, pivotY);
    super.drawChild(canvas, child, drawingTime);
    canvas.restore();
    return false;


}
	
	

}
