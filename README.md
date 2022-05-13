# WebTester  :man_technologist:
is a test automation framework that lets you develop, maintain and execute Selenium WebDriver and Java based test scripts. 

WebTester lets you execute full siute or selected scripts via Jenkins and has a set of handy methods that make test scripts more readable and compact. 

## :package: Packages


### TestCase
[@webdriver/testCase](https://github.com/asserte/WebTester/tree/main/src/main/java/lt/insoft/webdriver/testCase)

#### WebTester
[@webdriver/testCase/webTester](https://github.com/asserte/WebTester/tree/main/src/main/java/lt/insoft/webdriver/testCase/webTester)
This package contains class for webdriver initiation as well as classes that add Custom Allure step methods to the existing WebTester methods as well as extra features to the existing webdriver find, click, setText, etc, functionalities.

#### Utils
[@webdriver/testCase/utils](https://github.com/asserte/WebTester/tree/main/src/main/java/lt/insoft/webdriver/testCase/utils)
 package that contains Highlighters and DriverCreator classes. 
Highlighters is a class for element colouring. 
DriverCreator is a class that creates driver; you can choose from Chrome, Edge, Internet Explorer and Firefox as well as setup proxy.

### Resources
[@resources](https://github.com/asserte/WebTester/tree/main/src/main/resources)
already has prepared possible file formats that might be resuired for testing: JPG, PDF, PNG and DOCX files. It also contains app.propertis files used for configurations, such a App URL, Browser, Thread count and other. 

![test](https://user-images.githubusercontent.com/54704578/168267340-b26186ba-3190-4bb0-b7d2-a603b498811f.png)
