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

Currently, the third column is being filtered out. A new .xlsx file that does not use the second column like the current one to format data should leave it empty.