package com.kickstarter.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.libs.utils.ViewUtils;
import com.kickstarter.ui.IntentKey;
import com.kickstarter.viewmodels.ResetPasswordViewModel;
import com.kickstarter.ui.toolbars.LoginToolbar;

import butterknife.BindView;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.TransitionUtils.slideInFromLeft;

@RequiresActivityViewModel(ResetPasswordViewModel.class)
public final class ResetPasswordActivity extends BaseActivity<ResetPasswordViewModel> {
  @BindView(R.id.email) EditText email;
  @BindView(R.id.reset_password_button) Button resetPasswordButton;
  @BindView(R.id.login_toolbar) LoginToolbar loginToolbar;

  @BindString(R.string.forgot_password_title) String forgotPasswordString;
  @BindString(R.string.forgot_password_error) String errorMessageString;
  @BindString(R.string.general_error_oops) String errorTitleString;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.reset_password_layout);
    ButterKnife.bind(this);
    loginToolbar.setTitle(forgotPasswordString);

    viewModel.outputs.resetSuccess()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> onResetSuccess());

    viewModel.outputs.isFormSubmitting()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::setFormDisabled);

    viewModel.outputs.isFormValid()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::setFormEnabled);

    viewModel.errors.resetError()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(__ -> ViewUtils.showDialog(this, errorTitleString, errorMessageString));
  }

  @OnTextChanged(R.id.email)
  void onEmailTextChanged(final @NonNull CharSequence email) {
    viewModel.inputs.email(email.toString());
  }

  @OnClick(R.id.reset_password_button)
  public void resetButtonOnClick() {
    viewModel.inputs.resetPasswordClick();
  }

  @Override
  protected @Nullable Pair<Integer, Integer> exitTransition() {
    return slideInFromLeft();
  }

  private void onResetSuccess() {
    setFormEnabled(false);
    final Intent intent = new Intent(this, LoginActivity.class)
      .putExtra(IntentKey.EMAIL, email.getText().toString());
    startActivityWithTransition(intent, R.anim.fade_in_slide_in_left, R.anim.slide_out_right);
  }

  private void setFormEnabled(final boolean isEnabled) {
    resetPasswordButton.setEnabled(isEnabled);
  }

  private void setFormDisabled(final boolean isDisabled) {
    setFormEnabled(!isDisabled);
  }
}
