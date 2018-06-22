Your task is to build a simple console application using any language that you prefer.

Expected implementation time is: ~4 hours.

# Requirements

* Written in Java
* It must run on *nix OS, and shouldn't require any unusual binaries
* Code should be covered by Unit tests


Don't feel obligated to solve this problem using just the some standard library. Part of engineering is knowing when to include 3rd party modules. In particular, if you know a good alternative to standard libraries, feel free to include it.

# The Randomness Beacon: Sampling Chaos
The National Institute of Standards and Technology broadcasts a signed, timestamped random bit sequence once a minute, every minute, and has since Unix epoch time 1378395540 (09/05/2013 11:39 a.m.). It's called the Randomness Beacon, and you're going to analyze it.

The Randomness Beacon publishes a RESTfulAPI ( https://beacon.nist.gov/home ), through which you can retrive any given minute's beacon value as 128 hexidecimal characters, as well as a variety of other authentication data. 

That program should retrieve the most recent event from the randomness beacon, and count the number of characters in the OutputValue the beacon returns. It should then print that output to standard out in comma-delimited format.

Example:
If the randomness beacon returned the string "01AF04F" for its OutputValue, your application should print:

    0,2
    1,1
    4,1
    A,1
    F,2
The first value of any given line is a character. The second value is the number of times that value occurred in the OutputValue string.

# Deliverables
* Code as a github project
* Installation (if needed) and running instructions in README.md
* Tests
* Information about spent time
