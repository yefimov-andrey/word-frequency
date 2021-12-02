# Word frequency analyzer
## Launch instruction
0. Install Java 8 (if you have several versions installed already, set your corresponding system variable for the 8th version).
1. Download source code or testtask-0.0.1-SNAPSHOT.war from release (skip to step 4a if you downloaded .war file).
2. Put necessary text files into the data folder (remove unnecessary ones if they are present).
3. Perform command "mvn clean package" to package the project.
4. Perform command "cd target" to go to the packaged project directory.

4a. <b> Only do this step if you want to change the data folder or downloaded packaged project</b>. Set environment variable "DATA_FOLDER" to the folder where input files will be located.

5. Launch the application by running java -jar testtask-0.0.1-SNAPSHOT.war
6. Go to localhost:8080/analyze in order to get frequency results.

## Docker launch instruction

In order to launch application using Docker:
1. Build the application using command docker build -t ${packageName} .
1a. Alternatively, download testtask.tar from releases folder.
2. Run the package with the following command:

docker run -p ${port}:8080 -v ${dataDirectory}:/data -e "DATA_FOLDER=/data" ${packageName}

Where port would be the one you wish to run your Docker container on, data directory would be the one where input files are located and package name would be the one you built it with or "testtask" if you downloaded it.
3. Go to localhost:${port}/analyze in order to get frequency results

## Result output
Results are formed as follows:
1. Files are put into the "results" folder inside the initial data folder
1. They are split in 4 parts: words beginning with the letter A-G, N-H, O-U and V-Z respectively - resultsAG.txt, resultsNH.txt, resultsOU.txt and resultsVZ.txt.
2. In every txt file words are sorted by frequency from the most frequent to the rarest ones.
