package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.libs.transformations.CircleTransformation;
import com.kickstarter.models.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public class ProjectSocialViewHolder extends KSViewHolder {
  private User user;
  protected @BindView(R.id.friend_image) ImageView friendImageView;
  protected @BindView(R.id.friend_name) TextView friendNameTextView;

  public ProjectSocialViewHolder(final @NonNull View view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    user = requireNonNull((User) data, User.class);
  }

  @Override
  public void onBind() {
    Picasso.with(context()).load(user
      .avatar()
      .small())
    .transform(new CircleTransformation())
    .into(friendImageView);

    friendNameTextView.setText(user.name());
  }
}
