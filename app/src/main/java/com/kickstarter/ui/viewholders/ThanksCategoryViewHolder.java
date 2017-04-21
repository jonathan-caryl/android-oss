package com.kickstarter.ui.viewholders;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.kickstarter.KSApplication;
import com.kickstarter.R;
import com.kickstarter.libs.KSString;
import com.kickstarter.libs.utils.NumberUtils;
import com.kickstarter.models.Category;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindString;
import butterknife.ButterKnife;

import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public final class ThanksCategoryViewHolder extends KSViewHolder {
  private Category category;
  private final Context context;
  private final Delegate delegate;

  protected @BindView(R.id.thanks_category_view) CardView cardView;
  protected @BindView(R.id.explore_text_view) TextView exploreTextView;
  protected @BindView(R.id.live_projects_text_view) TextView liveProjectsTextView;

  protected @BindString(R.string.category_promo_explore_category) String exploreCategoryString;
  protected @BindString(R.string.category_promo_project_count_live_projects) String countLiveProjectsString;

  protected @Inject KSString ksString;

  public interface Delegate {
    void categoryClick(ThanksCategoryViewHolder viewHolder, Category category);
  }

  public ThanksCategoryViewHolder(final @NonNull View view, final @NonNull Delegate delegate) {
    super(view);
    this.delegate = delegate;
    this.context = view.getContext();

    ((KSApplication) context.getApplicationContext()).component().inject(this);
    ButterKnife.bind(this, view);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    category = requireNonNull((Category) data, Category.class);
  }

  public void onBind() {
    cardView.setCardBackgroundColor(category.colorWithAlpha());
    final @ColorInt int categoryTextColor = category.overlayTextColor(context);
    exploreTextView.setTextColor(categoryTextColor);
    exploreTextView.setText(ksString.format(exploreCategoryString, "category_name", category.name()));

    final Integer projectsCount = category.projectsCount();
    if (projectsCount != null) {
      liveProjectsTextView.setVisibility(View.VISIBLE);
      liveProjectsTextView.setText(ksString.format(
        countLiveProjectsString,
        "project_count",
        NumberUtils.format(projectsCount)
      ));
    } else {
      liveProjectsTextView.setVisibility(View.GONE);
    }

    liveProjectsTextView.setTextColor(categoryTextColor);
  }

  @Override
  public void onClick(final @NonNull View view) {
    delegate.categoryClick(this, category);
  }
}
