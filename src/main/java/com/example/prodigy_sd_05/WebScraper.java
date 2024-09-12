package com.example.prodigy_sd_05;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {

    public static void main(String[] args) {
        String url = "https://webscraper.io/test-sites/e-commerce/static/computers/laptops";

        List<String[]> productList = new ArrayList<>();

        productList.add(new String[] {"Product Name", "Price", "Rating"});

        try {
            Document document = Jsoup.connect(url).get();

            Elements products = document.select(".thumbnail");

            for (Element product : products) {
                String name = product.select(".title").text();
                String price = product.select(".price").text();
                String rating = product.select(".ratings > p:nth-child(2)").attr("data-rating");

                productList.add(new String[] {name, price, rating});
            }

            writeDataToCSV(productList, "products.csv");

            System.out.println("Data scraping and export to CSV completed successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while scraping the data.");
        }
    }

    public static void writeDataToCSV(List<String[]> data, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while writing data to CSV.");
        }
    }
}

