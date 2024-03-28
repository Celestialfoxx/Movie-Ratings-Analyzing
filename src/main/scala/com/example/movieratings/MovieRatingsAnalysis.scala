package com.example.movieratings

import org.apache.spark.sql.SparkSession

object MovieRatingsAnalysis extends App {
  // Initialize SparkSession
  val spark: SparkSession = SparkSession
    .builder()
    .appName("MovieRatingsAnalysis")
    .master("local[*]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  val filename = "src/main/scala/resources/movie_metadata.csv"

  val df = spark.read
    .option("header", "true") // Assuming the first row contains column names
    .option("inferSchema", "true") // Infer data types
    .csv(filename)

  val ratings = df.select("imdb_score").na.drop() // Drop rows with null ratings

  // Calculate mean and standard deviation
  val stats = ratings.describe("imdb_score")

  stats.show()

  spark.stop()
}
