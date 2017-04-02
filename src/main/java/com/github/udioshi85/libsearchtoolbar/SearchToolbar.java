package com.github.udioshi85.libsearchtoolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created on 4/2/2017.
 */

public class SearchToolbar extends FrameLayout {

    static final String TAG = "SearchToolbar";

    private Toolbar toolbar;
    private Toolbar searchToolbar;
    private MenuItem searchMenuItem;

    public SearchToolbar(Context context) {
        this(context, null);
    }

    public SearchToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateAndBindViews();
    }

    private void inflateAndBindViews() {
        View.inflate(getContext(), R.layout.view_search_toolbar, SearchToolbar.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchToolbar = (Toolbar) findViewById(R.id.searchToolbar);

        searchToolbar.inflateMenu(R.menu.menu_search);
        searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    circleReveal(false);
                } else {
                    searchToolbar.setVisibility(View.GONE);
                }
            }
        });

        searchMenuItem = searchToolbar.getMenu().findItem(R.id.action_filter_search);

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    circleReveal(false);
                } else
                    searchToolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;
            }
        });

        final SearchView searchView = (SearchView) searchMenuItem.getActionView();

        // Enable/Disable Submit button in the keyboard
        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close_grey_24dp);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search contacts & places");

    }

    public void setSupportActionBar(AppCompatActivity act) {
        act.setSupportActionBar(toolbar);
    }

    public boolean onSearchIconClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(true);
        } else {
            searchToolbar.setVisibility(View.VISIBLE);
        }
        searchMenuItem.expandActionView();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(final boolean isShow) {

        int positionFromRight = 1;
        int width = toolbar.getWidth();

        width -= (positionFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = searchToolbar.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(searchToolbar, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(searchToolbar, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    searchToolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if (isShow) {
            searchToolbar.setVisibility(View.VISIBLE);
        } else {

        }


        // start the animation
        anim.start();
    }

    public void setSearchHint(String hint) {
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint(hint);
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setTitleTextColor(int color) {
        toolbar.setTitleTextColor(color);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
