# WebTester 
is a test automation framework that lets you develop, maintain and execute Selenium WebDriver and Java based test scripts. 

It has a set of handy methods that make test scripts more readable and compact.

## :package: Packages

### Runner
`@webdriver/runner`
A WebTester runner to execute test scripts
It includes @webdriver/runner/XmlBuilder.java that is integrated technology which will allow you to configure test suite in Jenkins. 
Possibility to set Browser, Thread count and Application url and etc.

### TestCase
`@webdriver/testCase`

### WebTester
`@webdriver/testCase/webTester`
This package contains class for webdriver initiation as well as classes that add Custom Allure step methods to the existing WebTester methods as well as extra features to the existing webdriver find, click, setText, etc, functionalities.

### Utils
`@webdriver/testCase/utils`
Utils package that contains Highlighters and DriverCreator classes. 
Highlighters is a class for element colouring. 
DriverCreator is a class that creates driver; you can choose from Chrome, Edge, Internet Explorer and Firefox as well as setup proxy.
:man_technologist:
