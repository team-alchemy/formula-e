package com.fiaformulae.wayfinder.drivers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.fiaformulae.wayfinder.R;
import com.fiaformulae.wayfinder.models.Driver;
import com.fiaformulae.wayfinder.models.Team;
import java.util.List;

import static com.fiaformulae.wayfinder.AppConstants.DEFAULT_INT_VALUE;
import static com.fiaformulae.wayfinder.AppConstants.TEAM_ID;

public class DriversActivity extends AppCompatActivity implements DriversContract.View {
  @BindView(R.id.team_name) TextView teamNameView;
  @BindView(R.id.team_flag) ImageView teamFlagImage;
  @BindView(R.id.team_description) TextView teamDescriptionView;
  @BindView(R.id.win_count) TextView winCountView;
  @BindView(R.id.podium_count) TextView podiumCountView;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.back) ImageView back;
  private int teamId;
  private Team team;
  private List<Driver> drivers;
  private DriversContract.Presenter presenter;
  private PagerAdapter pagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drivers);
    ButterKnife.bind(this);

    presenter = new DriversPresenter(this);
    initializeViews();
    pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(pagerAdapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.back) void onBackClick() {
    onBackPressed();
  }

  private void initializeViews() {
    teamId = getIntent().getIntExtra(TEAM_ID, DEFAULT_INT_VALUE);
    team = presenter.getTeam(teamId);
    if (team == null) return;
    drivers = team.drivers();
    teamNameView.setText(team.getName());
    if (team.getFlagThumbnail() != null) {
      Glide.with(this).load(team.getFlagThumbnail()).into(teamFlagImage);
    }
    teamDescriptionView.setText(team.getDescription());
    if (team.getDetails() != null) {

    }
  }

  private class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return DriverFragment.newInstance(drivers.get(position));
    }

    @Override public int getCount() {
      return drivers.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return drivers.get(position).getName().toUpperCase();
    }
  }
}
