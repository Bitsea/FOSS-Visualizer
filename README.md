<h1>Foss Visualizer</h1>

<h2>Run</h2>

This is a maven based project.

To built and run the project either do:

./mvn clean package

followed by:

./java -jar target/FossVisualizer-1.0-SNAPSHOT.jar

Or alternatively use:

./mvn clean javafx:run

<h2>Select visualized Data</h2>

To select the data being visualized press the "Select File" button on the title page and select and appropriate .xlsx file.
If the selected file has been processed then the texts next to the button will become green and read: "File Selected: + {your filename}".

The format of the selected xlsx file must match the one of the file named "testData.xlsx" located under FOSS-Visualizer/src/main/resources.

The format for the input excel file is:

Column A: Issue Number, may be used to map a tree structure

Column B: Software area or category


Column C: skipped

Column D: Name of the OS Component

Column E: Parent of the OS Component

Column F: Indicator for component

Column G: Priority of the assigned License to component

Column H: License Type (SPDX-Format)

Column I: License Text

Column J: Description

Column K: Package/File Version

Column L: URL for component

Column M: External Note

Column O: Vulnerability List (CVSS Severity Score, Severity Impact, CVE Dictionary Entry, URL)

Column P: Number of files with Vulnerabilities

Column Q: List of files for the component

Column R: Copyright Text

Column S: Modification (yes or no)

Column T: Linking (static or dynamic)

Column U: Have Encryption (yes or no)

Column V: Activity

Column W: Size

Column X: Community diversity

Column Y: what is the most actual version

Column Z: Approved by policy (yes or no)

Column AA: have Patent (yes or no)

Column AB: Have Export Restrictions (yes or no)

Column AC: Number of Export Restrictions

Column AD: Number of Vulnerabilities

Column AE: AI generated code (yes or no)

Column AF: Snippet (yes or no)

Column AG: Strictness of license

Currently, the third column is being filtered out. 