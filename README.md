Search toolbar with animation
===================
Create a simple toolbar with circurlar reveal animation when searching!



![](https://github.com/UdiOshi85/GlobalFiles/blob/master/libSearchAnimationToolbar%20-1.0.gif)

## Usage
**Circle animation will work only on devices with Lollipop devices and above**

#### First, Compile library into your code
```javascript
compile 'com.not.ready.yet'
```

In your XML file put the following:
````javascript
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
