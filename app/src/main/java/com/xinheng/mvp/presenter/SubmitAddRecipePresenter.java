package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.user.PostAddRecipeItem;

/**
 * 提交添加处方的接口
 */
public interface SubmitAddRecipePresenter
{
    public void doAddRecipe(PostAddRecipeItem item);
}
