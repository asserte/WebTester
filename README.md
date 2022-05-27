# WebTester
WebTester is a test automation accelerator that simplifies development, maintenance and execution of Web UI automated test cases.

WebTester lets you execute a full suite or selected scripts via Jenkins and has a set of handy methods that make test scripts more readable and compact. The framework performs best in applications with a large number of text fields and dynamic HTML IDs.

## :package: Packages


### TestCase
[@webdriver/testCase](https://github.com/asserte/WebTester/tree/main/src/main/java/lt/insoft/webdriver/testCase)
is a package that contains main framework features:

#### WebTester
[@webdriver/testCase/webTester](https://github.com/asserte/WebTester/tree/main/src/main/java/lt/insoft/webdriver/testCase/webTester)
The following package offers a number of methods for clicking, finding, and writing items, as well as a number of smart waits.

#### Utils
[@webdriver/testCase/utils](https://github.com/asserte/WebTester/tree/main/src/main/java/lt/insoft/webdriver/testCase/utils)
The Utils package offers the main methods for creating drivers for Chrome, Edge, Firefox, and Internet Explorer. It also has a feature that highlights selected fields or buttons. Readers package additionally includes helpful methods to read information from Excel and PDF files.  

### Resources
[@resources](https://github.com/asserte/WebTester/tree/main/src/main/resources)
We have already prepared possible file formats that may be required for testing in the Resources package: JPG, PDF, PNG, and DOCX files. It also includes app.properties file, which is used to configure elements like the App URL, Browser, Thread count, and other.


## :bookmark_tabs: Execution and Reporting

#### Execution and Parametrization
WebTester allows users to select required parameters. It provides parametrization option that is both unlimited and configurable.
Jobs can, of course, be scheduled and executed at specific times and days.

![Project Run Example](https://user-images.githubusercontent.com/54704578/168271119-04633ad7-9722-4195-aeee-07701fd1a5d6.png)

#### Reporting
The WebTester framework uses the Allure plugin for reporting. It makes it simple to track and detect potential flaws. Each failed test case includes a screenshot of the webpage. Of course, this feature might be extended, with screenshots recorded after each test case or step.

![Allure Report](https://user-images.githubusercontent.com/54704578/168273806-ee694fca-68e7-4301-b1d6-33019f0efac3.png)
![Allure inside of report](https://user-images.githubusercontent.com/54704578/168273823-e3948807-18b2-43c1-8eee-e0b04aa0e065.png)

