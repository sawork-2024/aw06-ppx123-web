package com.example.webpos.products.db;


import com.example.webpos.products.model.Product;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class JD implements PosDB {

    private List<Product> products = null;

    @Override
    @Cacheable(value = "products")
    public List<Product> getProducts() {
        try {
            this.products = parseJD("java");
            System.out.println("Get from JD");
        } catch (IOException e) {
            this.products = new ArrayList<>();
        }
        return this.products;
    }

    @Override
    @Cacheable(value = "product", key = "#productId")
    public Product getProduct(String productId) {
        if (this.products == null) {
            getProducts();
        }
        for (Product p : this.products) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }


    public static List<Product> parseJD(String keyword) throws IOException {
        //获取请求https://search.jd.com/Search?keyword=java
        String url = "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8&pvid=d9fc21d24fb843789c9b9c7eaafd02ab";
        //解析网页
        Map<String, String> cookies = new HashMap<>();
        cookies.put("thor", "F0FA8C5A253BF4E2988569829F16D6A5986264056627E65702AA74200E84798E59DA81CB1E17FBFEFB360DA4161365418B105A356F3E9651C1E0BD23F88992C941A818A39EDBD93EFA6B573C0938116B235BEB849264D5B2954B2286DB6C4924E04BA815F29D520A90C46F93BEC2DBE5AB6610F93041B43BC23349EE51D3235CACE6119EECB3BBA1AF63ABC69E6CC4245D1994FC2B562A013688AF3A3D03EA24");

        Document document = Jsoup.connect(url).cookies(cookies).get();
//                Jsoup.parse(new URL(url), 10000);
        //所有js的方法都能用
        Element element = document.getElementById("J_goodsList");
        //获取所有li标签
        Elements elements = element.getElementsByTag("li");
//        System.out.println(element.html());
        List<Product> list = new ArrayList<>();

        //获取元素的内容
        for (Element el : elements
        ) {
            //关于图片特别多的网站，所有图片都是延迟加载的
            String id = el.attr("data-spu");
            String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            String price = el.getElementsByAttribute("data-price").text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            if (title.indexOf("，") >= 0)
                title = title.substring(0, title.indexOf("，"));

            Product product = new Product(id, title, Double.parseDouble(price), img);

            list.add(product);
        }
        return list;
    }


    @PostConstruct
    public void init() {
//        System.out.println(getProducts());
    }


}
