TBD

Plan:
- [Done] create example test cases
- support of working with DB
- BDD


selenium-framework-skeleton
===========================

This code allows to quickly start a selenium test automation framework preparation.
It already has all necessary possibilities to run test cases against a web based application.
The skeleton contains several test cases for demonstration purpose; you may use them as example for your own test cases.

How to run
==========

In order to run the example test cases just execute:

```mvn test```

The whole command is:

```
mvn test -Dsuite=suitename.xml -Drun_config=run_config_file -Denv_config=env_config_file -Dcreds=creds_file -Ddb_config=db_config_file -Dbrowser=firefox -Dheadless=true -Dgrid=false -Dselenoid=false
```

Where:

**1) -Dsuite=suitename.xml: testNG xml suite to run. Default suite name is defined in the POM file.

**2) -Drun_config=run_config_file: a config file with common test execution run parameters. The file "run_config_main" is used by default.

**3) -Denv_config=env_config_file: an environment config file. The file "env_config_main" is used by default.

**4) -Dcreds=creds_file: a file with test user credentials and additional information. The file "creds_main" is used by default.

**5) -Ddb_config=db_config_file: a file with DB connection details. The file "db_config_main" is used by default.

**6) -Dbrowser=firefox: this parameter says that the Firefox browser should be used for the test execution run. Possible parameters.
- firefox
- chrome
- mix

If you run test cases in parallel and set "mix" - in this case the first thread will use Firefox, the second one will use Chrome and so on.

If the browser parameter is not defined - the value from "env_config" file is used by default.

**7) -Dheadless=true: means that browsers will be run in headless mode. The value from the "run_config" file is used by default.

**8) -Dgrid=false: switch the value to "true" if you plan to launch test cases using Selenium Grid. The appropriate parameters are located in the "run_config" file.

**9) -Dselenoid=false: switch the value to "true" if you plan to launch test cases inside Selenoid docker container. Default value is set in the "run_config" file.

So it is possible to define the parameters above with two ways: using config files and using command line parametrization. The values from command line has higher priority.

Reporting
=========
