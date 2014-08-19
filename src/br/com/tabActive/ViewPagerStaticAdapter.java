package br.com.tabActive;

import br.com.JoinAndPlay.R;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerStaticAdapter extends PagerAdapter {
	LayoutInflater inflater;
	public  ViewPagerStaticAdapter( LayoutInflater inflater ) {
		super();
		this.inflater=inflater;
		// TODO Auto-generated method stub

	}
	@Override
	public void destroyItem(ViewGroup pager, int position, Object object) {
		//	((ViewPager) pager).removeView((View) object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TabFragment.SIZE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (View)arg1;
	}

	int getid(int position){
		int id=0;
		switch (position) {
		case 0:
			id=R.id.page_1;
			break;
		case 1:
			id=R.id.page_2;
			break;
		case 2:
			id=R.id.page_3;
			break;
		case 3:
			id=R.id.page_4;
			break;
		case 4:
			id=R.id.page_5;
			break;

		default:
			break;
		}
		return id;

	}
	@Override
	public Object instantiateItem(ViewGroup pager, int position) {
		//=inflater.inflate(R.layout.tela_aba_fragment_1, pager,false);//new View(pager.getContext());
		// imagem.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		int id=getid(position);
		View v =pager.findViewById(getid(position));
		///v.postInvalidate();
				v.getParent().requestLayout();
		((View) v.getParent()).invalidate();
		return v;
	}


}
