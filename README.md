Search toolbar with animation
===================
Create a simple toolbar with circurlar reveal animation when searching!

### The animation will work on Lollipop devices, For older devices we'll just ignore the animation but it's still work**
----------

### Example

![](https://github.com/UdiOshi85/GlobalFiles/blob/master/libSearchAnimationToolbar%20-1.0.gif)

----------

## Usage


#### First, Compile library into your code

> compile 'com.not.ready.yet'


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
