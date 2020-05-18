Sample Cucumber / Selenium / Java UI Test Framework
====================================================

This framework is a work in progress. It will:

* Read config files
    * One for the SUT (.e.g its root URL) 
    * One for the test framework (e.g. which browser to use) 
* Run the specified actor (browser) on the local device (PC)
* Manage the Web Driver so that it sets up and kills the browser around each scenario
* Produce a pretty HTML report at the end

# Installation
* Goes into a Maven project
* Pull the contents of the repo and just place it at the root of the project. Thus you get the pom.xml and a src folder in the root of the project
* Your IDE will want to retrieve dependencies.

# Configuration
 
Edit the configuration files

* Framework properties  e.g.
    * Browser, 
    * Headless or not, 
    * Wait times, and so on ...
* SUT properties e.g.
    * URL for SUT
* HTML reports
    * The reports will be written where you specify in the plugin in the TestRunner, not in this file
    * Change the report title with this file

# Use
* You can see where to put your feature files, and other test-ware.
* You will notice that the framework contains some stepDefs. These are merely to do with the framework's own smoke tests and can be (should be?) dispensed-with without tears. 
* ContextOfTest - items that remain constant for the entire run e.g.
    * Browser, headless or not, wait times, location of SUT
    * It is not likely that you will want alter stuff here, but you may want to use stuff from it
* ContextOfScenario - items that exist for the duration of each scenario
    * Create fields here to simply share data between steps within a scenario.
* Edit the test context config file to use different browsers for some simple x-browser testing, and so on.

# Roadmap
In no particular order

* Add RESTful API testing (transparent to the features)
* Add Safari to the browser factory
* Add more exciting 'options' to the browsers / configuration files
* Appium
* FTP report to somewhere (e.g. a web server)
