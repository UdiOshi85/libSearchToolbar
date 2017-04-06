Search toolbar with animation
===================
Create a simple toolbar with circurlar reveal animation when searching!

## Api > 21 ? Circular reveal animation! : change without animation;
----------

### Example

![](https://github.com/UdiOshi85/GlobalFiles/blob/master/libSearchAnimationToolbar%20-1.0.gif)

----------

## Usage


### In your application gradle file, add the following
### compile 'com.github.udioshi85:libSearchToolbar:1.0.0'


In your XML file put the following:
````
<com.oshi.libsearchtoolbar.SearchAnimationToolbar
	android:id="@+id/customSearchToolbar"
	android:layout_width="match_parent"
	android:layout_height="wrap_content" />
````

Your Activity/Fragment onCreate/onViewCreated:

````
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchAnimationToolbar searchAnimationToolbar = (SearchAnimationToolbar) findViewById(R.id.customSearchToolbar);
        searchAnimationToolbar.setSupportActionBar(MainCallappActivity.this);
        searchAnimationToolbar.setTitle("Search animation!");
        searchAnimationToolbar.setOnSearchQueryChangedListener(MainCallappActivity.this);
        searchAnimationToolbar.setSearchHint("Search for...");
        searchAnimationToolbar.setTitleTextColor(Color.WHITE);

    }
````


Override the lifecycle menu handling
````
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Provide your own menu xml, no library interaction here
	// This xml should have an menu item with magnifying glass icon
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
````
Once the user clicked on the magnifying glass, let's handle it in the following way:
````
@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.your_magnifying_icon_menu_item_id) {
	    // Tell the library that the icon was clicked, we will handle everything else
            return searchAnimationToolbar.onSearchIconClick();
        }
        return super.onOptionsItemSelected(item);
    }
````

> Alternativly, You can take the Toolbar:
> ````javascript
> customSearchAnimationToolbar.getToolbar()
> ````

Developer notes
--------------------
* I've implemented everything assuming that your menu xml has only 1 item and it's the magnifying glass one
* The animation uses API > 21 so if your device is below it's still work but without the animation
* Found a Bug? please report in our [Issues section](https://github.com/UdiOshi85/libSearchToolbar/issues)
* License under Apache 2.0
