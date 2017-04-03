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
````
searchAnimationToolbar = (SearchAnimationToolbar) findViewById(R.id.customSearchToolbar);
searchAnimationToolbar.setSupportActionBar(MainCallappActivity.this);
searchAnimationToolbar.setTitle("Search animation!");
searchAnimationToolbar.setOnSearchQueryChangedListener(MainCallappActivity.this);
searchAnimationToolbar.setSearchHint("Search for...");
searchAnimationToolbar.setTitleTextColor(Color.WHITE);
````

Alternativly, You can take the Toolbar:
````javascript
customSearchAnimationToolbar.getToolbar()
````
And do what ever you like :)
