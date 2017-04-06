Navigator Demo
======
Show demo how we navigate in Android App generally.

 ![logo](photo/ic_launcher.png)

### Architecture

- [Model–view–presenter(MVP)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)
- [Dagger2](https://google.github.io/dagger/) 
- [Retrofit2](http://square.github.io/retrofit/) 
- [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- [EventBus](https://github.com/greenrobot/EventBus) 
- [Data Binding](https://developer.android.com/topic/libraries/data-binding/index.html) 


### Design pattern

- [Composite](https://en.wikipedia.org/wiki/Composite_pattern)
- [Singleton](hhttps://en.wikipedia.org/wiki/Singleton_pattern)
- [Model–view–presenter(MVP)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)

### Other libraries and dependencies

- [GSON](https://github.com/google/gson) as data-parse.
- [smoothprogressbar](https://github.com/castorflex/SmoothProgressBar) as better loading-indicator.
- [Android Support-lib](https://developer.android.com/topic/libraries/support-library/index.html) as you know.

### Testing

[JUnit + Espresso](https://developer.android.com/training/testing/ui-testing/espresso-testing.html)

### Data Structure

There are only 2 types to represent how navigation-data will be generated, the ```Entry``` and ```NavigationEntries``` which is aggregated by ```Entry```.

```Entry``` can also be aggregated by itself.

![Structure](photo/data-struct.png)

Also you get that there are four ```Entry``` types:

- section: represented in JSON as"type": "section"
- node: represented in JSON as "type": "node"
- link: represented in JSON as "type": "link"
- external-link: represented in JSON as "type": "external-link"

Here we use different layouts to represent them.

```ìtem-section.xml``` ```ìtem-node.xml``` ```ìtem-link.xml``` (also for external-link)

 ![Layout](photo/layout-case.png)

```json
{
	"navigationEntries": [{
		"type": "section",
		"label": "Sortiment",
		"children": [{
			"type": "node",
			"label": "Alter",
			"children": [{
				"type": "node",
				"label": "Baby & Kleinkind",
				"children": [{
					"type": "link",
					"label": "0-6 Monate",
					"url": "http:\/\/www.mytoys.de\/0-6-months\/"
				}, {
					"type": "external-link",
					"label": "7-12 Monate",
					"url": "http:\/\/www.mytoys.de\/7-12-months\/"
				}]
			}]
		}]
	}]
}
```

### LICENSE

> MIT License
 ```
  Copyright (c) 2017 Chris Xinyue Zhao
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
  ```

