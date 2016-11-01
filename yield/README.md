# Yield Spread Calculator

Utility to calculate the yield spread between for a set of corporate bonds against a set of government bonds.  The utility supports this calculation in two different modes.
1. Simple: Calculated against the nearest neighbour bond
2. Interpolated: Calculated against a linearly interpolated bond curve

## Installation

#### Linux
1. From yield directory run
```./gradlew clean installDist test javadoc```
#### Windows
1. From yield directory run
```gradlew.bat clean installDist test javadoc```

#### Artifacts
1. Binaries builds to 
```yield/build/install/yield```
2. Javadoc builds to
```yield/build/docs/javadoc```
3. Unit test report
```yield/build/reports/tests ```

## Usage

#### Sample Input
The yield CLI application expects JSON input files in the following format
```json
{[
{"name":"C1","type":"CORP","term":10.3,"yield":5.3},
{"name":"C2","type":"CORP","term":15.2,"yield":8.3},
{"name":"G1","type":"GOV","term":9.4,"yield":3.7},
{"name":"G2","type":"GOV","term":12,"yield":4.8},
{"name":"G3","type":"GOV","term":16.3,"yield":5.5}
]}
```
#### Usage
1. Print usage help
```yield -h```
2. Process a file
```./build/install/yield/bin/yield -m 0 data/input.json ```
#### Sample Output
 ```C1
spread = 1.60
C2
spread = 2.80
```
## Approach
We describe several of the design decision made in developing this solution
- Both simple and interpolated yields are different flavours of the same problem.  Therefore it made sense to define an interface SpreadCalculator to define this boundary.
- How to store the bond data is a critical decision.  In this problem we know that we will need to search by term and we need to search by type.  Therefore it made sense to define an interface BondDataService to expose this functionality.  In terms of implementation for scalability this interface would likely be backed by a relational databased service.  For our toy example we choose to implement LocalBondDataService which provides an efficient binary searchable local storage.  
- Given that both spread modes differ only in the number of bond data points and the way in which the bond data points are manipulated a BondIterator was return from the ListIterator implementation (BondDataService) so that neighbouring Bonds could be accessed by the LinearInterpolatedSpread.  Also it may be useful is cubic spline or alternative interpolation method are implemented.
 
## Notes

Given the time constraints and limited requirements there were several items that I would revisit

1. Platform - Devleoped on Ubuntu 16.04 LTS and not tested cross platform
2.  BondIterator 
	- Implement remaining methods causing compile warnings due to incomplete ListIterator.
	- The BondIterator and LinearInterpolatedSpread implementation seems a bit akward and forced.  I would refactor this to have the BondDataService expose an extra API to return a pair (for linear interpolation) or if the requirements were extended to other interpolation methods then modify the interface to return an ordered "context" of the surrouding N bonds.
- The data service is missing a lot of requirements in order to design an optimal implementation so this would need revisiting.
- Validation on input JSON
