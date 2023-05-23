package edu.ucdenver.tournament;

import java.time.LocalDateTime;

public class TourTester {
    public static void main(String[] args) {
        Tournament tour1 = initTestTour();
    }

    public static Tournament initTourLite() {
        Tournament tournament = new Tournament("FIFA 2077", LocalDateTime.of(2077, 6, 12, 12,00,00),
                LocalDateTime.of(2077, 10, 10, 12, 00, 00));
        //All Qualified Countries
        tournament.addCountry("USA");
        tournament.addCountry("England");
        tournament.addCountry("France");
        tournament.addCountry("Qatar");
        tournament.addCountry("Ecuador");
        tournament.addCountry("Senegal");
        tournament.addCountry("Netherlands");
        tournament.addCountry("Iran");
        tournament.addCountry("Wales");
        tournament.addCountry("Belgium");
        tournament.addCountry("Canada");
        tournament.addCountry("Morocco");
        tournament.addCountry("Croatia");
        tournament.addCountry("Argentina");
        tournament.addCountry("Saudi Arabia");
        tournament.addCountry("Mexico");
        tournament.addCountry("Poland");
        tournament.addCountry("Brazil");
        tournament.addCountry("Serbia");
        tournament.addCountry("Switzerland");
        tournament.addCountry("Cameroon");
        tournament.addCountry("Australia");
        tournament.addCountry("Denmark");
        tournament.addCountry("Tunisia");
        tournament.addCountry("Japan");
        tournament.addCountry("Spain");
        tournament.addCountry("Portugal");
        tournament.addCountry("Ghana");
        tournament.addCountry("Germany");
        tournament.addCountry("Uruguay");
        tournament.addCountry("Costa Rica");
        tournament.addCountry("South Korea");

        //Referees
        for (int i=0; i < tournament.getParticipatingCountries().size(); i++) {
            String refereeName = "Referee #" + i;
            String countryName = tournament.getParticipatingCountries().get(i).getCountryName();
            tournament.addReferee(refereeName, countryName);
        }
        //Teams
        for (int i=0; i < tournament.getParticipatingCountries().size(); i++){
            String teamName = tournament.getParticipatingCountries().get(i).getCountryName() + " National Team";
            String countryName = tournament.getParticipatingCountries().get(i).getCountryName();
            tournament.addTeam(teamName, countryName);
        }

        //Team Squad -- adds 34/35 so add functions work
        for (int i=0; i < tournament.getListTeam().size(); i++){
            for (int j = 1; j < 35; j++){
                String playerName = "Player #" + j;
                Integer age = 18 + j;
                Double height = 60.0 + j;
                Double weight = 160.0 + j;
                tournament.getListTeam().get(i).addPlayer(playerName, age, height, weight);
            }
        }

        //Matches
        LocalDateTime match1DT = LocalDateTime.of(2022,11,20,9,00, 00);
        LocalDateTime match2DT = LocalDateTime.of(2022,11,21,6,00, 00);
        LocalDateTime match3DT = LocalDateTime.of(2022,11,21,12,00, 00);
        LocalDateTime match4DT = LocalDateTime.of(2022,11,21,9,00, 00);

        LocalDateTime match5DT = LocalDateTime.of(2022,11,22,3,00, 00);
        LocalDateTime match6DT = LocalDateTime.of(2022,11,22,6,00, 00);
        LocalDateTime match7DT = LocalDateTime.of(2022,11,22,9,00, 00);
        LocalDateTime match8DT = LocalDateTime.of(2022,11,22,12,00, 00);

        LocalDateTime match9DT = LocalDateTime.of(2022,11,23,3,00, 00);
        LocalDateTime match10DT = LocalDateTime.of(2022,11,23,6,00, 00);
        LocalDateTime match11DT = LocalDateTime.of(2022,11,23,9,00, 00);
        LocalDateTime match12DT = LocalDateTime.of(2022,11,23,12,00, 00);

        LocalDateTime match13DT = LocalDateTime.of(2022,11,24,3,00, 00);
        LocalDateTime match14DT = LocalDateTime.of(2022,11,24,6,00, 00);
        LocalDateTime match15DT = LocalDateTime.of(2022,11,24,9,00, 00);
        LocalDateTime match16DT = LocalDateTime.of(2022,11,24,12,00, 00);

        LocalDateTime match17DT = LocalDateTime.of(2022,11,25,3,00, 00);
        LocalDateTime match18DT = LocalDateTime.of(2022,11,25,6,00, 00);
        LocalDateTime match19DT = LocalDateTime.of(2022,11,25,9,00, 00);
        LocalDateTime match20DT = LocalDateTime.of(2022,11,25,12,00, 00);

        LocalDateTime match21DT = LocalDateTime.of(2022,11,26,3,00, 00);
        LocalDateTime match22DT = LocalDateTime.of(2022,11,26,6,00, 00);
        LocalDateTime match23DT = LocalDateTime.of(2022,11,26,9,00, 00);
        LocalDateTime match24DT = LocalDateTime.of(2022,11,26,12,00, 00);

        LocalDateTime match25DT = LocalDateTime.of(2022,11,27,3,00, 00);
        LocalDateTime match26DT = LocalDateTime.of(2022,11,27,6,00, 00);
        LocalDateTime match27DT = LocalDateTime.of(2022,11,27,9,00, 00);
        LocalDateTime match28DT = LocalDateTime.of(2022,11,27,12,00, 00);

        LocalDateTime match29DT = LocalDateTime.of(2022,11,28,3,00, 00);
        LocalDateTime match30DT = LocalDateTime.of(2022,11,28,6,00, 00);
        LocalDateTime match31DT = LocalDateTime.of(2022,11,28,9,00, 00);
        LocalDateTime match32DT = LocalDateTime.of(2022,11,28,12,00, 00);

        LocalDateTime match33DT = LocalDateTime.of(2022,11,29,3,00, 00);
        LocalDateTime match34DT = LocalDateTime.of(2022,11,29,6,00, 00);
        LocalDateTime match35DT = LocalDateTime.of(2022,11,29,9,00, 00);
        LocalDateTime match36DT = LocalDateTime.of(2022,11,29,12,00, 00);

        LocalDateTime match37DT = LocalDateTime.of(2022,11,30,3,00, 00);
        LocalDateTime match38DT = LocalDateTime.of(2022,11,30,6,00, 00);
        LocalDateTime match39DT = LocalDateTime.of(2022,11,30,9,00, 00);
        LocalDateTime match40DT = LocalDateTime.of(2022,11,30,12,00, 00);

        LocalDateTime match41DT = LocalDateTime.of(2022,12,1,3,00, 00);
        LocalDateTime match42DT = LocalDateTime.of(2022,12,1,6,00, 00);
        LocalDateTime match43DT = LocalDateTime.of(2022,12,1,9,00, 00);
        LocalDateTime match44DT = LocalDateTime.of(2022,12,1,12,00, 00);

        LocalDateTime match45DT = LocalDateTime.of(2022,12,2,3,00, 00);
        LocalDateTime match46DT = LocalDateTime.of(2022,12,2,6,00, 00);
        LocalDateTime match47DT = LocalDateTime.of(2022,12,2,9,00, 00);
        LocalDateTime match48DT = LocalDateTime.of(2022,12,2,12,00, 00);

        tournament.addMatch(match1DT, "Qatar National Team", "Ecuador National Team");
        tournament.addMatch(match2DT, "England National Team", "Iran National Team");
        tournament.addMatch(match3DT, "Senegal National Team", "Netherlands National Team");
        tournament.addMatch(match4DT, "USA National Team", "Wales National Team");
        tournament.addMatch(match5DT, "Argentina National Team", "Saudi Arabia National Team");
        tournament.addMatch(match6DT, "Denmark National Team", "Tunisia National Team");
        tournament.addMatch(match7DT, "Mexico National Team", "Poland National Team");
        tournament.addMatch(match8DT, "France National Team", "Australia National Team");
        tournament.addMatch(match9DT, "Morocco National Team", "Croatia National Team");
        tournament.addMatch(match10DT, "Germany National Team", "Japan National Team");
        tournament.addMatch(match11DT, "Spain National Team", "Costa Rica National Team");
        tournament.addMatch(match12DT, "Belgium National Team", "Canada National Team");
        tournament.addMatch(match13DT, "Switzerland National Team", "Cameroon National Team");
        tournament.addMatch(match14DT, "Uruguay National Team", "South Korea National Team");
        tournament.addMatch(match15DT, "Portugal National Team", "Ghana National Team");
        tournament.addMatch(match16DT, "Brazil National Team", "Serbia National Team");
        tournament.addMatch(match17DT, "Wales National Team", "Iran National Team");
        tournament.addMatch(match18DT, "Qatar National Team", "Senegal National Team");
        tournament.addMatch(match19DT, "Netherlands National Team", "Ecuador National Team");
        tournament.addMatch(match20DT, "England National Team", "USA National Team");
        tournament.addMatch(match21DT, "Tunisia National Team", "Australia National Team");
        tournament.addMatch(match22DT, "Poland National Team", "Saudi Arabia National Team");
        tournament.addMatch(match23DT, "France National Team", "Denmark National Team");
        tournament.addMatch(match24DT, "Argentina National Team", "Mexico National Team");
        tournament.addMatch(match25DT, "Japan National Team", "Costa Rica National Team");
        tournament.addMatch(match26DT, "Belgium National Team", "Morocco National Team");
        tournament.addMatch(match27DT, "Croatia National Team", "Canada National Team");
        tournament.addMatch(match28DT, "Spain National Team", "Germany National Team");
        tournament.addMatch(match29DT, "Cameroon National Team", "Serbia National Team");
        tournament.addMatch(match30DT, "South Korea National Team", "Ghana National Team");
        tournament.addMatch(match31DT, "Brazil National Team", "Switzerland National Team");
        tournament.addMatch(match32DT, "Portugal National Team", "Uruguay National Team");
        tournament.addMatch(match33DT, "Ecuador National Team", "Senegal National Team");
        tournament.addMatch(match34DT, "Netherlands National Team", "Qatar National Team");
        tournament.addMatch(match35DT, "Iran National Team", "USA National Team");
        tournament.addMatch(match36DT, "Wales National Team", "England National Team");
        tournament.addMatch(match37DT, "Tunisia National Team", "France National Team");
        tournament.addMatch(match38DT, "Australia National Team", "Denmark National Team");
        tournament.addMatch(match39DT, "Poland National Team", "Argentina National Team");
        tournament.addMatch(match40DT, "Saudi Arabia National Team", "Mexico National Team");
        tournament.addMatch(match41DT, "Croatia National Team", "Belgium National Team");
        tournament.addMatch(match42DT, "Canada National Team", "Morocco National Team");
        tournament.addMatch(match43DT, "Japan National Team", "Spain National Team");
        tournament.addMatch(match44DT, "Costa Rica National Team", "Germany National Team");
        tournament.addMatch(match45DT, "South Korea National Team", "Portugal National Team");
        tournament.addMatch(match46DT, "Ghana National Team", "Uruguay National Team");
        tournament.addMatch(match47DT, "Serbia National Team", "Switzerland National Team");
        tournament.addMatch(match48DT, "Cameroon National Team", "Brazil National Team");

        return tournament;
    }

   public static Tournament initTestTour() {

        //******************************************************************************

        //BLOCK A: Initialize all test values in the University

        // addTournament SUCCESS Case testing here
        Tournament tour1 = new Tournament("FIFA 2077", LocalDateTime.of(2077, 6, 12, 12, 00,00),
                LocalDateTime.of(2077,7,5, 12, 00, 00));
        int testCount = 1;
        int errCount = 0;
        int errTestCount = 0;

        LocalDateTime match1DT = LocalDateTime.of(2077,6,12,12,30);
        LocalDateTime match2DT = LocalDateTime.of(2077,6,12,14,00);
        LocalDateTime matchT1DT = LocalDateTime.of(2077,5,4,12,00);


        System.out.println("Entering Test Block A: Initialize Test Tournament Values");

        // TODO loadFromFile error testing here

        // TODO saveToFile error testing here



        try {
            int successCaseTestCount = 0;
            //Test 1-5

            //TESTING addCountry SUCCESS CASES
            successCaseTestCount++;
            tour1.addCountry("USA");
            testCount++;
            tour1.addCountry("England");
            testCount++;
            tour1.addCountry("France");
            testCount++;
            tour1.addCountry("South Korea");
            testCount++;
            tour1.addCountry("Morocco");
            testCount++;
            // Test 6-10
            tour1.addCountry("Italy");
            testCount++;
            tour1.addCountry("Germany");
            testCount++;
            tour1.addCountry("Brazil");
            testCount++;
            tour1.addCountry("Argentina");
            testCount++;
            tour1.addCountry("Costa Rica");
            testCount++;
            // Test 11-15
            tour1.addCountry("Mexico");
            testCount++;

            //TESTING addReferee SUCCESS CASES
            successCaseTestCount++;
            tour1.addReferee("John Smith","USA");
            testCount++;
            tour1.addReferee("Ref 1", "USA");
            testCount++;
            tour1.addReferee("Ref 2", "USA");
            testCount++;
            tour1.addReferee("Ref 3", "England");
            testCount++;
            // Test 16-20
            tour1.addReferee("Ref 4","Costa Rica");
            testCount++;
            tour1.addReferee("Ref 5","Brazil");
            testCount++;
            tour1.addReferee("Ref 6","Argentina");
            testCount++;
            tour1.addReferee("Ref 7","Mexico");
            testCount++;
            tour1.addReferee("Ref 8", "South Korea");
            testCount++;

            //TESTING addTeam SUCCESS CASES
            successCaseTestCount++;
            // Test 21-25
            tour1.addTeam("US National Team", "USA");
            testCount++;
            tour1.addTeam("French National Team", "France");
            testCount++;
            tour1.addTeam("England National Team", "England");
            testCount++;
            tour1.addTeam("Brazilian National Team", "Brazil");
            testCount++;
            tour1.addTeam("Mexican National Team", "Mexico");
            testCount++;

            // Test 26-30
            //TESTING addMatch SUCCESS CASES
            successCaseTestCount++;
            tour1.addMatch(match1DT,"US National Team", "French National Team");
            testCount++;
            tour1.addMatch(match2DT, "England National Team","Brazilian National Team");
            testCount++;

            Player player1 = new Player("Landon Donovan", 35, 70, 85);
            Player player2 = new Player("Wayne Rooney", 37, 65, 80);

            // 9 - TESTING addRefereeToMatch SUCCESS CASES
            successCaseTestCount++;
            tour1.addRefereeToMatch(match1DT, "Ref 7");
            testCount++;
            tour1.addRefereeToMatch(match2DT, "Ref 8");
            testCount++;

            // TODO addPlayerToLineUp SUCCESS Case testing here

            // TODO setMatchScore SUCCESS Case testing here

            // TODO getUpcomingMatchesAll SUCCESS Case testing here

            // TODO getUpcomingMatchesDate SUCCESS Case testing here

            // TODO addTeamMatches SUCCESS Case testing here

            // TODO getMatchLineUps SUCCESS Case testing here



            System.out.println("Values Initialized Successfully");
            System.out.printf("%d of 15 Task SUCCESS Cases Tested at University Package Level\n",successCaseTestCount);
            System.out.println("Exiting Test Block A");
        } catch (IllegalArgumentException iae) {
            System.out.printf("Block A: Test #%d failed.\n",testCount);
            System.out.println(iae);
            System.out.println("Values not Initialized Correctly. Error checking not performed");
            System.exit(1);
        }

        System.out.println("Entering Test Block B: Add Error Checking");
        testCount = 1;

        // TODO loadFromFile error testing here

        // TODO saveToFile error testing here

        // TODO addTournament error testing here

        //addCountry error testing Case : FAIL if Country already exists with given countryName
        try {
            errTestCount++;
            tour1.addCountry("France");
            System.out.println("addCountry error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addTeam error testing Case 1: FAIL when the country does not exist,
        try {
            errTestCount++;
            tour1.addTeam("National Team", "The Moon");
            System.out.println("addTeam Country error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addTeam error testing Case 2: FAIL when another team has the same name.
        try {
            errTestCount++;
            tour1.addTeam("US National Team", "USA");
            System.out.println("addTeam Name error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addReferee error testing Case : FAIL when another referee with the same name exists in the system.
        try {
            errTestCount++;
            tour1.addReferee("John Smith", "USA");
            System.out.println("addReferee error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addMatch error testing Case 1: FAIL when team names are the same
        try {
            errTestCount++;
            tour1.addMatch(matchT1DT,"TESTER","TESTER");
            System.out.println("addMatch Names are the same error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addMatch error testing Case 2: FAIL when date/time invalid
        try {
            errTestCount++;
            tour1.addMatch(match1DT,"US National Team","Mexican National Team");
            System.out.println("addMatch invalid date error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addMatch error testing Case 3: FAIL when teamA invalid
        try {
            errTestCount++;
            tour1.addMatch(matchT1DT,"US team","French National Team");
            System.out.println("addMatch teamA invalid error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        //addMatch error testing Case 4: FAIL when teamB invalid
        try {
            errTestCount++;
            tour1.addMatch(matchT1DT,"US National Team","The Moon");
            System.out.println("addMatch teamB invalid error checking failed.");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        // TODO addPlayer FAIL Case testing

        // TODO addRefereeToMatch FAIL Case testing here
        // addRefereeToMatch error testing Case 1: FAIL when Match doesn't exist
        try {
            errTestCount++;
            tour1.addRefereeToMatch(LocalDateTime.of(1999,12,10,00,00),"Ref 6");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        // addRefereeToMatch error testing Case 2: FAIL when Referee doesn't exist
        try {
            errTestCount++;
            tour1.addRefereeToMatch(match1DT,"Ref 100");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        // addRefereeToMatch error testing Case 3: FAIL when Referee is from same Country
        try {
            errTestCount++;
            tour1.addRefereeToMatch(match1DT,"Ref 1");
        } catch (IllegalArgumentException iae) {
            errCount++;
        }

        // TODO addPlayerToLineUp FAIL Case testing here

        // TODO setMatchScore FAIL Case testing here

        // TODO getUpcomingMatchesAll FAIL Case testing here

        // TODO getUpcomingMatchesDate FAIL Case testing here

        // TODO addTeamMatches FAIL Case testing here

        // TODO getMatchLineUps FAIL Case testing here


        System.out.printf("%d of %d errors detected\n",errCount,errTestCount);
        System.out.println("Exiting Test Block B");
        System.out.println("TEST PROGRAM COMPLETED");
        //USED FOR TESTING PRINTOUTS
        //System.out.println(tour1);
        return tour1;
    }

}
