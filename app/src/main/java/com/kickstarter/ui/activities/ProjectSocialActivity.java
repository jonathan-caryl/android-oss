package com.kickstarter.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.qualifiers.RequiresActivityViewModel;
import com.kickstarter.ui.adapters.ProjectSocialAdapter;
import com.kickstarter.ui.viewholders.ProjectContextViewHolder;
import com.kickstarter.viewmodels.ProjectSocialViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

import static com.kickstarter.libs.utils.TransitionUtils.slideInFromLeft;

@RequiresActivityViewModel(ProjectSocialViewModel.class)
public final class ProjectSocialActivity extends BaseActivity<ProjectSocialViewModel> implements ProjectSocialAdapter.Delegate {
  protected @BindView(R.id.project_social_recycler_view) RecyclerView recyclerView;

  @Override
  protected void onCreate(final @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.project_social_layout);
    ButterKnife.bind(this);

    final ProjectSocialAdapter adapter = new ProjectSocialAdapter(this);

    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    viewModel.outputs.project()
      .compose(bindToLifecycle())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(adapter::takeProject);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    recyclerView.setAdapter(null);
  }

  @Override
  public void projectContextClicked(final @NonNull ProjectContextViewHolder viewHolder) {
    back();
  }

  @Override
  protected @Nullable Pair<Integer, Integer> exitTransition() {
    return slideInFromLeft();
  }
}
