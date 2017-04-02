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
import android.text.Editable;
import android.text.TextWatcher;
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

public class SearchAnimationToolbar extends FrameLayout implements TextWatcher {

    static final String TAG = "SearchToolbar";

    public interface OnSearchQueryChangedListener {
        void onSearchCollapsed();
        void onSearchQueryChanged(String query);
        void onSearchExpanded();
    }


    private Toolbar toolbar;
    private Toolbar searchToolbar;
    private MenuItem searchMenuItem;
    private OnSearchQueryChangedListener onSearchQueryChangedListener;
    private String currentQuery = "";

    public SearchAnimationToolbar(Context context) {
        this(context, null);
    }

    public SearchAnimationToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchAnimationToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateAndBindViews();
    }

    private void inflateAndBindViews() {
        View.inflate(getContext(), R.layout.view_search_toolbar, SearchAnimationToolbar.this);

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
        txtSearch.addTextChangedListener(SearchAnimationToolbar.this);

    }

    @Override
    protected void onDetachedFromWindow() {
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search contacts & places");
        txtSearch.removeTextChangedListener(SearchAnimationToolbar.this);
        super.onDetachedFromWindow();
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
        
        int width = toolbar.getWidth();
        width -= (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);

        int cx = width;
        int cy = toolbar.getHeight() / 2;

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
            notifySearchExpanded();
        } else {
            notifySearchCallapsed();
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

    public void setOnSearchQueryChangedListener(OnSearchQueryChangedListener onSearchQueryChangedListener) {
        this.onSearchQueryChangedListener = onSearchQueryChangedListener;
    }

    private void notifySearchCallapsed() {
        if (this.onSearchQueryChangedListener != null) {
            this.onSearchQueryChangedListener.onSearchCollapsed();
        }
    }

    private void notifySearchExpanded() {
        if (this.onSearchQueryChangedListener != null) {
            this.onSearchQueryChangedListener.onSearchExpanded();
        }
    }

    private void notifySearchQueryChanged(String q) {
        if (this.onSearchQueryChangedListener != null) {
            this.onSearchQueryChangedListener.onSearchQueryChanged(q);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!currentQuery.equalsIgnoreCase(s.toString())) {
            notifySearchQueryChanged(s.toString());
        }

    }
}
