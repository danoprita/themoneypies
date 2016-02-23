# themoneypies

[![Build Status](https://travis-ci.org/danoprita/themoneypies.svg?branch=master)](https://travis-ci.org/danoprita/themoneypies)

## Build and run

1. Clone the blog repo:

        git clone git@github.com:danoprita/themoneypies.git

1. Install npm. See NodeJS: [https://nodejs.org/en/](https://nodejs.org/en/)

1. Install bower [http://bower.io/](http://bower.io/)

        npm install -g bower

1. Install dependencies:

        cd themoneypies
        bower install

1. Build the project

        mvn package

1. Copy .jar file to another directory

        cp target/themoneypies-1.0-SNAPSHOT.jar another-directory
 
1. Start the application (web server)

        cd another-directory
        java -jar themoneypies-1.0-SNAPSHOT.jar
        
1. Browse to [http://localhost:8081/index.html](http://localhost:8081/index.html)

