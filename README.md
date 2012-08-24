Collection Caster Service
=====================

## About

The Collection Caster Application Programming Interface (API) is a collection of REST Web services designed to help users to create an ATOM feed to advertise data collections so people and other applications and services can consume them through an Atom response feed. There are two main reasons to use the Collection Caster API:

* To advertise several or many data collections with a single  Atom Feed
* To help users create the Atom Feed.

The NSIDC Collection Caster API is compliant with the Federation of Earth Science Information Partners (ESIP) Discovery cluster's specification [v1.1](http://wiki.esipfed.org/index.php/Discovery_Cast_Atom_Response_Format_v1.1 "Discovery Cast Atom Response Format v1.1") and uses the common Atom response format, Atom Syndication Format (RFC 4287), with extensions specific for Earth science data usage such as GeoRSS, OpenSearch and Time namespaces. Atom is an XML-based document format that describes lists of related information known as feeds. Feeds are composed of a number of items, known as entries, each with an extensible set of attached metadata. For these feeds each entry should describe a single data set or data collection. For example, each entry has an ID URL, a title, a summary, a start and end time, and at least one author. An Atom Feed can have zero, one, or many entries.

Since the API is ESIP compliant, than any aggregation service that is able to aggregate ESIP feeds will be able to discover and incorporate relevant data sets from your feeds into their service; thus, greatly expanding the potential audience for your data.

You can use this API to create a collection cast feed, an XML document that you place on your Web site. In order to do this, you will need to create a header/footer file and a series of entry files, each of which describes a single data collection. The following documentation will explain these input files, the various outputs, and what to do with them. However, it is up to you to decide how you want to create the final collection cast file. It is recommended that you create the entry files in an automated manner using your metadata database or catalog of metadata records since essentially that is what the entry files are: files containing metadata about a particular data collection.
  
## Overview

An atom XML document is formed with 3 parts, the header, the entry, and the footer. In order to create an ESIP feed users will need to create either an XML input file or a JSON input file containing pertinent information for each of these parts. This information must also be constructed using the Federation of Earth Science Information Partners (ESIP) specifications to help ensure that Earth Science aggregators automatically retrieve and interpret your feed. To help you do that, we have created a template for you to use, or you can generate your own file without using our template paying close attention to the required fields and ESIP specifications.

These specifications and more detailed overview of the Caster and the ESIP standard can be found [here](http://nsidc.org/api/collection-caster/index.html "Collection Caster Application Programming Interface")

The Collection Caster API has 3 RESTful end-points that allow users to assemble an ESIP feed using the files previoulsy described. Once the web-app is up and running it will expose 3 RESTful services:

* /feed/header
* /entry
* /feed/footer


#### Generating the Header Content


Depending  whether you used XML or JSON format to create the header file, choose one of the following steps:

If you used XML format, enter the following command:

`curl  -X POST -H "Content-Type: application/xml" -d @xxxx.xml   http://yourSERVER.com/yourCONTEXT/cast/collection/1.0/feed/header`

If you used JSON format, enter the following command:

`curl  -X POST -H "Content-Type: application/json" -d @xxxx.json   http://yourSERVER.com/yourCONTEXT/collection/1.0/feed/header`

Where: xxxx = the directory path for the header/footer file.

The following XML example output is generated from the command entered in Step 1.

    <?xml version="1.0" ?>
      <feed xmlns="http://www.w3.org/2005/Atom">
        <id>http://yourSERVER.com/yourCONTEXT/example_collection_cast</id>
        <title type="text">Test</title>
        <updated>2011-09-26T12:09:54.000Z</updated>
        <author>
          <name>John Who </name>
          <email>email@domain.org</email>
          <uri>http://DOMAIN.org</uri>
        </author>

#### Generating the Footer Content

When generating the footer content, you are creating the closing tag for the Atom Feed XML file (`</feed>`). That is why you do not have to create a footer file, since there is no information needed to create a closing tag.
Enter the following command:

`curl http://yourSERVER.com/yourCONTEXT/collection/1.0/feed/footer`

The following XML example output is generated from the command entered in Step 1.

    </feed>

#### Generating the Entry Content


Depending on if you used XML or JSON format to create the entry file, choose one of the following steps:

If you used XML format, enter the following command:

`curl  -X POST -H "Content-Type: application/xml" -d @xxxx.xml   http://yourSERVER.com/yourCONTEXT/cast/collection/1.0/entry`

If you used JSON format, enter the following command:

`curl  -X POST -H "Content-Type: application/json" -d @xxxx.json   http://yourSERVER.com/yourCONTEXT/cast/collection/1.0/entry`

Where: xxxx = the directory path for the entry file.


## Compilation notes

To build the Collection Caster we need to install Maven and have a Java Runtime Environment >=1.6

* `mvn -Dmaven.test.skip=true package`

This will generate a web-app in the form of a .war file that can be deployed through Jetty, Tomcat, JBoss or Glassfish.

Before running the integration tests, it is necessary to download the rubygem dependencies for cucumber.  First execute the following command:

* `mvn integration-test -Dcucumber.installGems=true`

You can of course import the project to Eclipse and build it using the m2e plugin.


## Credit

This software was developed by the National Snow and Ice Data Center under NSF award 
number ARC 0946625 and NASA award number NNX10AE07A.


## License 

Copyright (c) 2012 Regents of the University of Colorado

This software was developed by the National Snow and Ice Data Center under NSF award number ARC 0946625 and NASA award number NNX10AE07A.

Permission is hereby granted, free of charge, to any person obtaining a copy of this
software and associated documentation files (the "Software"), to deal in the Software
without restriction, including without limitation the rights to use, copy, modify, merge,
publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
