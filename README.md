#### Packing challenge
In order to solve test assignation (check internal file called ***Packaging Challenge.pdf***) the solution contains

Implementation file called com.mobiquityinc.packer.Packer
Beans associated with implementation  Goal, Thing

Test file **PackerTest**
Resources to test Files (good ones and bad ones to check boundaries)

The solution used Streams to read files and to check possible 'things' that fit with the solution, those 'things' are getted using Collection/filter methods

There is a possible upgrade using recursive solution but the cycles will be the same in this case.  

To check boundaries the application uses a method that can be modified in order to add more validations if apply, this method can be extended if the design consider that there are other validations associated.

To check that an item matches with the solution, the program uses the method _checkGoal_, it can be extended in order to add more validations if the design consider it feasible

The current implementation validates, packager/items with max weight of 100, items with cost higher that 100, negative values.

The implementation do not validate the line/file format.  It can be an upgrade in future sprints

Coverage report:
Lines: 95%
Methods: 95%


#### Running Tests and Server Locally 
```
	$ mvn clean package
	$ mvn clean install
```
