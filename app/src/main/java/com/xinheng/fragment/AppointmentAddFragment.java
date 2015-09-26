package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.UserPatientListActivity;
import com.xinheng.adapter.subscribe.ImageGridAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.appointment.PatientRecordItem;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.StorageUtils;
import com.xinheng.view.CustomGridView;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：预约加号Fragment界面
 */
public class AppointmentAddFragment extends BaseFragment implements DataView
{
    private static final String ARG_DOCTOR_DETAIL_TIEM = "doctor_detail_item";
    public static final String ARG_POSITION = "position";
    public static AppointmentAddFragment newInstance(DoctorDetailItem doctorDetailItem, int postion)
    {
        AppointmentAddFragment fragment = new AppointmentAddFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DOCTOR_DETAIL_TIEM, doctorDetailItem);
        bundle.putInt(ARG_POSITION, postion);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Button mBtnSubmit;
    private CustomGridView mCustomGridView;
    private LinkedList<String> mImageFilePaths;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_appointment_add, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        mImageFilePaths = new LinkedList<>();
        mImageFilePaths.add(null);
        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
        mCustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String str = parent.getAdapter().getItem(position) == null ? null : parent.getAdapter().getItem(position).toString();
                if (TextUtils.isEmpty(str))
                {
                    PhotoUtils.showSelectDialog(mActivity);
                }
            }
        });

    }

    @Override
    protected void doGetData()
    {

    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
        }
    }

    /***
     * 将选中的就诊人的病历显示出来
     *
     * @param items
     */
    private void fillPatientRecord(List<PatientRecordItem> items)
    {
        if (null != items && !items.isEmpty())
        {
            for (PatientRecordItem patientRecordItem : items)
            {
                View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_patient_record_item, null);
                TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                TextView tvDepatName = (TextView) view.findViewById(R.id.tv_dept_name);
                ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
                tvDate.setText(DateFormatUtils.format(patientRecordItem.createDate, true, false));
                tvDepatName.setText(patientRecordItem.departName);
                if ("1".equals(patientRecordItem.isOpen))
                {
                    ivImage.setImageResource(R.mipmap.ic_subscribe_patient_record_auth);
                }
            }

        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_select_patient://选择常用患者
                    UserPatientListActivity.actionPatient(mActivity, true);
                    break;
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
        }
    }

    private void submit()
    {
        if (mImageFilePaths.size() > 1)
        {
            List<File> files = new LinkedList<>();
            for (int i = 0; i < mImageFilePaths.size() - 1; i++)//-1的目的去除默认的+
            {
                File file = new File(mImageFilePaths.get(i));
                if (file != null && file.exists())
                {
                    files.add(file);
                }
            }
            //   postSubmitSubscribeItem.files = files;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PhotoUtils.REQUEST_FROM_PHOTO)
            {
                if (null != data && data.getData() != null)
                {
                    String imageFilePath = StorageUtils.getFilePathFromUri(mActivity, data.getData());
                    if (null != imageFilePath)
                    {
                        mImageFilePaths.addFirst(imageFilePath);
                        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));

                    }
                }
            }
        }
    }
}
