package com.kickstarter.ui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.widget.Button;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.Koala;
import com.kickstarter.libs.preferences.BooleanPreferenceType;
import com.kickstarter.libs.qualifiers.AppRatingPreference;
import com.kickstarter.libs.utils.ViewUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppRatingDialog extends AppCompatDialog {
  protected @Inject @AppRatingPreference BooleanPreferenceType hasSeenAppRatingPreference;
  protected @Inject Koala koala;

  protected @BindView(R.id.no_thanks_button) Button noThanksButton;
  protected @BindView(R.id.remind_button) Button remindButton;
  protected @BindView(R.id.rate_button) Button rateButton;

  public AppRatingDialog(final @NonNull Context context) {
    super(context);
  }

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    setContentView(R.layout.app_rating_prompt);
    ButterKnife.bind(this);

    ((KSApplication) getContext().getApplicationContext()).component().inject(this);
  }

  @OnClick(R.id.rate_button)
  protected void rateButtonClick() {
    koala.trackAppRatingNow();
    hasSeenAppRatingPreference.set(true);
    dismiss();
    ViewUtils.openStoreRating(getContext(), getContext().getPackageName());
  }

  @OnClick(R.id.remind_button)
  protected void remindButtonClick() {
    koala.trackAppRatingRemindLater();
    dismiss();
  }

  @OnClick(R.id.no_thanks_button)
  protected void noThanksButtonClick() {
    koala.trackAppRatingNoThanks();
    hasSeenAppRatingPreference.set(true);
    dismiss();
  }
}
