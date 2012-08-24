
Feature: Collection Cast Service
    As a user with lots of collections to advertise, 
    I want to write a script that can cast each of my
        collections using an ESIP-compliant web service 
    So that all I have to do is write the interface
        between my metadata repository and the web service 
        and don't have to install anything.
       
Scenario: Casting service returns a valid ESIP feed
    Given metadata for a collection of data with the minimum set of required input fields
        And an updated field is specified
        And a bounding box is specified 
    When the casting service is called
    Then a valid ESIP XML Atom Entry is returned

Scenario: Casting service generates updated tag when not specified
    Given metadata for a collection of data with the minimum set of required input fields
    When the casting service is called
    Then a valid ESIP XML Atom Entry is returned
 
Scenario: Casting service returns a meaningful error when no params are provided
    Given metadata for a collection of data with no input parameters
    When the casting service is called
    Then the user should be given a meaningful error with a link to the help page

Scenario: Casting service returns a meaningful error when some params are provided
    Given metadata for a collection of data with some of the required input parameters
    When the casting service is called
    Then the user should be given a meaningful error with a link to the help page

Scenario Outline: Service should handle XML, JSON, and YAML
	Given an execution environment
		And a file in <format> format
	When I post to the casting service at /entry
    Then a valid ESIP XML Atom Entry is returned
	
Scenarios: Three different formats
	|format|
	| XML  |
	| JSON |
#  | YAML |
# Yaml is not being implemented because the RestEasy jaxb provider
# is not compatible with CXF.  We could implement a CXF jaxb provider
# for yaml using the jyaml parser.

Scenario: Casting service returns valid shell feed
	Given metadata announcing a set of one or more data collections
	When the feed service is called
	Then a valid Atom feed is returned with no entries

Scenario: Casting service returns valid a feed header
	Given metadata announcing a set of one or more data collections
	When the feed header service is called
	Then a valid Atom header is returned with no entries
	
Scenario: Casting service returns valid a feed footer
	Given metadata announcing a set of one or more data collections
	When the feed footer service is called
	Then a valid Atom footer is returned with no entries
