package com.hotelbooking.ui;

import com.hotelbooking.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
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

public class PullToRefreshListView extends ListView{

	// Views
	private Context context;
	private View rlHeader;
	private TextView tvHint;
	private ImageView imgArrow;
	private ProgressBar pgbCircle;
	private int headerViewHeight;
	private OnRefreshListener onRefreshListener = null;
	
	// Constructors.
	public PullToRefreshListView(Context context, AttributeSet attrs,
	         int defStyle) {
	     super(context, attrs, defStyle);
	     this.context = context;
	     init(context);
	}
	public PullToRefreshListView(Context context, AttributeSet attrs) {
	     super(context, attrs);
	     this.context = context;
	     init(context);
	}
	
	public PullToRefreshListView(Context context) {
		super(context);
		this.context = context;
		init(context);
	}
	
	// Animations
	private Animation rotateDownAnimation;
	private Animation rotateUpAnimation;
	private Animation hideAnimation;
	
	
	// Init views and animations.
	private void init(Context context)
	{
		super.setOnScrollListener(new OnMoreLvScrollListener(context));
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rlHeader = inflater.inflate(R.layout.list_header, null);
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
		
		hideAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		hideAnimation.setDuration(150);
		hideAnimation.setFillAfter(true);
	}
	
	public interface OnRefreshListener
	{
		public void onLoadMore();
	}
	
	public void setOnLoadMoreListener(OnRefreshListener onRefreshListener)
	{
		this.onRefreshListener = onRefreshListener;
	}
	
	public void onRefreshComplete()
	{
		changeState(State.ORIGIN);
	}

	// Motions related.
	private boolean isArrowRotating = false;
	private boolean isArrowStateUp = false;
	private boolean isListOnTop = true;
	private int currentState = State.ORIGIN;
	private int lastDownPosY;
	private int currentPosY;
	private int pullDisY;
	private int currentPadding;
	private boolean islistMotionEnable = true;
	private boolean isAnimating = false;
	private static final int DIS_RATIO = 2;
	
	private class State
	{
		public static final int ORIGIN = 0;
		public static final int REFRESHING = 1; 
		public static final int PULL_TO_REFRESH = 2;
		public static final int RELEASE_TO_REFRESH = 3;
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!isListOnTop || currentState == State.REFRESHING || isAnimating)
			return super.onTouchEvent(ev);
		int y = (int) ev.getRawY();
		int action = ev.getAction();
		switch (action){
		case MotionEvent.ACTION_DOWN:
			lastDownPosY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (lastDownPosY < 0)
				break;
			currentPosY = y;
			pullDisY = (currentPosY - lastDownPosY) / DIS_RATIO;
			if (currentState == State.ORIGIN && pullDisY > 0)
				changeState(State.PULL_TO_REFRESH);
			else if (currentState == State.PULL_TO_REFRESH && pullDisY > headerViewHeight)
				changeState(State.RELEASE_TO_REFRESH);
			else if (currentState == State.RELEASE_TO_REFRESH && pullDisY < 0)
				changeState(State.ORIGIN);
			else if (currentState == State.RELEASE_TO_REFRESH && pullDisY < headerViewHeight)
				changeState(State.PULL_TO_REFRESH);
			setHeaderPadding(- headerViewHeight + pullDisY);
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
		if (islistMotionEnable)
			return super.onTouchEvent(ev);
		else
			return true;
	}
	
	private void setHeaderPadding(int padding)
	{
		this.currentPadding = padding;
		rlHeader.setPadding(0, padding, 0, 0);
	}
	
	private void resetHeader(final int toPadding)
	{
		resetHeader(toPadding, 0);
	}
	
	private void resetHeader(final int toPadding, int delay)
	{
		ValueAnimator va = ValueAnimator.ofInt(currentPadding, toPadding);
	    va.setDuration(150);
	    va.setStartDelay(delay);
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
			islistMotionEnable = true;
			if (lastState == State.REFRESHING)
			{
				tvHint.setText("刷新完成");
				pgbCircle.startAnimation(hideAnimation);
				resetHeader(-headerViewHeight, 300);
			}
			else
			{
				resetHeader(-headerViewHeight);
			}
		}
		else if (state == State.REFRESHING)
		{
			pgbCircle.setVisibility(View.VISIBLE);
			rotateUpAnimation.setFillAfter(false);
			imgArrow.setVisibility(View.INVISIBLE);
			imgArrow.clearAnimation();
			isArrowRotating = false;
			lastDownPosY = -1;
			islistMotionEnable = true;
			tvHint.setText("正在刷新");
			resetHeader(0);
			onRefreshListener.onLoadMore();
		}
		else if (state == State.PULL_TO_REFRESH)
		{
			imgArrow.setVisibility(View.VISIBLE);
			islistMotionEnable = false;
			tvHint.setText("下拉可以刷新");
			isArrowStateUp = false;
			if (!isArrowRotating && lastState == State.RELEASE_TO_REFRESH)
				imgArrow.startAnimation(rotateDownAnimation);
		}
		else if (state == State.RELEASE_TO_REFRESH)
		{
			islistMotionEnable = false;
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
				isListOnTop = true;
			else 
				isListOnTop = false;
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
