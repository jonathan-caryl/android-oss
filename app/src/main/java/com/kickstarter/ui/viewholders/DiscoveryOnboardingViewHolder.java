package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.kickstarter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class DiscoveryOnboardingViewHolder extends KSViewHolder {
  protected @BindView(R.id.login_tout_button) Button lgoinToutButton;

  private final Delegate delegate;
  public interface Delegate {
    void discoveryOnboardingViewHolderLoginToutClick(DiscoveryOnboardingViewHolder viewHolder);
  }

  public DiscoveryOnboardingViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;

    ButterKnife.bind(this, view);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {}

  public void onBind() {}

  @OnClick(R.id.login_tout_button)
  protected void loginToutClick() {
    delegate.discoveryOnboardingViewHolderLoginToutClick(this);
  }
}
