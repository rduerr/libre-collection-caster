Feature: Collection Cast Service Sample
	As a developer inexperienced with data collection casting
	I want to receive an example ESIP-compliant data collection cast from a service
	So that I can learn enough to write my own casting script	

	Scenario: Sample XML is a valid ESIP document
		When I get the sample page
		Then a valid ESIP XML document is returned
		  And the document will contain a link to the ESIP specification
		  And every element in the document will have a descriptive comment

	Scenario: Help page is available at appropriate URI
		Given an execution environment
		When I access /help
		Then the TEMP response code will be a 200

    Scenario: Example xml is available at appropriate URI
        Given an execution environment
        When I access /example
        Then the TEMP response code will be a 200
		
	Scenario: User is viewing the service documentation
		When I get the help page
		Then the document will contain how to get to the sample page
		  And the document will contain information about what ESIP is