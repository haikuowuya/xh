package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.DoctorEvaluationActivity;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserDoctorItem;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.ToastUtils;

import java.util.List;

/**
 * 我的医生列表适配器
 */
public class UserDoctorListAdapter extends BaseAdapter<UserDoctorItem>
{

    public UserDoctorListAdapter(BaseActivity activity, List<UserDoctorItem> data)
    {
        super(activity, R.layout.list_user_doctor_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserDoctorItem item)
    {
        String lastServiceTime = DateFormatUtils.format(item.lastServiceTime, true, false);
        setTextViewText(convertView, R.id.tv_time, "上次就诊时间：" +  lastServiceTime);
        setTextViewText(convertView, R.id.tv_doctor_name, item.doctName);
        setTextViewText(convertView, R.id.tv_dept_name, item.hospital + "/" + item.department);
        setTextViewText(convertView, R.id.tv_hospital_name, item.hospital);

        OnClickListenerImpl onClickListener = new OnClickListenerImpl(item);
        /**
         * 病情反馈 点击
         */
        setViewOnClick(convertView, R.id.tv_action_feedback, onClickListener);
        /**
         * 预约挂号 点击
         */
        setViewOnClick(convertView, R.id.tv_action_subscribe, onClickListener);

        /**
         *   医生评价 点击
         */

        setViewOnClick(convertView, R.id.tv_action_evaluation, onClickListener);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        private UserDoctorItem userDoctorItem;

        public OnClickListenerImpl(UserDoctorItem userDoctorItem)
        {
            this.userDoctorItem = userDoctorItem;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_action_evaluation:
                    evaluation();
                    break;
                case R.id.tv_action_feedback:
                    feedback();
                    break;
                case R.id.tv_action_subscribe:
                    subscribe();
                    break;
            }

        }

        /**
         * 预约挂号
         */
        private void subscribe()
        {
            ToastUtils.showCrouton(getActivity(), "预约挂号");
        }

        /**
         * 病情反馈
         */

        private void feedback()
        {

            getActivity().showCroutonToast("病情反馈");
        }

        /**
         * 医生评价
         */
        private void evaluation()
        {

//            getActivity().showCroutonToast("医生评价");
            DoctorEvaluationActivity.actionDoctorEvaluation(getActivity(),userDoctorItem.doctId);


        }
    }
}
