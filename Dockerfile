# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install Gradle
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.5-bin.zip && \
    unzip gradle-7.5-bin.zip && \
    mv gradle-7.5 /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

# Define environment variable
ENV OUTPUT_FILE_NAME search_rankings.csv

# Run the application
CMD ["gradle", "run"]