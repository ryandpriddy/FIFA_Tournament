# PA1 - CSCI 3920 Programming Assignment 1. 
Tournament Management System using JavaFX with Networking and Multithreading Implementations

## Functionality

### Numbered Tasks

- [ ] 1. Load From File
  - [ ] Server Side
  - [ ] Client Side (Controller)
  - [ ] Scene Builder
  - [ ] Tournament Package & Error Handling
  
- [ ] 2. Save to File
  - [ ] Server Side
  - [ ] Client Side (Controller)
  - [ ] Scene Builder
  - [ ] Tournament Package & Error Handling

 - [x] 3. Create a New Tournament
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
  
 - [x] 4. Add a Participating Country
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [x] 5. national team representing a country to the tournament.
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
     - [x] E.g., the USA national team, Spain national team, etc.
     - [x] An exception is expected when the country does not exist, or another team has the same name
     
 - [x] 6. Add referees to the tournament. - JM
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [x] 7. Add a player to a national team squad
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [x] 8. Add a match on a particular date and time between two national teams
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [ ] 9. Assign a referee to a match. - JM
   - [ ] Server Side
   - [ ] Client Side (Controller)
   - [x] Scene Builder
   - [ ] Tournament Package & Error Handling
   
 - [x] 10. Add a player to a national team’s lineup for a particular match.
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [x] 11. Record the score of a completed match.
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [x] 12. Get a list of the upcoming matches, listing the date/time and the name of the two teams for each
game.
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
 - [x] 13. Get a list of matches on a particular date (no time)
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling

 - [x] 14. Get a list of all games for a specific team. Past matches should include the score.
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling

 - [x] 15. Get the lineups for a match (either past or future).
   - [x] Server Side
   - [x] Client Side (Controller)
   - [x] Scene Builder
   - [x] Tournament Package & Error Handling
   
### Other Tasks
   
 - [ ] General Needs
   - [x] Interface
     - [x] Choose Interface
     - [x] Welcome Page
     - [x] Admin Launch / Tabs Setup
     - [x] Public Launch / Tabs Setup
   - [ ] JavaDoc for methods
     - [ ] TODOs added for current methods, up to commit Sat Night
     - [x] Tournament Package Methods
       - [x] Tournament
       - [x] Country
       - [x] Team
       - [x] Player
       - [x] Match
       - [x] LineUp
       - [x] Referee
     - [ ] Server Package Methods
      - [ ] Server
      - [ ] Client Worker
     - [ ] Application Package Methods
      - [ ] ServerApplication Methods
      - [ ] PublicApplication Methods
      - [ ] PublicController Methods
      - [ ] AdminApplication Methods
      - [ ] AdminController Methods
      - [ ] ChooseApplication Methods
      - [ ] ChooseController Methods
      

 - [x] General GUI
   - [x] Admin
     - [x] Tab navigation
     - [x] CSS Theme
       - [x] Backgrounds
       - [x] Icons
       - [x] Buttons
       - [x] Lists
       - [x] Combo Boxes
       - [x] Date Pickers
       - [x] font-styles
       - [x] border-radius
   - [x] GenPub
     - [x] Tab navigation
     - [x] CSS Theme
       - [x] Backgrounds
       - [x] Icons
       - [x] Buttons
   - [x] Choose
     - [x] Tab navigation
     - [x] CSS Theme
       - [x] Backgrounds
       - [x] Icons
       - [x] Buttons

## Tournament Package

### Tournament Class
**Method implementation info here

### Other Member Classes
Country
Player
Team
Match
LineUp
Referee

## Application Package 
JavaFX GUI with Admin and General Public Interfaces

## Server Package
Handles incoming client requests and spawns ClientWorkers to concurrently work with each connected Client
