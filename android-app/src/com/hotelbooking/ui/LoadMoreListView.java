package com.hotelbooking.ui;

import com.hotelbooking.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadMoreListView extends ListView{

	// Views
	private Context context;
	private View rlFooter;
	private TextView tvHint;
	private ProgressBar pgbCircle;
	private int footerViewHeight;
	private OnLoadMoreListener onLoadMoreListener = null;
	
	// Constructors.
	public LoadMoreListView(Context context, AttributeSet attrs,
	         int defStyle) {
	     super(context, attrs, defStyle);
	     this.context = context;
	     init(context);
	}
	public LoadMoreListView(Context context, AttributeSet attrs) {
	     super(context, attrs);
	     this.context = context;
	     init(context);
	}
	
	public LoadMoreListView(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	// Animations
	private Animation hideAnimation;
	
	// Init views and animations.
	private void init(Context context)
	{
		super.setOnScrollListener(new OnMoreLvScrollListener(context));
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rlFooter = inflater.inflate(R.layout.list_footer, null);
		tvHint = (TextView) rlFooter.findViewById(R.id.text_header_hint);
		pgbCircle = (ProgressBar) rlFooter.findViewById(R.id.progress_bar_header);
		measureHeaderView(rlFooter);
		footerViewHeight = rlFooter.getMeasuredHeight();
		rlFooter.invalidate();
		setFooterPadding(-footerViewHeight);
		addFooterView(rlFooter);
		
		hideAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		hideAnimation.setDuration(150);
		hideAnimation.setFillAfter(true);
	}
	
	public interface OnLoadMoreListener
	{
		public void onLoadMore();
	}
	
	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
	{
		this.onLoadMoreListener = onLoadMoreListener;
	}
	
	public void onLoadComplete()
	{
		changeState(State.ORIGIN);
	}
	
	public void onLoadEnd()
	{
		changeState(State.END);
	}
	
	public void onLoadFail()
	{
		changeState(State.FAIL);
	}

	// Motions related.
	private int currentFooterState = State.ORIGIN;
	private int currentFooterPadding;
	
	private class State
	{
		public static final int ORIGIN = 0;
		public static final int LOADING = 1; 
		public static final int END = 2;
		public static final int FAIL = 3;
	}
	
	private void setFooterPadding(int padding)
	{
		this.currentFooterPadding = padding;
		rlFooter.setPadding(0, 0, 0, padding);
	}
	
	private void resetFooter(int toPadding)
	{
		resetFooter(toPadding, 0);
	}
	
	private void resetFooter(final int toPadding, int delay)
	{
		ValueAnimator va = ValueAnimator.ofInt(currentFooterPadding, toPadding);
	    va.setDuration(150);
	    va.setStartDelay(delay);
	    va.setInterpolator(new AccelerateDecelerateInterpolator());
	    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	        public void onAnimationUpdate(ValueAnimator animation) {
	            Integer value = (Integer) animation.getAnimatedValue();
	            rlFooter.setPadding(0, value, 0, 0);
	        }
	    });
	    va.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				setFooterPadding(toPadding);
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    va.start();
	}
	
	private void changeState(int state)
	{
		int lastState = currentFooterState;
		currentFooterState = state;
		if (state == State.ORIGIN)
		{
			resetFooter(0);
			pgbCircle.setVisibility(View.INVISIBLE);
		}
		else if (state == State.LOADING)
		{
			pgbCircle.setVisibility(View.VISIBLE);
			tvHint.setText("正在刷新");
			resetFooter(0);
			onLoadMoreListener.onLoadMore();
		}
		else if (state == State.END)
		{
			pgbCircle.setVisibility(View.INVISIBLE);
			pgbCircle.startAnimation(hideAnimation);
			tvHint.setText("已全部加载");
		}
		else if (state == State.FAIL)
		{
			pgbCircle.startAnimation(hideAnimation);
			tvHint.setText("加载失败，请检查网络");
			resetFooter(-footerViewHeight, 300);
		}
		
			
	}
	
	private class OnMoreLvScrollListener implements OnScrollListener
	{
		private Context context;
		public OnMoreLvScrollListener(Context context) {
			this.context = context;
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (visibleItemCount == totalItemCount)
				return;
			if (currentFooterState != State.END && currentFooterState != State.LOADING && firstVisibleItem + visibleItemCount == totalItemCount)
				changeState(State.LOADING);
		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub
		}
	
	}
	
	private void measureHeaderView(View view) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
		} else {
			// Measure specification mode: The parent has not imposed any
			// constraint on the child. It can be whatever size it wants.
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childMeasureWidth, childMeasureHeight);
	}
}
