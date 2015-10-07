package com.xinheng.mvp.presenter;

/**
 * 症状自测结果获取接口
 */
public interface SymptomCheckPresenter
{
    /***
     *    获取症状自测问题
     * @param symptomId
     */
    public  void doGetSymptomCheckQuestion(String symptomId);

    /**
     * 根据answer的值来获取症状自测的结果还是下一个问题
     * @param flowId
     * @param answer  1 yes, 0  no
     */
    public  void doGetSymptomCheckResultOrNext(String flowId, String answer);
}
