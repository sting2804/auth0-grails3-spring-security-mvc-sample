## Sample application demonstrating Auth0, Grails 3, and Spring Security integration for a Grails 3 API application

This is a companion sample for the [Auth0 Spring Security API](https://github.com/auth0/auth0-spring-security-api) library.
Please refer to that library and documentation for further information specific to the library itself.

This sample application shows you how to:

 1. Configure and run your Grails 3 application with Spring Security
 2. 100% Java Configuration (Annotations)
 3. Secure one or more URL endpoints with Role / Authority based permissions (ROLE_USER, ROLE_ADMIN etc)
 4. Secure Java Services using method level security annotations for role based access control

Let's get started - it only takes a few minutes to have a working application with all the above.

### Prerequisites

In order to run this example you will need to have Java 7+, and Grails 3 installed.

Check the [Grails](https://grails.org/) documentation for details on installing `Grails 3`.
[sdkman](http://sdkman.io/) is one useful tool for this purpose.

Check installed successfully with:

```sh
grails --version
```

FYI only, in creating this sample, the following SDK versions were used:

 ```
Grails Version: 3.1.9
Groovy Version: 2.4.7
JVM Version: 1.8.0_77
 ```

### Setup

Create an [Auth0 Account](https://auth0.com) (if not already done so - free!).


#### From the Auth0 Dashboard

Create an application - for the purposes of this sample - `app`

Ensure you add the following to the settings.


Add one or more `connections` to your application - for instance Google Social Connection,
or username-password DB connection.


###### Add Role Based Authorization By Creating an Auth0 Rule


Since this sample applies Role based authorization on the Home Page (defaults to requiring `ROLE_ADMIN`), go to `Rules`
(in the Auth0 dashboard) and create the following new Rule (just copy and paste the snippet below into a new rule):

```
function (user, context, callback) {
  user.app_metadata = user.app_metadata || {};
  // You can add a Role based on what you want
  // Here, we simply check domain
  var addRolesToUser = function(user, cb) {
    if (user.email.indexOf('@gmail.com') > -1) {
      cb(null, ['ROLE_ADMIN']);
   } else if (user.email.indexOf('@auth0.com') > -1) {
      cb(null, ['ROLE_ADMIN']);
    } else {
      cb(null, ['ROLE_USER']);
    }
  };

  addRolesToUser(user, function(err, roles) {
    if (err) {
      callback(err);
    } else {
      user.app_metadata.roles = roles;
      auth0.users.updateAppMetadata(user.user_id, user.app_metadata)
        .then(function(){
          callback(null, user, context);
        })
        .catch(function(err){
          callback(err);
        });
    }
  });
}
```

In our simple Rule above, we add `ROLE_ADMIN` to any user profiles whose email addresses are `gmail.com` and `auth0.com` domains.
Otherwise, we only provide `ROLE_USER` role. Our Spring Security Sample app will read this information from the UserProfile and apply
the granted authorities when checking authorization access to secured endpoints configured with Role based permissions
Obviously, please modify the rule to your needs (for example the email domain you will be using etc if not gmail).


### Inside the Application

###### Authentication and authorization settings

Here is our sample `AppConfig` entry where we specify the endpoints security settings -
defined under `src/com.auth0.example.AppConfig.java`


```
  // Apply the Authentication and Authorization Strategies your application endpoints require
    http.authorizeRequests()
            .antMatchers(securedRoute).authenticated();
    }
```

Here, we only allow users with `ROLE_USER` or `ROLE_ADMIN` to access the home page.

###### Update configuration information


For details on this settings, please check the README for the library this sample depends on  [Auth0 Spring Security API](https://github.com/auth0/auth0-spring-security-api).


### Build and Run

In order to build and run the project execute:

```sh
gradle bootRun
```

Then, go to [http://localhost:3099/](http://localhost:3099/).

The Grails landing page has been left with the sample so you can see the Controllers used, and get general information
on the Grails application itself. Since it launches from the root URL it is unsecured and hence available on startup.

To get token you can make GET request to [http://your_domain.auth0.com/oauth/ro](http://your_domain.auth0.com/oauth/ro). See more about this [https://auth0.com/docs/api/authentication#!#post--oauth-ro](here).

---


## License

The MIT License (MIT)

Copyright (c) 2013 AUTH10 LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
