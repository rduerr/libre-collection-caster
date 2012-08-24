Feature: Collection Cast Service Smoke Tests
	As an NSIDC Ops
	I want to be able to see whether the collection cast service is emitting smoke for basic operations
	So that I have a good likelihood of knowing that the services are operational
	
	@SmokeTest
	Scenario: Check example page is up and running
		Given the server is http://localhost:8789
		When I download the /example page
		Then the response code will be a 200
			And the content length will be greater than zero
			
	@SmokeTest
	Scenario: Check help page is up and running
		Given the server is http://localhost:8789
		When I download the /help page
		Then the response code will be a 200
			And the content length will be greater than zero
	