package com.hotelbooking.ui;

import com.hotelbooking.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class AutoListView extends ListView{

	private Context context;
	private View rlHeader;
	private TextView tvHint;
	private ImageView imgArrow;
	private ProgressBar pgbCircle;
	private int headerViewHeight;
	private OnLoadMoreListener onLoadMoreListener = null;
	
	public AutoListView(Context context, AttributeSet attrs,
	         int defStyle) {
	     super(context, attrs, defStyle);
	     this.context = context;
	     init(context);
	}
	public AutoListView(Context context, AttributeSet attrs) {
	     super(context, attrs);
	     this.context = context;
	     init(context);
	}

	
	public AutoListView(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	private Animation rotateDownAnimation;
	private Animation rotateUpAnimation;
	private boolean isArrowRotating = false;
	private boolean isArrowStateUp = false;
	
	private void init(Context context)
	{
		super.setOnScrollListener(new OnMoreLvScrollListener(context));
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rlHeader = inflater.inflate(R.layout.footer_hotel_list, null);
		tvHint = (TextView) rlHeader.findViewById(R.id.text_header_hint);
		imgArrow = (ImageView) rlHeader.findViewById(R.id.image_header_arrow);
		pgbCircle = (ProgressBar) rlHeader.findViewById(R.id.progress_bar_header);

		measureHeaderView(rlHeader);
		headerViewHeight = rlHeader.getMeasuredHeight();
		rlHeader.invalidate();
		
		setHeaderPadding(-headerViewHeight);
		addHeaderView(rlHeader);
		
		rotateDownAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateDownAnimation.setDuration(150);
		rotateDownAnimation.setInterpolator(new LinearInterpolator());
		rotateDownAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				isArrowRotating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				isArrowRotating = false;
				if (isArrowStateUp)
				{
					rotateUpAnimation.setFillAfter(true);
					imgArrow.startAnimation(rotateUpAnimation);
				}
			}
		});
		
		rotateUpAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateUpAnimation.setDuration(150);
		rotateUpAnimation.setFillAfter(true);
		rotateUpAnimation.setInterpolator(new LinearInterpolator());
		rotateUpAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				isArrowRotating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				isArrowRotating = false;
				if (!isArrowStateUp)
					imgArrow.startAnimation(rotateDownAnimation);
			}
		});
	}
	
	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
	{
		this.onLoadMoreListener = onLoadMoreListener;
	}
	
	public void onRefreshComplete()
	{
		changeState(State.ORIGIN);
	}
	 

	
	public interface OnLoadMoreListener
	{
		public void onLoadMore();
	}
	
	
	private boolean isOnTop = true;
	private int currentState = State.ORIGIN;
	private int downPosY;
	private int currentPosY;
	private int pullDis;
	private int padding;
	private boolean listEnable = true;
	private boolean isAnimating = false;
	private static final int RATIO = 2;
	
	
	private class State
	{
		public static final int ORIGIN = 0;
		public static final int REFRESHING = 1; 
		public static final int PULL_TO_REFRESH = 2;
		public static final int RELEASE_TO_REFRESH = 3;
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!isOnTop || currentState == State.REFRESHING || isAnimating)
			return super.onTouchEvent(ev);
		int y = (int) ev.getRawY();
		int action = ev.getAction();
		switch (action){
		case MotionEvent.ACTION_DOWN:
			downPosY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (downPosY < 0)
				break;
			currentPosY = y;
			pullDis = (currentPosY - downPosY) / RATIO;
			if (currentState == State.ORIGIN && pullDis > 0)
				changeState(State.PULL_TO_REFRESH);
			else if (currentState == State.PULL_TO_REFRESH && pullDis > headerViewHeight)
				changeState(State.RELEASE_TO_REFRESH);
			else if (currentState == State.RELEASE_TO_REFRESH && pullDis < 0)
				changeState(State.ORIGIN);
			else if (currentState == State.RELEASE_TO_REFRESH && pullDis < headerViewHeight)
				changeState(State.PULL_TO_REFRESH);
			setHeaderPadding(- headerViewHeight + pullDis);
			break;
		case MotionEvent.ACTION_UP:
			if (currentState == State.PULL_TO_REFRESH)
				changeState(State.ORIGIN);
			else if (currentState == State.RELEASE_TO_REFRESH)
				changeState(State.REFRESHING);
			else
				break;
		default:
				break;
		}
		if (listEnable)
			return super.onTouchEvent(ev);
		else
			return true;
	}
	
	private void setHeaderPadding(int padding)
	{
		this.padding = padding;
		rlHeader.setPadding(0, padding, 0, 0);
	}
	
	
	
	private void resetHeader(final int toPadding)
	{
		ValueAnimator va = ValueAnimator.ofInt(padding, toPadding);
	    va.setDuration(150);
	    va.setInterpolator(new AccelerateDecelerateInterpolator());
	    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	        public void onAnimationUpdate(ValueAnimator animation) {
	            Integer value = (Integer) animation.getAnimatedValue();
	            rlHeader.setPadding(0, value, 0, 0);
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
				isAnimating = false;
				setHeaderPadding(toPadding);
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    va.start();
	    isAnimating = true;
	}
	
	private void changeState(int state)
	{
		int lastState = currentState;
		currentState = state;
		if (state == State.ORIGIN)
		{
			pgbCircle.setVisibility(View.INVISIBLE);
			imgArrow.setVisibility(View.VISIBLE);
			listEnable = true;
			tvHint.setText("下拉可以刷新");
			resetHeader(-headerViewHeight);
		}
		else if (state == State.REFRESHING)
		{
			pgbCircle.setVisibility(View.VISIBLE);
			rotateUpAnimation.setFillAfter(false);
			imgArrow.setVisibility(View.INVISIBLE);
			imgArrow.clearAnimation();
			isArrowRotating = false;
			downPosY = -1;
			listEnable = true;
			tvHint.setText("刷新中");
			resetHeader(0);
			onLoadMoreListener.onLoadMore();
		}
		else if (state == State.PULL_TO_REFRESH)
		{
			listEnable = false;
			tvHint.setText("下拉可以刷新");
			isArrowStateUp = false;
			if (!isArrowRotating && lastState == State.RELEASE_TO_REFRESH)
				imgArrow.startAnimation(rotateDownAnimation);
		}
		else if (state == State.RELEASE_TO_REFRESH)
		{
			listEnable = false;
			tvHint.setText("释放立即刷新");
			isArrowStateUp = true;
			if (!isArrowRotating && lastState == State.PULL_TO_REFRESH)
			{
				rotateUpAnimation.setFillAfter(true);
				imgArrow.startAnimation(rotateUpAnimation);
			}
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
			if (firstVisibleItem == 0)
				isOnTop = true;
			else 
				isOnTop = false;
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
