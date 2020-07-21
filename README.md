# news-feed
# IBA Tech Academy final project

# News Feed Crawler!

## About
This is a monolith project to **scrap** several **news websites automatically** and **collect** them in **one page** ordering by time.
Scrapping executes with fixed 2 hours delay. 
Running version: [https://news-feed-crawler.herokuapp.com/](https://news-feed-crawler.herokuapp.com/)

Scrapped sites:
 - https://www.foxnews.com/world
 - https://www.huffpost.com/
 - https://news.az/
 - https://www.aljazeera.com/news
 - https://www.aljazeera.com/topics/regions/asia.html
 - https://www.businessinsider.co.za/ with all subdomains
 - https://techstartups.com/

##  Functionalities

 1. Create an account by using OAuth2 Gmail,Facebook or Custom Registration
 2. Login
 3. Logout
 4. See all news in main page
 5. Get details of news
 6. Open source of news
 7. See list of authors and enable/disable them
 8. Search news
 9. Automatic Crawling websites with 2 hours delay and save information in database

## Technical details

Thymeleaf is used as a template engine. Front end is pure HTML,CSS and JS. 
It is used Spring Boot,Spring MVC for backend. Spring Security customized for usage. Database is PostgreSQL. Spring Data JPA and custom queries are used to data persistance. Project deployed in Heroku. Registration verification and reset password handled by sending gmail. Java Mail Sender is used for this purpose.

## How to run

Running in local you need to update credentials in application.properties. Default port is 5000. 
command to build project: **mvn clean install**

