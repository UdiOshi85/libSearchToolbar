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

In your JAVA file:
````javascript
SearchAnimationToolbar customSearchAnimationToolbar = (SearchAnimationToolbar) findViewById(R.id.customSearchToolbar);
        customSearchAnimationToolbar.setTitle("Search animation!");
        customSearchAnimationToolbar.setSupportActionBar(MainCallappActivity.this);
        customSearchAnimationToolbar.setOnSearchQueryChangedListener(MainCallappActivity.this);
        customSearchAnimationToolbar.setSearchHint("Search for...");
        customSearchAnimationToolbar.setTitleTextColor(Color.WHITE);
````

Alternativly, You can take the Toolbar:
````javascript
customSearchAnimationToolbar.getToolbar()
````
And do what ever you like :)
