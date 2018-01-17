package cn.xuhao.android.lib.presenter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import cn.xuhao.android.lib.http.PaxOk;
import cn.xuhao.android.lib.observer.lifecycle.ILifecycleObserver;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xuhao on 2017/4/19.
 */

public abstract class AbsViewPresenter<T extends IBaseView> implements ILifecycleObserver {

    protected T mView;

    private CompositeSubscription compositeSubscription;

    public AbsViewPresenter(@NonNull T view) {
        mView = view;
        mView.addLifecycleObserver(this);
    }

    @Override
    @CallSuper
    public void onCreate() {

    }

    @Override
    @CallSuper
    public void onDestroy() {
        unSubscribe();
        PaxOk.getInstance().cancelTag(this);
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

    public Context getContext() {
        if (isViewAttached()) {
            return mView.getContext();
        }
        return null;
    }

    public final String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    public final String getString(@StringRes int resId, Object... args) {
        return getContext().getString(resId, args);
    }

    public void addSubscribe(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    public void unSubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }
}
