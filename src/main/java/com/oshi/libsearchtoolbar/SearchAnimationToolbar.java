package com.oshi.libsearchtoolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAnimationToolbar extends FrameLayout implements TextWatcher {

    public interface OnSearchQueryChangedListener {
        void onSearchCollapsed();

        void onSearchQueryChanged(String query);

        void onSearchExpanded();

        void onSearchSubmitted(String query);
    }

    private String toolbarTitle;
    private String toolbarSubtitle;
    private String searchHint;
    private int toolbarTitleColor;
    private int toolbarSubtitleColor;
    private int searchBackgroundColor;


    private Toolbar toolbar;
    private Toolbar searchToolbar;
    private EditText txtSearch;
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
        bindAttributes(attrs);
    }

    private void bindAttributes(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SearchAnimationToolbar);
        toolbarTitle = typedArray.getString(R.styleable.SearchAnimationToolbar_title);
        toolbarTitleColor = typedArray.getColor(R.styleable.SearchAnimationToolbar_titleTextColor, Color.WHITE);
        toolbarSubtitle = typedArray.getString(R.styleable.SearchAnimationToolbar_subtitle);
        toolbarSubtitleColor = typedArray.getColor(R.styleable.SearchAnimationToolbar_subtitleTextColor, Color.WHITE);

        toolbar.setTitle(toolbarTitle);
        toolbar.setTitleTextColor(toolbarTitleColor);

        if (!TextUtils.isEmpty(toolbarSubtitle)) {
            toolbar.setSubtitle(toolbarSubtitle);
            toolbar.setSubtitleTextColor(toolbarSubtitleColor);
        }

        searchHint = typedArray.getString(R.styleable.SearchAnimationToolbar_searchHint);

        if (!TextUtils.isEmpty(searchHint)) {
            txtSearch.setHint(searchHint);
        }

        searchBackgroundColor = typedArray.getColor(R.styleable.SearchAnimationToolbar_searchBackgroundColor, Color.WHITE);
        searchToolbar.setBackgroundColor(searchBackgroundColor);

    }



    private void inflateAndBindViews() {
        View.inflate(getContext(), R.layout.view_search_toolbar, SearchAnimationToolbar.this);

        toolbar = (Toolbar) findViewById(R.id.lib_search_animation_toolbar);
        searchToolbar = (Toolbar) findViewById(R.id.lib_search_animation_search_toolbar);

        searchToolbar.inflateMenu(R.menu.lib_search_toolbar_menu_search);
        searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse();
            }
        });

        searchMenuItem = searchToolbar.getMenu().findItem(R.id.lib_search_toolbar_action_filter_search);

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                collapse();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
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
        txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.addTextChangedListener(SearchAnimationToolbar.this);
        txtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    notifySearchSubmitted(txtSearch.getText().toString());
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        txtSearch.removeTextChangedListener(SearchAnimationToolbar.this);
        super.onDetachedFromWindow();
    }

    public void setSupportActionBar(AppCompatActivity act) {
        act.setSupportActionBar(toolbar);
    }

    public boolean onSearchIconClick() {
        expand();
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

        anim.setDuration(250L);

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
        txtSearch.setHint(hint);
    }

    public boolean onBackPressed() {
        boolean isInSearchMode = searchToolbar.getVisibility() == View.VISIBLE;
        if (!isInSearchMode) {
            return false;
        }

        collapse();
        searchMenuItem.collapseActionView();
        return true;
    }


    public void setTitle(String title) {
        toolbar.setTitle(title);
        toolbar.invalidate();
    }

    public void setTitleTextColor(int color) {
        toolbar.setTitleTextColor(color);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Toolbar getSearchToolbar() {
        return searchToolbar;
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

    private void notifySearchSubmitted(String q) {
        if (this.onSearchQueryChangedListener != null) {
            this.onSearchQueryChangedListener.onSearchSubmitted(q);
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

    private void collapse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(false);
        } else
            searchToolbar.setVisibility(View.GONE);
    }

    private void expand() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(true);
        } else {
            searchToolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Expands the toolbar (Without animation) and update the search with a given query
     */
    public void expandAndSearch(String query) {
        searchToolbar.setVisibility(View.VISIBLE);
        searchMenuItem.expandActionView();
        txtSearch.setText(query);
    }
}
