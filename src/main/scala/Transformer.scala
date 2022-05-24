import org.apache.spark.sql.SparkSession

object Transformer extends App {

    val spark = SparkSession.builder()
        .appName("File Reader")
        .master("local[3]")
        .getOrCreate()

    val file = "files/ferries2022-1.csv"

    val df = spark.read
        .option("header", "true")
        .option("multiline", "true")
        .option("mode", "FAILFAST")
        .csv(file)

    df.show(1)

}
