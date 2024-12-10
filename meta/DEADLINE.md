# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

The Weather Food Suggestion app is a JavaFX application which is used to find the weather of the inputted cities and also gather its longitude and latitude and its weather code and offers a suggestion of food items based on the weather conditions.

The primary functions are to get the latitude and longitude of the city, use the coordinates to get weather details, and based on the weather conditions suggest pre written food options.

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 1

Nominatim API: Used to get the latitude and longitude of the entered city.
https://nominatim.openstreetmap.org/search?q=San+Francisco&format=json&limit=1
It does not require an API key but has rate limits and is 1 request per second

### API 2

Open-Meteo API: Used to get current weather details based on the city’s coordinates.
https://api.open-meteo.com/v1/forecast?latitude=37.7749&longitude=-122.4194&current_weather=true




## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

Something new I leanred is how to integrate multiple RESTful JSON API's into one application. I also enjoyed learning to parse the complex JSON data into Java objects using Gson and to structure the API calls perfectly with the HttpClient. JavaFX just made the experience fun and gave me a chance to see me hardwok as an interactive UI that users could use.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

If I could start this project over from scratch, I would spend time with the structure of the appliction before just getting into the code which is reccomended yes but I feel like I didnt do a gopod job planning as I was too excited to just code and see where my mind takes me. I would specifically focus defining the responsibilities for each class to avoid repeating code for example I had a redundancy in my code where I had 2 of the same functions with different APIs being used for the same purpose so its mistakes like that where I would just write down neatly what I would need to do before I just jump into coding.
