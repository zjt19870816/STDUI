package com.st.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 
 * Copyright (c) 2012 All rights reserved ���ƣ�AbCircleProgressBar.java
 * ���������ε�ProgressBar
 * 
 * @author zhaoqp
 * @date��2013-9-22 ����2:44:17
 * @version v1.0
 */
public class CircleProgressBar extends View {

	private int progress = 0;
	private int max = 100;

	// ���ƹ켣
	private Paint pathPaint = null;

	// �������
	private Paint fillArcPaint = null;

	private RectF oval;

	// �ݶȽ���������ɫ
	private int[] arcColors = new int[] { 0xFF48cbdc, 0xFF4c9fda, 0xFFeac83d,
			0xFFc7427e, 0xFF48cbdc, 0xFF48cbdc };
	// ��ɫ�켣
	private int pathColor = 0xFFF0EEDF;
	private int pathBorderColor = 0xFFD2D1C4;

	// ����·�����
	private int pathWidth = 35;

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	// Ĭ��Բ�İ뾶
	private int radius = 120;
	
	private float mSweepAnglePer;

	// ָ���˹�Դ�ķ���ͻ�����ǿ������Ӹ���Ч��
	private EmbossMaskFilter emboss = null;
	// ���ù�Դ�ķ���
	float[] direction = new float[] { 1, 1, 1 };
	// ���û���������
	float light = 0.4f;
	// ѡ��ҪӦ�õķ���ȼ�
	float specular = 6;
	// �� maskӦ��һ�������ģ��
	float blur = 3.5f;
	private BarAnimation anim;
	// ָ����һ��ģ������ʽ�Ͱ뾶������ Paint �ı�Ե
	private BlurMaskFilter mBlur = null;
	// ������
	private OnProgressListener mAbOnProgressListener = null;
	// view�ػ�ı��
	private boolean reset = false;
	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		pathPaint = new Paint();
		// �����Ƿ񿹾��
		pathPaint.setAntiAlias(true);
		// �����������
		pathPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// �����пյ���ʽ
		pathPaint.setStyle(Paint.Style.STROKE);
		pathPaint.setDither(true);
		pathPaint.setStrokeJoin(Paint.Join.ROUND);

		fillArcPaint = new Paint();
		// �����Ƿ񿹾��
		fillArcPaint.setAntiAlias(true);
		// �����������
		fillArcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// �����пյ���ʽ
		fillArcPaint.setStyle(Paint.Style.STROKE);
		fillArcPaint.setDither(true);
		fillArcPaint.setStrokeJoin(Paint.Join.ROUND);

		oval = new RectF();
		emboss = new EmbossMaskFilter(direction, light, specular, blur);
		mBlur = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
		anim = new BarAnimation();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (reset) {
			canvas.drawColor(Color.TRANSPARENT);
			reset = false;
		}
		this.width = getMeasuredWidth();
		this.height = getMeasuredHeight();
		this.radius = getMeasuredWidth() / 2 - pathWidth;

		// ���û�����ɫ
		pathPaint.setColor(pathColor);
		// ���û��ʿ��
		pathPaint.setStrokeWidth(pathWidth);
		// ��Ӹ���Ч��
		pathPaint.setMaskFilter(emboss);

		// �����ĵĵط������뾶Ϊr��Բ
		canvas.drawCircle(this.width / 2, this.height / 2, radius, pathPaint);
		// ����
		pathPaint.setStrokeWidth(0.5f);
		pathPaint.setColor(pathBorderColor);
		canvas.drawCircle(this.width / 2, this.height / 2, radius + pathWidth
				/ 2 + 0.5f, pathPaint);
		canvas.drawCircle(this.width / 2, this.height / 2, radius - pathWidth
				/ 2 - 0.5f, pathPaint);
		// ������ɫ���
		SweepGradient sweepGradient = new SweepGradient(this.width / 2,
				this.height / 2, arcColors, null);
		fillArcPaint.setShader(sweepGradient);
		// ���û���Ϊ��ɫ

		// ģ��Ч��
		fillArcPaint.setMaskFilter(mBlur);

		// �����ߵ�����,����Բ��
		fillArcPaint.setStrokeCap(Paint.Cap.ROUND);

		// fillArcPaint.setColor(Color.BLUE);

		fillArcPaint.setStrokeWidth(pathWidth);
		// �������������Ͻ����꣬���½�����
		oval.set(this.width / 2 - radius, this.height / 2 - radius, this.width
				/ 2 + radius, this.height / 2 + radius);
		// ��Բ�����ڶ�������Ϊ����ʼ�Ƕȣ�������Ϊ��ĽǶȣ����ĸ�Ϊtrue��ʱ����ʵ�ģ�false��ʱ��Ϊ����
		canvas.drawArc(oval, -90, mSweepAnglePer, false,
				fillArcPaint);
	}

	/**
	 * 
	 * ��������ȡԲ�İ뾶
	 * 
	 * @return
	 * @throws
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * 
	 * ����������Բ�İ뾶
	 * 
	 * @param radius
	 * @throws
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress, int time) {
		this.progress = progress;
		anim.setDuration(time);
		this.startAnimation(anim);
	}

	/**
	 * ����������
	 * 
	 * @author Administrator
	 * 
	 */
	public class BarAnimation extends Animation {
		public BarAnimation() {

		}

		/**
		 * ÿ��ϵͳ�����������ʱ�� �ı�mSweepAnglePer��mPercent��stepnumbernow��ֵ��
		 * Ȼ�����postInvalidate()��ͣ�Ļ���view��
		 */
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				mSweepAnglePer = interpolatedTime * progress * 360
						/ max;
			} else {
				mSweepAnglePer = progress * 360 / max;
			}
			postInvalidate();
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = View.MeasureSpec.getSize(heightMeasureSpec);
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(width, height);
	}

	public OnProgressListener getOnProgressListener() {
		return mAbOnProgressListener;
	}

	public void setOnProgressListener(OnProgressListener mOnProgressListener) {
		this.mAbOnProgressListener = mOnProgressListener;
	}

	/**
	 * 
	 * ���������ý���
	 * 
	 * @throws
	 */
	public void reset() {
		reset = true;
		this.progress = 0;
		this.invalidate();
	}

}
