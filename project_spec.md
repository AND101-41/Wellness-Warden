# **Wellness Warden**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description 

**A user friendly app that allows people to monitor their food intake and nutritional values. It displays accumulated calories,carbs, proteins and other nutrients over the day.**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->
- **Description**: A user friendly app that allows people to monitor their food intake and nutritional values. It displays accumulated calories,carbs, proteins and other nutrients over the day.
- **Category:**: Health and Fitness
- **Mobile:**: Mobile is essential as they are portable, so a person can bring their phones anywhere they go and log food information on the go. 
- **Story:**: It creates awareness about the stuff we eat, and can set limits on certain aspects of food. People who want to diet and loose weight need to monitor their intake and this app would help them do so.
- **Market:**: Health conscious individuals who are trying to improve their health,lose weight. Fitness enthusiasts and people with medical conditions.
- **Habit:**: The frequency of usage depends on the user's interest and engagement with their health and fitness.
- **Scope:** V1 would allow users to enter food items and retrieve nutritional information. V2 would allow users to track their daily intake and compare it to the recommended values. V3 would allow users to set goals and track their progress. V4 would allow users to track their progress over time and compare it to their goals. V5 would allow users to track their progress over time and compare it to their goals and earn rewards for accomplishing their goals. A stripped down version of the app would still be fun to build as the main theme of tracking and improving health would be still there.


## Product Spec

### 1. User Features (Required and Optional)

Required Features:
1. User can log food items they consume.
2. User can retrieve nutritional information of logged food items.
3. User can view their daily intake of calories, carbs, proteins, and other nutrients.
4. User can compare their daily intake with recommended dietary values.
5. User can set dietary goals.



Stretch Features:
1. User can get personalized diet plans according to their goals.
2. User can track their progress over time through graphs and charts.
3. User can share their progress on social media.
4. User can earn rewards or badges upon reaching their goals.
5. User can get alerts and reminders to log their meals.
6. User can scan barcodes of products to retrieve nutritional information.
7. User can receive recipe suggestions based on their nutritional needs.
8. User can connect with other app users for motivation and support.
9. User can get alerts of new research or articles related to their dietary preferences or goals.
10. User can consult with a dietitian or nutrition expert through the app.


### 2. Chosen API(s)

- **USDA Food Data Central**
  - **API Endpoint: https://fdc.nal.usda.gov/api-guide.html**
  - Information Used from the API
    - Detailed food information, including names, descriptions, serving sizes, and unique identifiers.
    - Comprehensive nutrient data, including macronutrients, vitamins, minerals, and other essential nutrients.
    - Data on different serving sizes for accurate measurement of food intake.
    - Information on food components and food groups for user classification.
    - Data sources and references for transparency and credibility.
    - Links to images and icons related to food items for a visually engaging user experience.
- **Nutrition API by ESHA Research**
  - **API Endpoint: https://nutrition-api-dev.esha.com/apis**
  - Information Used from the API
    - Nutritional information for food items, including calories, carbohydrates, proteins, fats, vitamins, and minerals, which is essential for monitoring food intake.
    - Ability to search and retrieve detailed nutritional data for foods that users log.
    - Calculation of daily intake of calories, carbs, proteins, and other nutrients based on logged food items.
    - Comparison of daily intake with recommended dietary values, providing feedback to users for healthier choices.
- **Edamam Nutrition Analysis API**
  - API Endpoint: https://developer.edamam.com/edamam-docs-nutrition-api
  - Information Used from the API:
    - Nutritional data for food items, including calories, carbs, proteins, and other nutrients, enabling users to track their dietary intake.
    - Ability to search and retrieve detailed nutritional information for logged food items.
    - Calculation and display of daily intake of essential nutrients based on logged food items.
    - Comparison of daily intake with recommended dietary values to provide users with feedback for informed decisions.

### 3. User Interaction

Required Features

**Example API**

**Get Food Items**: User can log food items they consume
- User types in the name of the food item
   => Results display based on the food item entered
- User can choose to log the food item
   => Food item gets added to the user's daily log

**Get Nutritional Info**: User can retrieve nutritional information of logged food items
- User chooses a logged food item
   => Nutritional information of the chosen food item is displayed

**Get Daily Intake**: User can view their daily intake of calories, carbs, proteins, and other nutrients
- User selects to view daily intake
   => Daily intake based on logged food items is displayed

**Get Recommended Values**: User can compare their daily intake with recommended dietary values
- User selects to compare daily intake with recommended values
   => Daily intake is compared and displayed alongside recommended values

**Set Goals**: User can set dietary goals
- User selects to set goals
   => User is prompted to enter their goals (e.g., daily caloric intake, protein intake, etc.)
- User enters their dietary goals
   => Goals are saved and displayed on the user's profile

**Track Goals**: User can track progress towards their goals
- User selects to view goal progress
   => Progress towards each goal, based on daily intake, is displayed

**Get Recipes**: User can receive recipe suggestions based on their nutritional needs (Optional)
- User selects to view recipe suggestions
   => Recipes that align with user's dietary goals and preferences are displayed

**Get Community**: User can connect with other app users for motivation and support (Optional)
- User selects to view community
   => A feed of posts from other users is displayed, along with options to interact (like, comment, etc.)

## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->

![20231105_170951](https://github.com/wresendiz1/and101-capstone/assets/105386979/cf60c2d0-199c-4e89-9130-01ca0d030a36)
![Nutritio_231105_170748_2](https://github.com/wresendiz1/and101-capstone/assets/105386979/6bbbb089-1c8e-4789-a9a2-a4465eab821c)
![20231105_170919](https://github.com/wresendiz1/and101-capstone/assets/105386979/7fd98c54-6387-4170-8fec-95cc26264a9e)


### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Build Notes

Here's a place for any other notes on the app, it's creation 
process, or what you learned this unit!  

For Milestone 2, include **2+ Videos/GIFs** of the build process here!

## License

Copyright **2023** **Willy Resendiz, Ray Isaac Simbiri, Abdinahmen Ahmed, Heet Patel**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
