package com.xinheng.base;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinheng.util.UILUtils;

import java.util.List;

/***
 * baseAdapter的封装，简化操作
 *
 * @param <T>
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter
{
    protected List<T> mData;
    private BaseActivity mActivity;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    protected int mPosition;

    public BaseAdapter(BaseActivity activity, int layoutId, List<T> data)
    {
        super();
        this.mData = data;
        this.mActivity = activity;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(mActivity);
        UILUtils.config(activity);
    }

    public BaseActivity getActivity()
    {
        return mActivity;
    }

    @Override
    public int getCount()
    {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null == mData ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(mLayoutId, null);
        }
        mPosition = position;
        bindDataToView(convertView, mData.get(position));
        return convertView;
    }

    public int getPosition()
    {
        return mPosition;
    }

    public boolean isLastPosition()
    {
        return mPosition == mData.size() - 1;
    }

    public abstract void bindDataToView(View convertView, T t);

    public void setViewVisibility(View convertView, int viewId, int visibility)
    {
        View view = getView(convertView, viewId);
        view.setVisibility(visibility);
    }

    public void setTextViewText(View convertView, int viewId, CharSequence text)
    {
        TextView textView = getView(convertView, viewId);
        textView.setText(text);
    }

    public void setTextViewTextColor(View convertView, int viewId, int textColor)
    {
        TextView textView = getView(convertView, viewId);
        textView.setTextColor(textColor);
    }

    public void setViewOnClick(View convertView, int viewId, View.OnClickListener listener)
    {
        getView(convertView, viewId).setOnClickListener(listener);
    }

    public void setConvertViewOnClick(View convertView, View.OnClickListener listener)
    {
        convertView.setOnClickListener(listener);
    }

    public void setCurrentProgress(View convertView, int viewId, int progress)
    {
        ProgressBar progressBar = getView(convertView, viewId);
        progressBar.setProgress(progress);

    }

    public void setImageViewDrawable(View convertView, int viewId, Drawable drawable)
    {
        ImageView imageView = getView(convertView, viewId);
        imageView.setImageDrawable(drawable);
    }

    public void setImageViewDrawableFromUrl(View convertView, int viewId, final String imageUrl, int defaultResId)
    {
        final ImageView imageView = getView(convertView, viewId);
        imageView.setImageResource(defaultResId);
        imageView.setTag(imageUrl);

    }

    public void setImageViewResId(View convertView, int viewId, int resId)
    {
        ImageView imageView = getView(convertView, viewId);
        imageView.setImageResource(resId);
    }

    public <W extends View> W getView(View convertView, int viewId)
    {
        return ViewHolder.getView(convertView, viewId);
    }

}
