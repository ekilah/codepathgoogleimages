### GoogleImagesSearch
An Android app for viewing Google Images search results, built during a @codepath course on app development in Android. The purpose of the assignment was to practice more with UIs/Activities, Intents, and third party resources.

#### Details
- How much time did this take?
  - This took me probably 12-15 hours of dev time. Most of that was spent on the UI layouts (including iterations).
- Required user stories completed:
  - [x] User can enter a search query that will display a grid of image results from the Google Image API. 
  - [x] User can click on "settings" which allows selection of advanced search options to filter results
  - [x] User can configure advanced search filters such as:
    - [x] Size (small, medium, large, extra-large)
    - [x] Color filter (black, blue, brown, gray, green, etc...)
    - [x] Type (faces, photo, clip art, line art)
    - [x] Site (espn.com)
  - [x] Subsequent searches will have any filters applied to the search results
  - [x] User can tap on any image in results to see the image full-screen
  - [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages - API v1 limitation)
- Advanced optional user stories completed:
  - [x] Error cases reported via Toasts (no internet, parse error, API pagination limit reached)
  - [x] Toolbar instead of ActionBar
    - [x] SearchView on Toolbar
  - [x] Intents for sharing images to other apps on fullscreen view
  - [x] Improved user interface (a bit)
- Bonus optional user stories completed:
  - [x] StaggeredGridView instead of GridView
- My own additional features
  - [x] Ability to open source web page/article/etc. where the image was found
  - [x] Ability to open standalone image URL in a browser (app) of your choice
  - [x] Dimensions of source shown on fullscreen page
  - [x] Image title shown on initial results page
  - [x] Image title and content shown on fullscreen view
    - [x] Can be dismissed/enabled with a long click on the fullscreen image
      - [x] Haptic feedback (vibrate) on dismiss/enable
  - [x] Swipe left or right on the fullscreen view to see the other results
  - [x] Clickable hashtags and usernames


####Demo time!
enjoy these demos of the latest features (made with [LICEcap](http://www.cockos.com/licecap/))

Master:

![Demo GIF](https://github.com/ekilah/codepathgoogleimages/blob/master/demo.gif)
